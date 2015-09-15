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

package com.github.piasy.model.db;

import com.github.piasy.model.dao.GithubUserTableMeta;
import com.github.piasy.model.entities.GithubUser;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.operations.delete.DeleteResult;
import com.pushtorefresh.storio.sqlite.operations.put.PutResults;
import java.util.List;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by Piasy{github.com/Piasy} on 15/8/14.
 *
 * Implementation of {@link StorIOSQLiteDelegate}.
 */
public final class StorIOSQLiteDelegateImpl implements StorIOSQLiteDelegate {
    private final StorIOSQLite mStorIOSQLite;

    /**
     * Create instance with the {@link StorIOSQLite} to delegate.
     *
     * @param mStorIOSQLite the {@link StorIOSQLite} to delegate.
     */
    public StorIOSQLiteDelegateImpl(final StorIOSQLite mStorIOSQLite) {
        this.mStorIOSQLite = mStorIOSQLite;
    }

    @Override
    public DeleteResult deleteAllGithubUser() {
        return mStorIOSQLite.delete()
                .byQuery(GithubUserTableMeta.getDeleteAllQuery())
                .prepare()
                .executeAsBlocking();
    }

    @Override
    public PutResults<GithubUser> putAllGithubUser(final List<GithubUser> users) {
        return mStorIOSQLite.put().objects(users).prepare().executeAsBlocking();
    }

    @Override
    public List<GithubUser> getAllGithubUser() {
        return mStorIOSQLite.get()
                .listOfObjects(GithubUser.class)
                .withQuery(GithubUserTableMeta.QUERY_ALL)
                .prepare()
                .executeAsBlocking();
    }

    @Override
    public Observable<List<GithubUser>> getAllGithubUserReactively() {
        return mStorIOSQLite.get()
                .listOfObjects(GithubUser.class)
                .withQuery(GithubUserTableMeta.QUERY_ALL)
                .prepare()
                .createObservable()
                .subscribeOn(Schedulers.io());
    }
}
