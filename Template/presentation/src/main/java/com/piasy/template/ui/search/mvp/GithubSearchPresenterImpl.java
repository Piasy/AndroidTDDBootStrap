package com.piasy.template.ui.search.mvp;

import com.piasy.common.android.utils.net.RxUtil;
import com.piasy.model.entities.GithubUser;
import com.piasy.model.rest.github.GithubAPI;
import com.piasy.template.base.mvp.BaseRxPresenter;
import com.raizlabs.android.dbflow.sql.language.Select;

import android.support.annotation.NonNull;

import java.util.List;

import de.greenrobot.event.EventBus;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Piasy{github.com/Piasy} on 15/7/24.
 */
public class GithubSearchPresenterImpl extends BaseRxPresenter<GithubSearchView>
        implements GithubSearchPresenter {

    private final EventBus mBus;
    private final GithubAPI mGithubAPI;

    public GithubSearchPresenterImpl(EventBus bus, GithubAPI githubAPI) {
        mBus = bus;
        mGithubAPI = githubAPI;
    }

    @NonNull
    @Override
    protected EventBus getEventBus() {
        return mBus;
    }

    @Override
    public void searchUser(String query, String sort, String order) {
        // show local data at first
        addSubscription(Observable.just(new Select().from(GithubUser.class).queryList())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(users -> {
                    if (isViewAttached() && users != null && !users.isEmpty()) {
                        getView().showSearchUserResult(users);
                    }
                }, RxUtil.NetErrorProcessor));

        // then load data from cloud
        addSubscription(mGithubAPI.searchGithubUsers(query, sort, order)
                .subscribeOn(Schedulers.io())
                .doOnNext(githubUserGithubSearchResult -> {
                    List<GithubUser> local = new Select().from(GithubUser.class).queryList();
                    for (int i = 0; i < githubUserGithubSearchResult.getItems().size(); i++) {
                        int index = local.indexOf(githubUserGithubSearchResult.getItems().get(i));
                        if (index != -1) {
                            local.get(index).copy(githubUserGithubSearchResult.getItems().get(i));
                            local.get(index).update();
                        } else {
                            githubUserGithubSearchResult.getItems().get(i).save();
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(githubUserGithubSearchResult -> {
                    if (isViewAttached()) {
                        getView().showSearchUserResult(githubUserGithubSearchResult.getItems());
                    }
                }, RxUtil.NetErrorProcessor));
    }
}
