package com.piasy.template.ui.search.mvp;

import com.piasy.common.android.utils.net.RxUtil;
import com.piasy.model.entities.GithubSearchResult;
import com.piasy.model.entities.GithubUser;
import com.piasy.model.rest.github.GithubAPI;
import com.piasy.template.base.mvp.BaseRxPresenter;

import android.support.annotation.NonNull;

import de.greenrobot.event.EventBus;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
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
                .subscribe(new Action1<GithubSearchResult<GithubUser>>() {
                    @Override
                    public void call(GithubSearchResult<GithubUser> githubUserGithubSearchResult) {
                        if (isViewAttached()) {
                            getView().showSearchUserResult(githubUserGithubSearchResult.getItems());
                        }
                    }
                }, RxUtil.NetErrorProcessor));
    }
}
