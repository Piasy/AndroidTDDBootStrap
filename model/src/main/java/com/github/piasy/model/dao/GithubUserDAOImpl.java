package com.github.piasy.model.dao;

import android.support.annotation.NonNull;
import com.github.piasy.common.Constants;
import com.github.piasy.common.android.utils.net.RxUtil;
import com.github.piasy.model.db.StorIOSQLiteDelegate;
import com.github.piasy.model.entities.GithubUser;
import com.github.piasy.model.rest.github.GithubAPI;
import java.util.ArrayList;
import java.util.List;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by Piasy{github.com/Piasy} on 15/8/5.
 */
public class GithubUserDAOImpl implements GithubUserDAO {

    private final StorIOSQLiteDelegate mStorIOSQLite;
    private final GithubAPI mGithubAPI;
    private final RxUtil.RxErrorProcessor mRxErrorProcessor;

    public GithubUserDAOImpl(StorIOSQLiteDelegate storIOSQLite, GithubAPI githubAPI,
            RxUtil.RxErrorProcessor rxErrorProcessor) {
        mStorIOSQLite = storIOSQLite;
        mGithubAPI = githubAPI;
        mRxErrorProcessor = rxErrorProcessor;
    }

    @NonNull
    @Override
    public Observable<List<GithubUser>> getUsers() {
        // search from cloud
        mGithubAPI.searchGithubUsers("piasy", Constants.GithubAPIParams.SEARCH_SORT_JOINED,
                Constants.GithubAPIParams.SEARCH_ORDER_DESC)
                .subscribeOn(Schedulers.io())
                .subscribe(searchResult -> {
                    if (searchResult.items().isEmpty()) {
                        // if no cloud data, remove local data
                        mStorIOSQLite.deleteAllGithubUser();
                    } else {
                        // else update local data totally
                        List<GithubUser> local = mStorIOSQLite.getAllGithubUser();

                        // NOTE!!! create a copy to avoid modify caller's state
                        // there will be two extra List creation, but they will be GCed quickly(if
                        // no memory leak happens), but object creation may have bad effect in
                        // Android platform, so is this a good practice?

                        // UPDATE: create snapshot for params is the caller's duty
                        List<GithubUser> cloud = searchResult.items();
                        if (local.isEmpty()) {
                            // first time, no local data, show partly cloud data at first
                            // NOTE!!! create a snapshot, to avoid callee see changed params
                            mStorIOSQLite.putAllGithubUser(new ArrayList<>(cloud));
                        }

                        for (int i = 0; i < cloud.size(); i++) {
                            GithubUser fullUserInfo = mGithubAPI.getGithubUser(cloud.get(i).login())
                                    .toBlocking()
                                    .single();
                            cloud.set(i, fullUserInfo);
                        }
                        mStorIOSQLite.putAllGithubUser(cloud);
                    }
                }, mRxErrorProcessor);

        return mStorIOSQLite.getAllGithubUserReactively();
    }
}
