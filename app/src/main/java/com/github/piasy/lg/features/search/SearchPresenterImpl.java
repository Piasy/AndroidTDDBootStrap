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

package com.github.piasy.lg.features.search;

import android.text.TextUtils;
import com.github.piasy.lg.features.search.mvp.SearchPresenter;
import com.github.piasy.lg.features.search.mvp.SearchUserView;
import com.github.piasy.base.mvp.NullObjRxBasePresenter;
import com.github.piasy.model.errors.RxNetErrorProcessor;
import com.github.piasy.model.users.GithubUser;
import com.github.piasy.model.users.dao.GithubUserDao;
import java.util.Collections;
import java.util.List;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Piasy{github.com/Piasy} on 3/6/16.
 */
public class SearchPresenterImpl extends NullObjRxBasePresenter<SearchUserView>
        implements SearchPresenter {
    private final GithubUserDao mGithubUserDao;
    private final RxNetErrorProcessor mRxNetErrorProcessor;
    private String mQuery;

    public SearchPresenterImpl(final GithubUserDao githubUserDao,
            final RxNetErrorProcessor rxNetErrorProcessor) {
        super();
        mGithubUserDao = githubUserDao;
        mRxNetErrorProcessor = rxNetErrorProcessor;
    }

    @Override
    public void searchUser(final String query) {
        if (!TextUtils.equals(mQuery, query)) {
            mQuery = query;
            if (TextUtils.isEmpty(mQuery)) {
                getView().showSearchResult(Collections.<GithubUser>emptyList());
            } else {
                addSubscription(mGithubUserDao.searchUser(query)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<List<GithubUser>>() {
                            @Override
                            public void call(final List<GithubUser> users) {
                                getView().showSearchResult(users);
                            }
                        }, mRxNetErrorProcessor));
            }
        }
    }
}
