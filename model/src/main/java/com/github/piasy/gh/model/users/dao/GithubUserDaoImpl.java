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

package com.github.piasy.gh.model.users.dao;

import android.support.annotation.NonNull;
import com.github.piasy.base.di.ActivityScope;
import com.github.piasy.gh.model.users.GithubApi;
import com.github.piasy.gh.model.users.GithubUser;
import com.github.piasy.gh.model.users.GithubUserSearchResult;
import java.util.List;
import javax.inject.Inject;
import rx.Observable;

/**
 * Created by Piasy{github.com/Piasy} on 15/8/5.
 *
 * Implementation of {@link GithubUserDao}.
 */
@ActivityScope
public final class GithubUserDaoImpl implements GithubUserDao {

    private final DbUserDelegate mDbUserDelegate;
    private final GithubApi mGithubApi;

    @Inject
    public GithubUserDaoImpl(final DbUserDelegate dbUserDelegate, final GithubApi githubApi) {
        mDbUserDelegate = dbUserDelegate;
        mGithubApi = githubApi;
    }

    @NonNull
    @Override
    public Observable<List<GithubUser>> searchUser(@NonNull final String query) {
        return mGithubApi.searchGithubUsers(query, GithubApi.GITHUB_API_PARAMS_SEARCH_SORT_JOINED,
                GithubApi.GITHUB_API_PARAMS_SEARCH_ORDER_DESC)
                .map(GithubUserSearchResult::items)
                .doOnNext(mDbUserDelegate::putAllGithubUser);
    }
}
