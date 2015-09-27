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

package com.github.piasy.template.features.splash;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import butterknife.Bind;
import com.github.piasy.common.android.utils.roms.MiUIUtil;
import com.github.piasy.common.android.utils.ui.ToastUtil;
import com.github.piasy.common.utils.EmailUtil;
import com.github.piasy.model.entities.GithubUser;
import com.github.piasy.template.R;
import com.github.piasy.template.base.BaseFragment;
import com.github.piasy.template.features.splash.di.SplashComponent;
import com.github.piasy.template.features.splash.mvp.SplashPresenter;
import com.github.piasy.template.features.splash.mvp.SplashView;
import com.jakewharton.rxbinding.support.v7.widget.RxSearchView;
import com.mugen.Mugen;
import com.mugen.MugenCallbacks;
import com.mugen.attachers.RecyclerViewAttacher;
import com.promegu.xlog.base.XLog;
import com.trello.rxlifecycle.FragmentEvent;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Fragment that search for github user.
 */
@XLog
public class GithubSearchFragment extends BaseFragment<SplashView, SplashPresenter, SplashComponent>
        implements SplashView {

    private static final int SEARCH_DELAY_MILLIS = 500;
    private static final int SPAN_COUNT = 3;

    @Inject
    EmailUtil mEmailUtil;
    @Inject
    ToastUtil mToastUtil;
    @Inject
    AppCompatActivity mActivity;
    @Bind(R.id.mRvSearchResult)
    RecyclerView mRvSearchResult;
    @Bind(R.id.mToolBar)
    Toolbar mToolBar;
    @Inject
    Resources mResources;

    @Inject
    MiUIUtil mMiUIUtil;

    private GithubSearchUserResultAdapter mAdapter;
    private RecyclerViewAttacher mAttacher;
    private boolean mIsLoading = false;
    private boolean mHasAllLoaded = false;
    private String mCurrentQuery = "";

    @Override
    protected void inject() {
        getComponent().inject(this);
    }

    @Override
    public SplashPresenter createPresenter() {
        return null;
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupView();
    }

    private void setupView() {
        mActivity.setSupportActionBar(mToolBar);
        mAdapter = new GithubSearchUserResultAdapter(mResources,
                user -> mToastUtil.makeToast("Clicked: " + user.login()));
        mRvSearchResult.setLayoutManager(
                new StaggeredGridLayoutManager(SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL));
        mRvSearchResult.setAdapter(mAdapter);
        mAttacher = Mugen.with(mRvSearchResult, new MugenCallbacks() {
            @Override
            public void onLoadMore() {
                mIsLoading = true;
                // TODO load more
            }

            @Override
            public boolean isLoading() {
                return mIsLoading;
            }

            @Override
            public boolean hasLoadedAllItems() {
                return mHasAllLoaded;
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search_github_user, menu);
        MenuItem search = menu.findItem(R.id.mActionSearch);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        RxSearchView.queryTextChanges(searchView)
                .compose(this.<CharSequence>bindUntilEvent(FragmentEvent.DESTROY_VIEW))
                .debounce(SEARCH_DELAY_MILLIS, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(query -> {
                    if (!TextUtils.equals(mCurrentQuery, query)) {
                        mCurrentQuery = query.toString();
                        if (!TextUtils.isEmpty(mCurrentQuery)) {
                            presenter.searchUser(mCurrentQuery);
                            showProgress();
                            mIsLoading = true;
                        } else {
                            showSearchUserResult(Collections.emptyList());
                        }
                    }
                });
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_github_search;
    }

    @Override
    public void showSearchUserResult(@NonNull final List<GithubUser> users) {
        mAdapter.showUsers(users);
        stopProgress(true);
        mIsLoading = false;
        mAttacher.start();
    }
}
