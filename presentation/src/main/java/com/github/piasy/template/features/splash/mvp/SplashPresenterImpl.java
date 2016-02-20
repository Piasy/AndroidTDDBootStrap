/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Piasy
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.piasy.template.features.splash.mvp;

import android.support.annotation.NonNull;
import com.github.piasy.common.android.utils.net.RxUtil;
import com.github.piasy.model.dao.GithubUserDAO;
import com.github.piasy.model.entities.GithubUser;
import com.github.piasy.template.base.mvp.BaseRxPresenter;
import de.greenrobot.event.EventBus;
import java.util.List;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Piasy{github.com/Piasy} on 15/7/24.
 *
 * Implementation for {@link SplashPresenter}.
 */
public class SplashPresenterImpl extends BaseRxPresenter<SplashView> implements SplashPresenter {

    private final EventBus mBus;
    private final GithubUserDAO mGithubUserDAO;
    private final RxUtil.RxErrorProcessor mRxErrorProcessor;

    /**
     * Provide {@link SplashPresenter} with dependencies.
     *
     * @param bus {@link EventBus}.
     * @param githubUserDAO {@link GithubUserDAO} to operate with data.
     * @param rxErrorProcessor {@link RxUtil.RxErrorProcessor} to process errors in rx.
     */
    public SplashPresenterImpl(final EventBus bus, final GithubUserDAO githubUserDAO,
            final RxUtil.RxErrorProcessor rxErrorProcessor) {
        super();
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
    public void searchUser(@NonNull final String query) {
        // load user from dao, dao take responsibility for where to get the real data
        addSubscription(mGithubUserDAO.searchUser(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<GithubUser>>() {
                    @Override
                    public void call(final List<GithubUser> githubUsers) {
                        if (isViewAttached()) {
                            getView().showSearchUserResult(githubUsers);
                        }
                    }
                }, mRxErrorProcessor));
    }
}
