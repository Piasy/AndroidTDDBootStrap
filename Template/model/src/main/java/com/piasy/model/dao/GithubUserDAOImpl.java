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
            List<GithubUser> local = new Select().from(GithubUser.class).queryList();
            if (local == null) {
                local = new ArrayList<>();
            }
            subscriber.onNext(local);

            GithubSearchResult<GithubUser> searchResult = mGithubAPI.searchGithubUsers("piasy",
                    Constants.GithubAPIParams.SEARCH_SORT_JOINED,
                    Constants.GithubAPIParams.SEARCH_ORDER_DESC).toBlocking().single();
            if (searchResult.getItems() == null) {
                subscriber.onNext(new ArrayList<GithubUser>());
            } else {
                subscriber.onNext(searchResult.getItems());

                for (int i = 0; i < searchResult.getItems().size(); i++) {
                    int index = local.indexOf(searchResult.getItems().get(i));
                    if (index != -1) {
                        local.get(index).copy(searchResult.getItems().get(i));
                        local.get(index).update();
                    } else {
                        searchResult.getItems().get(i).save();
                    }
                }
            }

            subscriber.onCompleted();
        });
    }
}
