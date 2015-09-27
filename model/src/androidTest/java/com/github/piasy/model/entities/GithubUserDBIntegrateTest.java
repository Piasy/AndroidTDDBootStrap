package com.github.piasy.model.entities;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import com.github.piasy.common.android.utils.tests.BaseThreeTenBPAndroidTest;
import com.github.piasy.model.db.StorIOSQLiteDelegate;
import com.github.piasy.model.db.StorIOSQLiteDelegateImpl;
import com.github.piasy.model.db.di.DBModuleExposure;
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
public class GithubUserDBIntegrateTest extends BaseThreeTenBPAndroidTest {
    private static final String LOGIN = "Piasy";
    private static final String AVATAR = "avatar";
    private static final String TYPE = "User";
    private static final String EMAIL = "xz4215@gmail.com";
    private StorIOSQLiteDelegate mStorIOSQLiteDelegate;

    @Before
    public void setUp() {
        initThreeTenABP(InstrumentationRegistry.getContext());

        final StorIOSQLite storIOSQLite =
                DBModuleExposure.exposeStorIOSQLite(InstrumentationRegistry.getContext());
        mStorIOSQLiteDelegate = new StorIOSQLiteDelegateImpl(storIOSQLite);

        // Clearing table before each test case
        mStorIOSQLiteDelegate.deleteAllGithubUser();
    }

    @Test
    public void testInsert() {
        List<GithubUser> storedUsers = mStorIOSQLiteDelegate.getAllGithubUser();
        Assert.assertTrue(storedUsers.isEmpty());

        final List<GithubUser> users = new ArrayList<>();
        final GithubUser user = GithubUser.builder()
                .id(1)
                .login(LOGIN)
                .avatar_url(AVATAR)
                .type(TYPE)
                .followers(3)
                .following(25)
                .email(EMAIL)
                .created_at(ZonedDateTime.now())
                .build();
        users.add(user);
        final PutResults<GithubUser> results = mStorIOSQLiteDelegate.putAllGithubUser(users);

        Assert.assertEquals(1, results.numberOfInserts());
        Assert.assertEquals(0, results.numberOfUpdates());

        storedUsers = mStorIOSQLiteDelegate.getAllGithubUser();
        Assert.assertEquals(1, storedUsers.size());
        Assert.assertEquals(user, storedUsers.get(0));
    }

    @Test
    public void testUpdate() {
        List<GithubUser> storedUsers = mStorIOSQLiteDelegate.getAllGithubUser();
        Assert.assertTrue(storedUsers.isEmpty());

        final List<GithubUser> users = new ArrayList<>();
        final GithubUser user = GithubUser.builder()
                .id(1)
                .login(LOGIN)
                .avatar_url(AVATAR)
                .type(TYPE)
                .followers(3)
                .following(25)
                .email(EMAIL)
                .created_at(ZonedDateTime.now())
                .build();
        users.add(user);
        PutResults<GithubUser> results = mStorIOSQLiteDelegate.putAllGithubUser(users);

        Assert.assertEquals(1, results.numberOfInserts());
        Assert.assertEquals(0, results.numberOfUpdates());

        storedUsers = mStorIOSQLiteDelegate.getAllGithubUser();
        Assert.assertEquals(1, storedUsers.size());
        Assert.assertEquals(user, storedUsers.get(0));

        final GithubUser altered = GithubUser.builder()
                .id(1)
                .login(LOGIN)
                .avatar_url(AVATAR)
                .type(TYPE)
                .followers(4)
                .following(25)
                .email("i@piasy.com")
                .created_at(ZonedDateTime.now())
                .build();
        users.set(0, altered);
        results = mStorIOSQLiteDelegate.putAllGithubUser(users);

        Assert.assertEquals(0, results.numberOfInserts());
        Assert.assertEquals(1, results.numberOfUpdates());

        storedUsers = mStorIOSQLiteDelegate.getAllGithubUser();
        Assert.assertEquals(1, storedUsers.size());
        Assert.assertFalse(user.equals(storedUsers.get(0)));
        Assert.assertEquals(altered, storedUsers.get(0));
    }

    @Test
    public void testDelete() {
        List<GithubUser> storedUsers = mStorIOSQLiteDelegate.getAllGithubUser();
        Assert.assertTrue(storedUsers.isEmpty());

        final List<GithubUser> users = new ArrayList<>();
        final GithubUser user = GithubUser.builder()
                .id(1)
                .login(LOGIN)
                .avatar_url(AVATAR)
                .type(TYPE)
                .followers(3)
                .following(25)
                .email(EMAIL)
                .created_at(ZonedDateTime.now())
                .build();
        users.add(user);
        final PutResults<GithubUser> results = mStorIOSQLiteDelegate.putAllGithubUser(users);

        Assert.assertEquals(1, results.numberOfInserts());
        Assert.assertEquals(0, results.numberOfUpdates());

        storedUsers = mStorIOSQLiteDelegate.getAllGithubUser();
        Assert.assertEquals(1, storedUsers.size());
        Assert.assertEquals(user, storedUsers.get(0));

        final DeleteResult deleteResult = mStorIOSQLiteDelegate.deleteAllGithubUser();
        Assert.assertEquals(1, deleteResult.numberOfRowsDeleted());
        storedUsers = mStorIOSQLiteDelegate.getAllGithubUser();
        Assert.assertEquals(0, storedUsers.size());
    }
}
