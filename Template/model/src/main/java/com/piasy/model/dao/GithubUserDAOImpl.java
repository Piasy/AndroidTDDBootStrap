package com.piasy.model.dao;

import com.piasy.common.Constants;
import com.piasy.model.entities.GithubSearchResult;
import com.piasy.model.entities.GithubUser;
import com.piasy.model.rest.github.GithubAPI;
import com.raizlabs.android.dbflow.sql.language.Select;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by Piasy{github.com/Piasy} on 15/8/5.
 */
public class GithubUserDAOImpl implements GithubUserDAO {

    private final GithubAPI mGithubAPI;

    public GithubUserDAOImpl(GithubAPI githubAPI) {
        mGithubAPI = githubAPI;
    }

    @NonNull
    @Override
    public Observable<List<GithubUser>> getUsers() {
        return Observable.create(subscriber -> {
            // load from db, and show it if not empty
            List<GithubUser> local = new Select().from(GithubUser.class).queryList();
            if (local == null) {
                local = new ArrayList<>();
            } else {
                subscriber.onNext(local);
            }

            // search from cloud
            GithubSearchResult<GithubUser> searchResult = mGithubAPI.searchGithubUsers("piasy",
                    Constants.GithubAPIParams.SEARCH_SORT_JOINED,
                    Constants.GithubAPIParams.SEARCH_ORDER_DESC).toBlocking().single();
            // and show it
            if (searchResult.getItems() == null) {
                subscriber.onNext(new ArrayList<GithubUser>());
            } else {
                // show search result if db data is empty
                if (local.isEmpty()) {
                    subscriber.onNext(searchResult.getItems());
                }

                // if not empty, get full user info from cloud
                for (int i = 0; i < searchResult.getItems().size(); i++) {
                    GithubUser fullUserInfo = mGithubAPI
                            .getGithubUser(searchResult.getItems().get(i).getLogin())
                            .toBlocking().single();
                    int index = local.indexOf(fullUserInfo);
                    if (index != -1) {
                        local.get(index).copy(fullUserInfo);
                        local.get(index).update();
                    } else {
                        fullUserInfo.save();
                        local.add(fullUserInfo);
                    }
                }
                // and then show it
                if (!local.isEmpty()) {
                    subscriber.onNext(local);
                }
            }

            subscriber.onCompleted();
        });
    }
}
