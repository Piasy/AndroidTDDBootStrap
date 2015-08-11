package com.piasy.model.dao.di;

import com.piasy.model.dao.DBOpenHelper;
import com.piasy.model.dao.GithubUserTableMeta;
import com.piasy.model.entities.GithubUser;
import com.pushtorefresh.storio.sqlite.SQLiteTypeMapping;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.impl.DefaultStorIOSQLite;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by piasy on 15/8/10.
 */
@Module
public class DBModule {

    @Provides
    @NonNull
    @Singleton
    StorIOSQLite provideStorIOSQLite(@NonNull SQLiteOpenHelper sqLiteOpenHelper) {
        return DefaultStorIOSQLite.builder()
                .sqliteOpenHelper(sqLiteOpenHelper)
                .addTypeMapping(GithubUser.class, SQLiteTypeMapping.<GithubUser>builder()
                        .putResolver(GithubUserTableMeta.sGithubUserPutResolver)
                        .getResolver(GithubUserTableMeta.sGithubUserGetResolver)
                        .deleteResolver(GithubUserTableMeta.sGithubUserDeleteResolver)
                        .build())
                .build();
    }

    @Provides
    @NonNull
    @Singleton
    SQLiteOpenHelper provideSQSqLiteOpenHelper(@NonNull Context context) {
        return new DBOpenHelper(context);
    }

}
