package com.piasy.model.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;
import com.piasy.model.entities.GithubUser;
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

/**
 * Created by piasy on 15/8/10.
 */
public class GithubUserTableMeta {
    @NonNull
    public static final String TABLE = "GithubUsers";

    @NonNull
    public static final String COLUMN_LOGIN = "login";

    @NonNull
    public static final String COLUMN_ID = "id";

    @NonNull
    public static final String COLUMN_AVATAR_URL = "avatar_url";

    @NonNull
    public static final String COLUMN_TYPE = "type";

    @NonNull
    public static final String COLUMN_EMAIL = "email";

    @NonNull
    public static final String COLUMN_FOLLOWERS = "followers";

    @NonNull
    public static final String COLUMN_FOLLOWING = "following";

    @NonNull
    public static final String COLUMN_CREATED_AT = "created_at";
    @NonNull
    public static final Query sQueryAll = Query.builder().table(TABLE).build();
    @NonNull
    public static final PutResolver<GithubUser> sGithubUserPutResolver =
            new DefaultPutResolver<GithubUser>() {
                @NonNull
                @Override
                protected InsertQuery mapToInsertQuery(@NonNull GithubUser user) {
                    return InsertQuery.builder().table(TABLE).build();
                }

                @NonNull
                @Override
                protected UpdateQuery mapToUpdateQuery(@NonNull GithubUser user) {
                    return UpdateQuery.builder()
                            .table(TABLE)
                            .where(COLUMN_ID + " = ?")
                            .whereArgs(user.id())
                            .build();
                }

                @NonNull
                @Override
                protected ContentValues mapToContentValues(@NonNull GithubUser user) {
                    final ContentValues contentValues = new ContentValues(8);

                    contentValues.put(COLUMN_ID, user.id());
                    contentValues.put(COLUMN_LOGIN, user.login());
                    contentValues.put(COLUMN_AVATAR_URL, user.avatar_url());
                    contentValues.put(COLUMN_TYPE, user.type());
                    contentValues.put(COLUMN_EMAIL, user.email());
                    contentValues.put(COLUMN_FOLLOWERS, user.followers());
                    contentValues.put(COLUMN_FOLLOWING, user.following());
                    contentValues.put(COLUMN_CREATED_AT,
                            user.created_at() == null ? -1 : user.created_at().getTime());

                    return contentValues;
                }
            };
    @NonNull
    public static final GetResolver<GithubUser> sGithubUserGetResolver =
            new DefaultGetResolver<GithubUser>() {
                @NonNull
                @Override
                public GithubUser mapFromCursor(@NonNull Cursor cursor) {
                    return GithubUser.builder()
                            .id(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)))
                            .login(cursor.getString(cursor.getColumnIndex(COLUMN_LOGIN)))
                            .avatar_url(cursor.getString(cursor.getColumnIndex(COLUMN_AVATAR_URL)))
                            .type(cursor.getString(cursor.getColumnIndex(COLUMN_TYPE)))
                            .email(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)))
                            .followers(cursor.getInt(cursor.getColumnIndex(COLUMN_FOLLOWERS)))
                            .following(cursor.getInt(cursor.getColumnIndex(COLUMN_FOLLOWING)))
                            .build();
                }
            };
    @NonNull
    public static final DeleteResolver<GithubUser> sGithubUserDeleteResolver =
            new DefaultDeleteResolver<GithubUser>() {
                @NonNull
                @Override
                protected DeleteQuery mapToDeleteQuery(@NonNull GithubUser user) {
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
        return CreateGithubUserTableSQLHolder.sCreateGithubUserTableSQL;
    }

    @NonNull
    public static DeleteQuery getDeleteAllQuery() {
        return DeleteAllQueryHolder.sDeleteAllQuery;
    }

    private static class CreateGithubUserTableSQLHolder {
        // lazy instantiate
        private static final String sCreateGithubUserTableSQL = "CREATE TABLE " + TABLE + "(" +
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
        private static final DeleteQuery sDeleteAllQuery =
                DeleteQuery.builder().table(TABLE).build();
    }
}
