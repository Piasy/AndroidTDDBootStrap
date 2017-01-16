/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Piasy
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

package com.github.piasy.bootstrap.features.search;

import android.app.Activity;
import android.content.res.Resources;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import butterknife.BindView;
import com.github.piasy.bootstrap.R;
import com.github.piasy.bootstrap.R2;
import com.github.piasy.bootstrap.base.android.BaseFragment;
import com.github.piasy.bootstrap.base.utils.ScreenUtil;
import com.github.piasy.bootstrap.base.utils.ToastUtil;
import com.github.piasy.bootstrap.features.profile.ProfileActivityAutoBundle;
import com.github.piasy.bootstrap.model.users.GithubUser;
import com.jakewharton.rxbinding.support.v7.widget.RxSearchView;
import hu.akarnokd.rxjava.interop.RxJavaInterop;
import io.reactivex.Flowable;
import java.util.List;
import javax.inject.Inject;
import onactivityresult.ExtraString;
import onactivityresult.OnActivityResult;

public class SearchFragment
        extends BaseFragment<SearchUserView, SearchPresenter, SearchComponent>
        implements SearchUserView {

    public static final String RESULT_KEY_DUMMY = "dummy";

    private static final int SPAN_COUNT = 3;
    private static final int CODE_DETAIL = 1000;

    @Inject
    Resources mResources;
    @Inject
    AppCompatActivity mActivity;
    @Inject
    ToastUtil mToastUtil;
    @Inject
    ScreenUtil mScreenUtil;

    @BindView(R2.id.mToolBar)
    Toolbar mToolBar;
    @BindView(R2.id.mRvSearchResult)
    RecyclerView mRvSearchResult;

    private SearchUserResultAdapter mAdapter;
    private SearchView mSearchView;

    @Override
    protected int getLayoutRes() {
        return R.layout.search_fragment;
    }

    @Override
    protected boolean shouldHaveOptionsMenu() {
        return true;
    }

    @Override
    protected void bindView(final View rootView) {
        super.bindView(rootView);
        mToolBar.setTitle(R.string.search);
        mActivity.setSupportActionBar(mToolBar);
        mAdapter = new SearchUserResultAdapter(mResources, user -> startActivityForResultSafely(
                ProfileActivityAutoBundle.builder(user).build(getContext()),
                CODE_DETAIL));
        mRvSearchResult.setLayoutManager(
                new StaggeredGridLayoutManager(SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL));
        mRvSearchResult.setAdapter(mAdapter);
    }

    @OnActivityResult(requestCode = CODE_DETAIL, resultCodes = { Activity.RESULT_OK })
    void onDetail(@ExtraString(name = RESULT_KEY_DUMMY) final String dummy) {
        mToastUtil.toast("onDetailOk " + dummy);
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search_github_user, menu);
        final MenuItem search = menu.findItem(R.id.mActionSearch);
        mSearchView = (SearchView) MenuItemCompat.getActionView(search);
        mPresenter.onViewReady();
    }

    @Override
    public Flowable<CharSequence> onQueryChanges() {
        return RxJavaInterop.toV2Flowable(RxSearchView.queryTextChanges(mSearchView));
    }

    @Override
    public void showSearchResult(final List<GithubUser> users) {
        mAdapter.showUsers(users);
    }

    @Override
    public void showError(final String message) {
        mToastUtil.toast(message);
    }

    @Override
    protected void injectDependencies(final SearchComponent component) {
        component.inject(this);
    }
}
