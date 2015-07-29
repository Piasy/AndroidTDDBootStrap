package com.piasy.template.ui.search.mvp;

import com.piasy.common.android.utils.net.RxUtil;
import com.piasy.model.entities.GithubUser;
import com.piasy.model.rest.github.GithubAPI;
import com.piasy.template.base.mvp.BaseRxPresenter;
import com.raizlabs.android.dbflow.runtime.TransactionManager;
import com.raizlabs.android.dbflow.runtime.transaction.process.ProcessModelInfo;
import com.raizlabs.android.dbflow.runtime.transaction.process.SaveModelTransaction;
import com.raizlabs.android.dbflow.sql.language.Select;

import android.support.annotation.NonNull;

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
                .doOnNext(githubUserGithubSearchResult ->
                        TransactionManager.getInstance()
                                .addTransaction(
                                        new SaveModelTransaction<>(
                                                ProcessModelInfo.withModels(
                                                        githubUserGithubSearchResult.getItems()))))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(githubUserGithubSearchResult -> {
                    if (isViewAttached()) {
                        getView().showSearchUserResult(githubUserGithubSearchResult.getItems());
                    }
                }, RxUtil.NetErrorProcessor));
    }
}
