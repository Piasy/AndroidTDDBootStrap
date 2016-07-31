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

package com.github.piasy.gh.model.users;

import android.database.sqlite.SQLiteDatabase;
import com.github.piasy.base.di.ActivityScope;
import com.squareup.sqlbrite.BriteDatabase;
import java.util.List;
import javax.inject.Inject;
import rx.Observable;

/**
 * Created by Piasy{github.com/Piasy} on 15/8/14.
 */
@ActivityScope
public final class DbUserDelegateImpl implements DbUserDelegate {
    private final BriteDatabase mBriteDb;

    @Inject
    DbUserDelegateImpl(final BriteDatabase briteDb) {
        this.mBriteDb = briteDb;
    }

    @Override
    public void deleteAllGithubUser() {
        mBriteDb.execute(GithubUser.DELETE_ALL);
    }

    @SuppressWarnings({
            "PMD.OneDeclarationPerLine", "PMD.LocalVariableCouldBeFinal",
            "PMD.DataflowAnomalyAnalysis"
    })
    @Override
    public void putAllGithubUser(final List<GithubUser> users) {
        final BriteDatabase.Transaction transaction = mBriteDb.newTransaction();
        try {
            for (int i = 0, size = users.size(); i < size; i++) {
                mBriteDb.insert(GithubUser.TABLE_NAME,
                        GithubUser.FACTORY.marshal(users.get(i)).asContentValues(),
                        SQLiteDatabase.CONFLICT_REPLACE);
            }
            transaction.markSuccessful();
        } finally {
            transaction.end();
        }
    }

    @Override
    public Observable<List<GithubUser>> getAllGithubUser() {
        return mBriteDb.createQuery(GithubUser.TABLE_NAME, GithubUser.GET_ALL)
                .mapToList(GithubUser.MAPPER::map);
    }
}
