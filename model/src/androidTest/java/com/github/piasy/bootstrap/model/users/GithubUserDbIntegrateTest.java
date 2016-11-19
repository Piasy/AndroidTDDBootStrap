package com.github.piasy.bootstrap.model.users;

import android.support.test.InstrumentationRegistry;
import com.github.piasy.bootstrap.base.model.provider.DbModuleExposure;
import com.github.piasy.bootstrap.model.DaoModuleExposure;
import com.github.piasy.bootstrap.testbase.rules.ThreeTenBPRule;
import com.squareup.sqlbrite.BriteDatabase;
import java.util.ArrayList;
import java.util.List;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;

/**
 * Created by piasy on 15/8/11.
 */
public class GithubUserDbIntegrateTest {
    private static final String LOGIN = "Piasy";
    private static final String AVATAR = "avatar";
    private static final String TYPE = "User";

    @Rule
    public ThreeTenBPRule mThreeTenBPRule = ThreeTenBPRule.androidTest(
            InstrumentationRegistry.getContext());

    private DbUserDelegate mDbUserDelegate;
    private ZonedDateTime mDate;

    @Before
    public void setUp() {
        final BriteDatabase briteDb = DbModuleExposure.exposeBriteDb(
                DaoModuleExposure.exposeBriteDbConfig(InstrumentationRegistry.getContext()));
        mDbUserDelegate = new DbUserDelegateImpl(briteDb);
        mDate = ZonedDateTime.of(2016, 5, 6, 0, 1, 0, 0, ZoneId.systemDefault());

        // Clearing table before each test case
        mDbUserDelegate.deleteAllGithubUser();
    }

    @Test
    public void testInsert() {
        List<GithubUser> storedUsers = mDbUserDelegate.getAllGithubUser().blockingFirst();
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

        storedUsers = mDbUserDelegate.getAllGithubUser().blockingFirst();
        Assert.assertEquals(1, storedUsers.size());
        Assert.assertEquals(user, storedUsers.get(0));
    }

    @Test
    public void testUpdate() {
        List<GithubUser> storedUsers = mDbUserDelegate.getAllGithubUser().blockingFirst();
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

        storedUsers = mDbUserDelegate.getAllGithubUser().blockingFirst();
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

        storedUsers = mDbUserDelegate.getAllGithubUser().blockingFirst();
        Assert.assertEquals(1, storedUsers.size());
        Assert.assertFalse(user.equals(storedUsers.get(0)));
        Assert.assertEquals(altered, storedUsers.get(0));
    }

    @Test
    public void testDelete() {
        List<GithubUser> storedUsers = mDbUserDelegate.getAllGithubUser().blockingFirst();
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

        storedUsers = mDbUserDelegate.getAllGithubUser().blockingFirst();
        Assert.assertEquals(1, storedUsers.size());
        Assert.assertEquals(user, storedUsers.get(0));

        mDbUserDelegate.deleteAllGithubUser();
        storedUsers = mDbUserDelegate.getAllGithubUser().blockingFirst();
        Assert.assertEquals(0, storedUsers.size());
    }
}
