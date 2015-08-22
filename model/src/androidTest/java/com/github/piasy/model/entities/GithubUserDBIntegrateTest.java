package com.github.piasy.model.entities;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import com.github.piasy.model.dao.GithubUserTableMeta;
import com.github.piasy.model.db.DBOpenHelper;
import com.github.piasy.model.db.StorIOSQLiteDelegate;
import com.github.piasy.model.db.StorIOSQLiteDelegateImpl;
import com.pushtorefresh.storio.sqlite.SQLiteTypeMapping;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.impl.DefaultStorIOSQLite;
import com.pushtorefresh.storio.sqlite.operations.put.PutResults;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.zone.TzdbZoneRulesProvider;
import org.threeten.bp.zone.ZoneRulesProvider;

/**
 * Created by piasy on 15/8/11.
 */
@RunWith(AndroidJUnit4.class)
public class GithubUserDBIntegrateTest {
    private static final AtomicBoolean initialized = new AtomicBoolean();
    private StorIOSQLiteDelegate mStorIOSQLiteDelegate;

    @Before
    public void setUp() {
        initThreeTenABP();

        StorIOSQLite storIOSQLite = DefaultStorIOSQLite.builder()
                .sqliteOpenHelper(new DBOpenHelper(InstrumentationRegistry.getContext()))
                .addTypeMapping(GithubUser.class, SQLiteTypeMapping.<GithubUser>builder()
                        .putResolver(GithubUserTableMeta.sGithubUserPutResolver)
                        .getResolver(GithubUserTableMeta.sGithubUserGetResolver)
                        .deleteResolver(GithubUserTableMeta.sGithubUserDeleteResolver)
                        .build())
                .build();
        mStorIOSQLiteDelegate = new StorIOSQLiteDelegateImpl(storIOSQLite);

        // Clearing table before each test case
        mStorIOSQLiteDelegate.deleteAllGithubUser();
    }

    private void initThreeTenABP() {
        if (initialized.getAndSet(true)) {
            return;
        }
        TzdbZoneRulesProvider provider;
        try {
            InputStream is = InstrumentationRegistry.getContext()
                    .getAssets()
                    .open("org/threeten/bp/TZDB.dat");
            provider = new TzdbZoneRulesProvider(is);
        } catch (IOException e) {
            throw new IllegalStateException("TZDB.dat missing from assets.", e);
        }
        ZoneRulesProvider.registerProvider(provider);
    }

    @Test
    public void testInsert() {
        List<GithubUser> storedUsers = mStorIOSQLiteDelegate.getAllGithubUser();
        Assert.assertTrue(storedUsers.isEmpty());

        List<GithubUser> users = new ArrayList<>();
        GithubUser user = GithubUser.builder()
                .id(1)
                .login("Piasy")
                .avatar_url("avatar")
                .type("User")
                .followers(3)
                .following(25)
                .email("xz4215@gmail.com")
                .created_at(ZonedDateTime.now())
                .build();
        users.add(user);
        PutResults<GithubUser> results = mStorIOSQLiteDelegate.putAllGithubUser(users);

        Assert.assertEquals(1, results.numberOfInserts());
        Assert.assertEquals(0, results.numberOfUpdates());

        storedUsers = mStorIOSQLiteDelegate.getAllGithubUser();
        Assert.assertEquals(1, storedUsers.size());
        Assert.assertEquals(user, storedUsers.get(0));
    }
}
