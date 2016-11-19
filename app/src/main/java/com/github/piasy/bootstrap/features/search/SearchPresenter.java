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

import android.annotation.SuppressLint;
import android.text.TextUtils;
import com.github.piasy.bootstrap.model.errors.RxNetErrorProcessor;
import com.github.piasy.bootstrap.model.users.GithubUser;
import com.github.piasy.bootstrap.model.users.GithubUserRepo;
import com.github.piasy.yamvp.dagger2.ActivityScope;
import com.github.piasy.yamvp.rx.YaRxPresenter;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;

/**
 * Created by Piasy{github.com/Piasy} on 3/6/16.
 */
@ActivityScope
public class SearchPresenter extends YaRxPresenter<SearchUserView> {
    private static final int SEARCH_DELAY_MILLIS = 500;

    private final GithubUserRepo mGithubUserRepo;
    private final RxNetErrorProcessor mRxNetErrorProcessor;
    private String mQuery;

    @Inject
    SearchPresenter(final GithubUserRepo githubUserRepo,
            final RxNetErrorProcessor rxNetErrorProcessor) {
        super();
        mGithubUserRepo = githubUserRepo;
        mRxNetErrorProcessor = rxNetErrorProcessor;
    }

    // gradle build will compile code use `Objects.requireNonNull()`
    @SuppressLint("NewApi")
    void onViewReady() {
        addUtilStop(getView().onQueryChanges()
                .observeOn(AndroidSchedulers.mainThread())
                .debounce(SEARCH_DELAY_MILLIS, TimeUnit.MILLISECONDS)
                .flatMap(query -> {
                    if (TextUtils.equals(mQuery, query)) {
                        return Flowable.empty();
                    }
                    mQuery = query.toString();
                    if (TextUtils.isEmpty(mQuery)) {
                        return Flowable.<List<GithubUser>>just(Collections.emptyList());
                    }
                    return mGithubUserRepo.searchUser(mQuery);
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getView()::showSearchResult,
                        t -> mRxNetErrorProcessor.tryWithApiError(t,
                                e -> getView().showError(e.message()))));
    }
}
