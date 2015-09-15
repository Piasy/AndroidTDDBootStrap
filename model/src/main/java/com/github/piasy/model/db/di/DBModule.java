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

package com.github.piasy.model.db.di;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import com.github.piasy.model.dao.GithubUserTableMeta;
import com.github.piasy.model.db.DBOpenHelper;
import com.github.piasy.model.db.StorIOSQLiteDelegate;
import com.github.piasy.model.db.StorIOSQLiteDelegateImpl;
import com.github.piasy.model.entities.GithubUser;
import com.pushtorefresh.storio.sqlite.SQLiteTypeMapping;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.impl.DefaultStorIOSQLite;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * Created by piasy on 15/8/10.
 *
 * DB Module class for dagger2.
 */
@Module
public class DBModule {

    /**
     * Provide {@link StorIOSQLite} with the given {@link SQLiteOpenHelper}.
     *
     * @param sqLiteOpenHelper {@link SQLiteOpenHelper} instance.
     * @return {@link StorIOSQLite} instance.
     */
    @Provides
    @NonNull
    @Singleton
    StorIOSQLite provideStorIOSQLite(@NonNull final SQLiteOpenHelper sqLiteOpenHelper) {
        return DefaultStorIOSQLite.builder()
                .sqliteOpenHelper(sqLiteOpenHelper)
                .addTypeMapping(GithubUser.class, SQLiteTypeMapping.<GithubUser>builder()
                        .putResolver(GithubUserTableMeta.GITHUB_USER_PUT_RESOLVER)
                        .getResolver(GithubUserTableMeta.GITHUB_USER_GET_RESOLVER)
                        .deleteResolver(GithubUserTableMeta.GITHUB_USER_DELETE_RESOLVER)
                        .build())
                .build();
    }

    /**
     * Provide {@link SQLiteOpenHelper} with context.
     *
     * @param context context object used to create {@link SQLiteOpenHelper}.
     * @return {@link SQLiteOpenHelper} to open SQLite db.
     */
    @Provides
    @NonNull
    @Singleton
    SQLiteOpenHelper provideSQSqLiteOpenHelper(@NonNull final Context context) {
        return new DBOpenHelper(context);
    }

    /**
     * Provide a delegate to {@link StorIOSQLite}.
     *
     * @param storIOSQLite {@link StorIOSQLite} instance to delegate to.
     * @return delegate instance.
     */
    @Provides
    @NonNull
    @Singleton
    StorIOSQLiteDelegate provideStorIOSQLiteDelegate(@NonNull final StorIOSQLite storIOSQLite) {
        return new StorIOSQLiteDelegateImpl(storIOSQLite);
    }
}
