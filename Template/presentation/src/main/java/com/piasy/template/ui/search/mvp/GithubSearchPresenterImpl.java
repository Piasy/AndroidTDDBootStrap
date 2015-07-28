package com.piasy.template.ui.search.mvp;

import com.piasy.common.android.utils.net.RxUtil;
import com.piasy.model.rest.github.GithubAPI;
import com.piasy.template.base.mvp.BaseRxPresenter;

import android.support.annotation.NonNull;

import de.greenrobot.event.EventBus;
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
        addSubscription(mGithubAPI.searchGithubUsers(query, sort, order)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(githubUserGithubSearchResult -> {
                    if (isViewAttached()) {
                        getView().showSearchUserResult(githubUserGithubSearchResult.getItems());
                    }
                }, RxUtil.NetErrorProcessor));
    }
}
