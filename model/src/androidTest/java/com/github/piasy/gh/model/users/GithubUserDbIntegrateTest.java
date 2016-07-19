package com.github.piasy.gh.model.users;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import com.github.piasy.base.model.provider.DbModuleExposure;
import com.github.piasy.base.test.BaseThreeTenBPAndroidTest;
import com.github.piasy.gh.model.DaoModuleExposure;
import com.squareup.sqlbrite.BriteDatabase;
import java.util.ArrayList;
import java.util.List;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.threeten.bp.ZoneId;
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
    private ZonedDateTime mDate;

    @Before
    public void setUp() {
        initThreeTenABP(InstrumentationRegistry.getContext());

        final BriteDatabase briteDb = DbModuleExposure.exposeBriteDb(
                DaoModuleExposure.exposeBriteDbConfig(InstrumentationRegistry.getContext()));
        mDbUserDelegate = new DbUserDelegateImpl(briteDb);
        mDate = ZonedDateTime.of(2016, 5, 6, 0, 1, 0, 0, ZoneId.systemDefault());

        // Clearing table before each test case
        mDbUserDelegate.deleteAllGithubUser();
    }

    @Test
    public void testInsert() {
        List<GithubUser> storedUsers = mDbUserDelegate.getAllGithubUser().toBlocking().first();
        Assert.assertTrue(storedUsers.isEmpty());

        final List<GithubUser> users = new ArrayList<>();
        final GithubUser user = GithubUser.builder()
                .id(1L)
                .login(LOGIN)
                .avatar_url(AVATAR)
                .type(TYPE)
                .created_at(mDate)
                .build();
        users.add(user);
        mDbUserDelegate.putAllGithubUser(users);

        storedUsers = mDbUserDelegate.getAllGithubUser().toBlocking().first();
        Assert.assertEquals(1, storedUsers.size());
        Assert.assertEquals(user, storedUsers.get(0));
    }

    @Test
    public void testUpdate() {
        List<GithubUser> storedUsers = mDbUserDelegate.getAllGithubUser().toBlocking().first();
        Assert.assertTrue(storedUsers.isEmpty());

        final List<GithubUser> users = new ArrayList<>();
        final GithubUser user = GithubUser.builder()
                .id(1L)
                .login(LOGIN)
                .avatar_url(AVATAR)
                .type(TYPE)
                .created_at(mDate)
                .build();
        users.add(user);
        mDbUserDelegate.putAllGithubUser(users);

        storedUsers = mDbUserDelegate.getAllGithubUser().toBlocking().first();
        Assert.assertEquals(1, storedUsers.size());
        Assert.assertEquals(user, storedUsers.get(0));

        final GithubUser altered = GithubUser.builder()
                .id(1L)
                .login(LOGIN)
                .avatar_url(AVATAR)
                .type(TYPE)
                .created_at(mDate.plusHours(1))
                .build();
        users.set(0, altered);
        mDbUserDelegate.putAllGithubUser(users);

        storedUsers = mDbUserDelegate.getAllGithubUser().toBlocking().first();
        Assert.assertEquals(1, storedUsers.size());
        Assert.assertFalse(user.equals(storedUsers.get(0)));
        Assert.assertEquals(altered, storedUsers.get(0));
    }

    @Test
    public void testDelete() {
        List<GithubUser> storedUsers = mDbUserDelegate.getAllGithubUser().toBlocking().first();
        Assert.assertTrue(storedUsers.isEmpty());

        final List<GithubUser> users = new ArrayList<>();
        final GithubUser user = GithubUser.builder()
                .id(1L)
                .login(LOGIN)
                .avatar_url(AVATAR)
                .type(TYPE)
                .created_at(mDate)
                .build();
        users.add(user);
        mDbUserDelegate.putAllGithubUser(users);

        storedUsers = mDbUserDelegate.getAllGithubUser().toBlocking().first();
        Assert.assertEquals(1, storedUsers.size());
        Assert.assertEquals(user, storedUsers.get(0));

        mDbUserDelegate.deleteAllGithubUser();
        storedUsers = mDbUserDelegate.getAllGithubUser().toBlocking().first();
        Assert.assertEquals(0, storedUsers.size());
    }
}
