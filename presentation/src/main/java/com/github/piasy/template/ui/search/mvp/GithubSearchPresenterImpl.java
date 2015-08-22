package com.github.piasy.template.ui.search.mvp;

import android.support.annotation.NonNull;
import com.github.piasy.common.android.utils.net.RxUtil;
import com.github.piasy.model.dao.GithubUserDAO;
import com.github.piasy.template.base.mvp.BaseRxPresenter;
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
    private final RxUtil.RxErrorProcessor mRxErrorProcessor;

    public GithubSearchPresenterImpl(EventBus bus, GithubUserDAO githubUserDAO,
            RxUtil.RxErrorProcessor rxErrorProcessor) {
        mBus = bus;
        mGithubUserDAO = githubUserDAO;
        mRxErrorProcessor = rxErrorProcessor;
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
                }, mRxErrorProcessor));
    }
}
