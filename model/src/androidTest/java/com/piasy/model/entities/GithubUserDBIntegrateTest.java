package com.piasy.model.entities;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import com.piasy.model.dao.GithubUserTableMeta;
import com.piasy.model.db.DBOpenHelper;
import com.pushtorefresh.storio.sqlite.SQLiteTypeMapping;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.impl.DefaultStorIOSQLite;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by piasy on 15/8/11.
 */
@RunWith(AndroidJUnit4.class)
public class GithubUserDBIntegrateTest {

    private StorIOSQLite mStorIOSQLite;

    @Before
    public void setUp() {
        mStorIOSQLite = DefaultStorIOSQLite.builder()
                .sqliteOpenHelper(new DBOpenHelper(InstrumentationRegistry.getContext()))
                .addTypeMapping(GithubUser.class, SQLiteTypeMapping.<GithubUser>builder()
                        .putResolver(GithubUserTableMeta.sGithubUserPutResolver)
                        .getResolver(GithubUserTableMeta.sGithubUserGetResolver)
                        .deleteResolver(GithubUserTableMeta.sGithubUserDeleteResolver)
                        .build())
                .build();

        // Clearing books table before each test case
        mStorIOSQLite.delete()
                .byQuery(GithubUserTableMeta.getDeleteAllQuery())
                .prepare()
                .executeAsBlocking();
    }

    @Test
    public void testInsert() {
        // TODO
    }
}
