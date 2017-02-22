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

import android.database.Cursor;
import android.support.annotation.VisibleForTesting;
import com.github.piasy.bootstrap.base.model.jsr310.ZonedDateTimeDelightAdapter;
import com.github.piasy.yamvp.dagger2.ActivityScope;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqldelight.RowMapper;
import hu.akarnokd.rxjava.interop.RxJavaInterop;
import io.reactivex.Observable;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import org.threeten.bp.format.DateTimeFormatter;

/**
 * Created by Piasy{github.com/Piasy} on 15/8/14.
 */
@ActivityScope
class UserDbSource {

    private final BriteDatabase mBriteDb;

    private final RowMapper<GitHubUser> mUserMapper;

    private final GitHubUser.Insert_user mInsertion;
    private final GitHubUser.Delete_by_login mDeletion;

    @Inject
    UserDbSource(final DateTimeFormatter dateTimeFormatter, final BriteDatabase briteDb) {
        mBriteDb = briteDb;

        final GitHubUser.Factory<GitHubUser> userFactory = new GitHubUser.Factory<>(
                AutoValue_GitHubUser::new,
                new ZonedDateTimeDelightAdapter(dateTimeFormatter));
        mUserMapper = userFactory.select_by_loginMapper();

        mDeletion = new GitHubUser.Delete_by_login(mBriteDb.getWritableDatabase());
        mInsertion = new GitHubUser.Insert_user(mBriteDb.getWritableDatabase(), userFactory);
    }

    List<GitHubUser> getNow(final String login) {
        final Cursor cursor = mBriteDb.query(GitHubUser.SELECT_BY_LOGIN, login);
        try {
            if (cursor.moveToFirst()) {
                return Collections.singletonList(mUserMapper.map(cursor));
            }
            return Collections.emptyList();
        } finally {
            cursor.close();
        }
    }

    Observable<GitHubUser> get(final String login) {
        return Observable.fromCallable(() -> getNow(login))
                .filter(list -> !list.isEmpty())
                .map(list -> list.get(0));
    }

    Observable<GitHubUser> observe(final String login) {
        return RxJavaInterop.toV2Observable(
                mBriteDb.createQuery(GitHubUser.TABLE_NAME, GitHubUser.SELECT_BY_LOGIN, login)
                        .mapToOneOrDefault(mUserMapper::map, UserRepo.INVALID_USER)
                        .filter(user -> user != UserRepo.INVALID_USER));
    }

    void put(final List<GitHubUser> users) {
        final BriteDatabase.Transaction transaction = mBriteDb.newTransaction();
        try {
            final int size = users.size();
            for (int i = 0; i < size; i++) {
                mBriteDb.executeInsert(GitHubUser.TABLE_NAME, users.get(i).insert(mInsertion));
            }
            transaction.markSuccessful();
        } finally {
            transaction.end();
        }
    }

    void put(final GitHubUser user) {
        mBriteDb.executeInsert(GitHubUser.TABLE_NAME, user.insert(mInsertion));
    }

    int delete(final String login) {
        return mBriteDb.executeUpdateDelete(GitHubUser.TABLE_NAME,
                GitHubUser.delete(mDeletion, login));
    }

    @VisibleForTesting
    int deleteAll() {
        return mBriteDb.delete(GitHubUser.TABLE_NAME, "1");
    }
}
