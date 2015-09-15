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

package com.github.piasy.model.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;
import com.github.piasy.model.entities.GithubUser;
import com.pushtorefresh.storio.sqlite.operations.delete.DefaultDeleteResolver;
import com.pushtorefresh.storio.sqlite.operations.delete.DeleteResolver;
import com.pushtorefresh.storio.sqlite.operations.get.DefaultGetResolver;
import com.pushtorefresh.storio.sqlite.operations.get.GetResolver;
import com.pushtorefresh.storio.sqlite.operations.put.DefaultPutResolver;
import com.pushtorefresh.storio.sqlite.operations.put.PutResolver;
import com.pushtorefresh.storio.sqlite.queries.DeleteQuery;
import com.pushtorefresh.storio.sqlite.queries.InsertQuery;
import com.pushtorefresh.storio.sqlite.queries.Query;
import com.pushtorefresh.storio.sqlite.queries.UpdateQuery;
import org.threeten.bp.Instant;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;

/**
 * Created by piasy on 15/8/10.
 *
 * {@link com.pushtorefresh.storio.sqlite.StorIOSQLite} table meta info holder class
 */
public final class GithubUserTableMeta {

    /**
     * table name
     */
    @NonNull
    public static final String TABLE = "GithubUsers";

    /**
     * login column
     */
    @NonNull
    public static final String COLUMN_LOGIN = "login";

    /**
     * id column
     */
    @NonNull
    public static final String COLUMN_ID = "id";

    /**
     * avatar_url column
     */
    @NonNull
    public static final String COLUMN_AVATAR_URL = "avatar_url";

    /**
     * type column
     */
    @NonNull
    public static final String COLUMN_TYPE = "type";

    /**
     * email column
     */
    @NonNull
    public static final String COLUMN_EMAIL = "email";

    /**
     * followers column
     */
    @NonNull
    public static final String COLUMN_FOLLOWERS = "followers";

    /**
     * following column
     */
    @NonNull
    public static final String COLUMN_FOLLOWING = "following";

    /**
     * created_at column
     */
    @NonNull
    public static final String COLUMN_CREATED_AT = "created_at";

    /**
     * query all StorIOSQLite {@link Query}
     */
    @NonNull
    public static final Query QUERY_ALL = Query.builder().table(TABLE).build();

    /**
     * StorIOSQLite {@link PutResolver}
     */
    @NonNull
    public static final PutResolver<GithubUser> GITHUB_USER_PUT_RESOLVER =
            new DefaultPutResolver<GithubUser>() {
                @NonNull
                @Override
                protected InsertQuery mapToInsertQuery(@NonNull final GithubUser user) {
                    return InsertQuery.builder().table(TABLE).build();
                }

                @NonNull
                @Override
                protected UpdateQuery mapToUpdateQuery(@NonNull final GithubUser user) {
                    return UpdateQuery.builder()
                            .table(TABLE)
                            .where(COLUMN_ID + " = ?")
                            .whereArgs(user.id())
                            .build();
                }

                @NonNull
                @Override
                protected ContentValues mapToContentValues(@NonNull final GithubUser user) {
                    final ContentValues contentValues = new ContentValues(8);

                    contentValues.put(COLUMN_ID, user.id());
                    contentValues.put(COLUMN_LOGIN, user.login());
                    contentValues.put(COLUMN_AVATAR_URL, user.avatar_url());
                    contentValues.put(COLUMN_TYPE, user.type());
                    contentValues.put(COLUMN_EMAIL, user.email());
                    contentValues.put(COLUMN_FOLLOWERS, user.followers());
                    contentValues.put(COLUMN_FOLLOWING, user.following());
                    contentValues.put(COLUMN_CREATED_AT, user.created_at() == null ? -1 :
                            user.created_at().toInstant().toEpochMilli());

                    return contentValues;
                }
            };

    /**
     * StorIOSQLite {@link GetResolver}
     */
    @NonNull
    public static final GetResolver<GithubUser> GITHUB_USER_GET_RESOLVER =
            new DefaultGetResolver<GithubUser>() {
                @NonNull
                @Override
                public GithubUser mapFromCursor(@NonNull final Cursor cursor) {
                    return GithubUser.builder()
                            .id(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)))
                            .login(cursor.getString(cursor.getColumnIndex(COLUMN_LOGIN)))
                            .avatar_url(cursor.getString(cursor.getColumnIndex(COLUMN_AVATAR_URL)))
                            .type(cursor.getString(cursor.getColumnIndex(COLUMN_TYPE)))
                            .email(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)))
                            .followers(cursor.getInt(cursor.getColumnIndex(COLUMN_FOLLOWERS)))
                            .following(cursor.getInt(cursor.getColumnIndex(COLUMN_FOLLOWING)))
                            .created_at(ZonedDateTime.ofInstant(Instant.ofEpochMilli(cursor.getLong(
                                            cursor.getColumnIndex(COLUMN_CREATED_AT))),
                                    ZoneId.systemDefault()))
                            .build();
                }
            };

    /**
     * StorIOSQLite {@link DeleteResolver}
     */
    @NonNull
    public static final DeleteResolver<GithubUser> GITHUB_USER_DELETE_RESOLVER =
            new DefaultDeleteResolver<GithubUser>() {
                @NonNull
                @Override
                protected DeleteQuery mapToDeleteQuery(@NonNull final GithubUser user) {
                    return DeleteQuery.builder()
                            .table(TABLE)
                            .where(COLUMN_ID + " = ?")
                            .whereArgs(user.id())
                            .build();
                }
            };

    // This is just class with Meta Data, we don't need instances
    private GithubUserTableMeta() {
        throw new IllegalStateException("No instances please");
    }

    @NonNull
    public static String getCreateGithubUserTableSQL() {
        return CreateGithubUserTableSQLHolder.CREATE_GITHUB_USER_TABLE_SQL;
    }

    @NonNull
    public static DeleteQuery getDeleteAllQuery() {
        return DeleteAllQueryHolder.DELETE_ALL_QUERY;
    }

    private static class CreateGithubUserTableSQLHolder {
        // lazy instantiate
        private static final String CREATE_GITHUB_USER_TABLE_SQL = "CREATE TABLE " + TABLE + "(" +
                COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY, " +
                COLUMN_LOGIN + " TEXT NOT NULL, " +
                COLUMN_AVATAR_URL + " TEXT NOT NULL, " +
                COLUMN_TYPE + " TEXT NOT NULL, " +
                COLUMN_EMAIL + " TEXT, " +
                COLUMN_FOLLOWERS + " INTEGER, " +
                COLUMN_FOLLOWING + " INTEGER, " +
                COLUMN_CREATED_AT + " INTEGER" +
                ");";
    }

    private static class DeleteAllQueryHolder {
        // lazy instantiate
        private static final DeleteQuery DELETE_ALL_QUERY =
                DeleteQuery.builder().table(TABLE).build();
    }
}
