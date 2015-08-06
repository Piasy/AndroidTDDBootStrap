package com.piasy.template.ui.search.mvp;

import com.piasy.common.android.utils.net.RxUtil;
import com.piasy.model.dao.GithubUserDAO;
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
    private final GithubUserDAO mGithubUserDAO;

    public GithubSearchPresenterImpl(EventBus bus, GithubUserDAO githubUserDAO) {
        mBus = bus;
        mGithubUserDAO = githubUserDAO;
    }

    @NonNull
    @Override
    protected EventBus getEventBus() {
        return mBus;
    }

    @Override
    public void loadUser() {
        // load user from dao, dao take responsibility for where to get the real data
        addSubscription(mGithubUserDAO.getUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(githubUsers -> {
                    if (isViewAttached()) {
                        getView().showSearchUserResult(githubUsers);
                    }
                }, RxUtil.NetErrorProcessor));
    }
}
