package com.piasy.model.dao;

import com.piasy.common.Constants;
import com.piasy.common.android.utils.net.RxUtil;
import com.piasy.model.entities.GithubUser;
import com.piasy.model.rest.github.GithubAPI;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;

import android.support.annotation.NonNull;

import java.util.List;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by Piasy{github.com/Piasy} on 15/8/5.
 */
public class GithubUserDAOImpl implements GithubUserDAO {

    private final StorIOSQLite mStorIOSQLite;
    private final GithubAPI mGithubAPI;
    private final RxUtil.RxErrorProcessor mRxErrorProcessor;

    public GithubUserDAOImpl(StorIOSQLite storIOSQLite, GithubAPI githubAPI,
                             RxUtil.RxErrorProcessor rxErrorProcessor) {
        mStorIOSQLite = storIOSQLite;
        mGithubAPI = githubAPI;
        mRxErrorProcessor = rxErrorProcessor;
    }

    @NonNull
    @Override
    public Observable<List<GithubUser>> getUsers() {
        // search from cloud
        mGithubAPI.searchGithubUsers("piasy",
                Constants.GithubAPIParams.SEARCH_SORT_JOINED,
                Constants.GithubAPIParams.SEARCH_ORDER_DESC)
                .subscribeOn(Schedulers.io())
                .subscribe(searchResult -> {
                    if (searchResult.getItems() == null || searchResult.getItems().isEmpty()) {
                        // if no cloud data, remove local data
                        mStorIOSQLite.delete()
                                .byQuery(GithubUserTableMeta.getDeleteAllQuery())
                                .prepare()
                                .executeAsBlocking();
                    } else {
                        // else update local data totally
                        List<GithubUser> local = mStorIOSQLite.get()
                                .listOfObjects(GithubUser.class)
                                .withQuery(GithubUserTableMeta.sQueryAll)
                                .prepare()
                                .executeAsBlocking();

                        List<GithubUser> cloud = searchResult.getItems();
                        if (local.isEmpty()) {
                            // first time, no local data, show partly cloud data at first
                            mStorIOSQLite.put()
                                    .objects(cloud)
                                    .prepare()
                                    .executeAsBlocking();
                        }

                        for (int i = 0; i < searchResult.getItems().size(); i++) {
                            GithubUser fullUserInfo = mGithubAPI
                                    .getGithubUser(cloud.get(i).login())
                                    .toBlocking().single();
                            cloud.set(i, fullUserInfo);
                        }
                        mStorIOSQLite.put()
                                .objects(cloud)
                                .prepare()
                                .executeAsBlocking();
                    }
                }, mRxErrorProcessor);

        return mStorIOSQLite.get()
                .listOfObjects(GithubUser.class)
                .withQuery(GithubUserTableMeta.sQueryAll)
                .prepare()
                .createObservable()
                .subscribeOn(Schedulers.io());
    }
}
