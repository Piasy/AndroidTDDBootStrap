package com.github.piasy.model.users;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import com.github.piasy.base.model.provider.DbModuleExposure;
import com.github.piasy.base.test.BaseThreeTenBPAndroidTest;
import com.github.piasy.model.DaoModuleExposure;
import com.github.piasy.model.users.dao.DbUserDelegate;
import com.github.piasy.model.users.dao.DbUserDelegateImpl;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.operations.delete.DeleteResult;
import com.pushtorefresh.storio.sqlite.operations.put.PutResults;
import java.util.ArrayList;
import java.util.List;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.threeten.bp.ZonedDateTime;

/**
 * Created by piasy on 15/8/11.
 */
@RunWith(AndroidJUnit4.class)
public class GithubUserDbIntegrateTest extends BaseThreeTenBPAndroidTest {
    private static final String LOGIN = "Piasy";
    private static final String AVATAR = "avatar";
    private static final String TYPE = "User";
    private DbUserDelegate mDbUserDelegate;

    @Before
    public void setUp() {
        initThreeTenABP(InstrumentationRegistry.getContext());

        final StorIOSQLite storIOSQLite = DbModuleExposure.exposeStorIOSQLite(
                DaoModuleExposure.exposeStorIOSQLiteConfig(InstrumentationRegistry.getContext()));
        mDbUserDelegate = new DbUserDelegateImpl(storIOSQLite);

        // Clearing table before each test case
        mDbUserDelegate.deleteAllGithubUser();
    }

    @Test
    public void testInsert() {
        List<GithubUser> storedUsers = mDbUserDelegate.getAllGithubUser();
        Assert.assertTrue(storedUsers.isEmpty());

        final List<GithubUser> users = new ArrayList<>();
        final GithubUser user = GithubUser.builder()
                .login(LOGIN)
                .avatar_url(AVATAR)
                .type(TYPE)
                .created_at(ZonedDateTime.now())
                .build();
        users.add(user);
        final PutResults<GithubUser> results = mDbUserDelegate.putAllGithubUser(users);

        Assert.assertEquals(1, results.numberOfInserts());
        Assert.assertEquals(0, results.numberOfUpdates());

        storedUsers = mDbUserDelegate.getAllGithubUser();
        Assert.assertEquals(1, storedUsers.size());
        Assert.assertEquals(user, storedUsers.get(0));
    }

    @Test
    public void testUpdate() {
        List<GithubUser> storedUsers = mDbUserDelegate.getAllGithubUser();
        Assert.assertTrue(storedUsers.isEmpty());

        final List<GithubUser> users = new ArrayList<>();
        final GithubUser user = GithubUser.builder()
                .login(LOGIN)
                .avatar_url(AVATAR)
                .type(TYPE)
                .created_at(ZonedDateTime.now())
                .build();
        users.add(user);
        PutResults<GithubUser> results = mDbUserDelegate.putAllGithubUser(users);

        Assert.assertEquals(1, results.numberOfInserts());
        Assert.assertEquals(0, results.numberOfUpdates());

        storedUsers = mDbUserDelegate.getAllGithubUser();
        Assert.assertEquals(1, storedUsers.size());
        Assert.assertEquals(user, storedUsers.get(0));

        final GithubUser altered = GithubUser.builder()
                .login(LOGIN)
                .avatar_url(AVATAR)
                .type(TYPE)
                .created_at(ZonedDateTime.now())
                .build();
        users.set(0, altered);
        results = mDbUserDelegate.putAllGithubUser(users);

        Assert.assertEquals(0, results.numberOfInserts());
        Assert.assertEquals(1, results.numberOfUpdates());

        storedUsers = mDbUserDelegate.getAllGithubUser();
        Assert.assertEquals(1, storedUsers.size());
        Assert.assertFalse(user.equals(storedUsers.get(0)));
        Assert.assertEquals(altered, storedUsers.get(0));
    }

    @Test
    public void testDelete() {
        List<GithubUser> storedUsers = mDbUserDelegate.getAllGithubUser();
        Assert.assertTrue(storedUsers.isEmpty());

        final List<GithubUser> users = new ArrayList<>();
        final GithubUser user = GithubUser.builder()
                .login(LOGIN)
                .avatar_url(AVATAR)
                .type(TYPE)
                .created_at(ZonedDateTime.now())
                .build();
        users.add(user);
        final PutResults<GithubUser> results = mDbUserDelegate.putAllGithubUser(users);

        Assert.assertEquals(1, results.numberOfInserts());
        Assert.assertEquals(0, results.numberOfUpdates());

        storedUsers = mDbUserDelegate.getAllGithubUser();
        Assert.assertEquals(1, storedUsers.size());
        Assert.assertEquals(user, storedUsers.get(0));

        final DeleteResult deleteResult = mDbUserDelegate.deleteAllGithubUser();
        Assert.assertEquals(1, deleteResult.numberOfRowsDeleted());
        storedUsers = mDbUserDelegate.getAllGithubUser();
        Assert.assertEquals(0, storedUsers.size());
    }
}
