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

package com.github.piasy.octostars.users;

import com.github.piasy.bootstrap.base.utils.RxUtil;
import com.github.piasy.yamvp.dagger2.ActivityScope;
import io.reactivex.Observable;
import javax.inject.Inject;

/**
 * Created by Piasy{github.com/Piasy} on 15/8/5.
 */
@ActivityScope
public class UserRepo {

    public static final String TYPE_USER = "User";
    public static final String TYPE_ORG = "Organization";

    public static final String ICONS_USER = "{md-person}";
    public static final String ICONS_ORG = "{md-people}";

    public static final long INVALID_ID = -1;
    public static final GitHubUser INVALID_USER = GitHubUser.fake(INVALID_ID, "fake", TYPE_USER);

    private final UserDbSource mUserDbSource;
    private final UserApiSource mUserApiSource;

    @Inject
    UserRepo(final UserDbSource userDbSource, final UserApiSource userApiSource) {
        mUserDbSource = userDbSource;
        mUserApiSource = userApiSource;
    }

    public Observable<GitHubUser> get(final String login, final boolean refresh) {
        return RxUtil.repoGet(
                mUserDbSource.get(login),
                mUserApiSource.user(login)
                        .doOnNext(mUserDbSource::put),
                refresh
        );
    }

    public void put(final GitHubUser user) {
        mUserDbSource.put(user);
    }
}
