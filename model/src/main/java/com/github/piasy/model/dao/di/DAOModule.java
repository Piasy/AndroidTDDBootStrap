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

package com.github.piasy.model.dao.di;

import com.github.piasy.common.android.utils.net.RxUtil;
import com.github.piasy.model.dao.GithubUserDAO;
import com.github.piasy.model.dao.GithubUserDAOImpl;
import com.github.piasy.model.db.StorIOSQLiteDelegate;
import com.github.piasy.model.rest.github.GithubAPI;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Piasy{github.com/Piasy} on 15/8/5.
 *
 * DAO module class for dagger2.
 */
@Module
public class DAOModule {

    /**
     * Provide {@link GithubUserDAO} with the given {@link StorIOSQLiteDelegate}, {@link GithubAPI}
     * and {@link RxUtil.RxErrorProcessor}.
     *
     * @param storIOSQLite stor io SQLite delegate.
     * @param githubAPI GithubAPI instance.
     * @param rxErrorProcessor Rx error processor.
     * @return GithubUserDAO instance.
     */
    @Provides
    GithubUserDAO provideGithubUserDAO(final StorIOSQLiteDelegate storIOSQLite,
            final GithubAPI githubAPI, final RxUtil.RxErrorProcessor rxErrorProcessor) {
        return new GithubUserDAOImpl(storIOSQLite, githubAPI, rxErrorProcessor);
    }
}
