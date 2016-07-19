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

package com.github.piasy.gh.features.search;

import android.text.TextUtils;
import com.github.piasy.base.di.ActivityScope;
import com.github.piasy.base.mvp.NullObjRxBasePresenter;
import com.github.piasy.gh.features.search.mvp.SearchPresenter;
import com.github.piasy.gh.features.search.mvp.SearchUserView;
import com.github.piasy.gh.model.errors.RxNetErrorProcessor;
import com.github.piasy.gh.model.users.GithubUserRepo;
import java.util.Collections;
import javax.inject.Inject;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Piasy{github.com/Piasy} on 3/6/16.
 */
@ActivityScope
public class SearchPresenterImpl extends NullObjRxBasePresenter<SearchUserView>
        implements SearchPresenter {
    private final GithubUserRepo mGithubUserRepo;
    private final RxNetErrorProcessor mRxNetErrorProcessor;
    private String mQuery;

    @Inject
    public SearchPresenterImpl(final GithubUserRepo githubUserRepo,
            final RxNetErrorProcessor rxNetErrorProcessor) {
        super();
        mGithubUserRepo = githubUserRepo;
        mRxNetErrorProcessor = rxNetErrorProcessor;
    }

    @Override
    public void searchUser(final String query) {
        if (!TextUtils.equals(mQuery, query)) {
            mQuery = query;
            if (TextUtils.isEmpty(mQuery)) {
                getView().showSearchResult(Collections.emptyList());
            } else {
                addSubscription(mGithubUserRepo.searchUser(query)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(getView()::showSearchResult,
                                t -> mRxNetErrorProcessor.tryWithApiError(t,
                                        e -> getView().showError(e.message()))));
            }
        }
    }
}
