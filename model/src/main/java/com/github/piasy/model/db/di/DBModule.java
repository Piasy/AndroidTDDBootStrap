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

    @Provides
    @NonNull
    @Singleton
    StorIOSQLiteDelegate provideStorIOSQLiteDelegate(@NonNull StorIOSQLite storIOSQLite) {
        return new StorIOSQLiteDelegateImpl(storIOSQLite);
    }
}
