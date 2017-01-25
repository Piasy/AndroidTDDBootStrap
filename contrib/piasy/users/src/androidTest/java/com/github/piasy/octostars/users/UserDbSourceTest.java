package com.github.piasy.octostars.users;

import android.support.test.InstrumentationRegistry;
import com.github.piasy.bootstrap.base.model.provider.ProviderModuleExposure;
import com.github.piasy.bootstrap.mocks.MockDateTimeFormatter;
import com.github.piasy.bootstrap.testbase.TestUtil;
import com.github.piasy.bootstrap.testbase.rules.ThreeTenBPRule;
import com.squareup.sqlbrite.BriteDatabase;
import io.reactivex.observers.TestObserver;
import java.util.List;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by piasy on 15/8/11.
 */
public class UserDbSourceTest {
    private static final String LOGIN = "Piasy";
    private static final String AVATAR = "avatar";

    @Rule
    public ThreeTenBPRule mThreeTenBPRule = ThreeTenBPRule.androidTest(
            InstrumentationRegistry.getContext());

    private UserDbSource mUserDbSource;
    private ZonedDateTime mDate;

    @Before
    public void setUp() {
        final BriteDatabase briteDb = ProviderModuleExposure.exposeBriteDb(
                InstrumentationRegistry.getContext(), GitHubUser.CREATE_TABLE);
        mUserDbSource = new UserDbSource(MockDateTimeFormatter.mockDateTimeFormatter(), briteDb);
        mDate = ZonedDateTime.of(2016, 5, 6, 0, 1, 0, 0, ZoneId.systemDefault());

        // Clearing table before each test case
        mUserDbSource.deleteAll();
    }

    @Test
    public void testInsert() {
        List<GitHubUser> storedUsers = mUserDbSource.getNow(LOGIN);
        assertThat(storedUsers).isEmpty();

        final GitHubUser user = GitHubUser.builder()
                .id(1L)
                .login(LOGIN)
                .avatar_url(AVATAR)
                .type(UserRepo.TYPE_USER)
                .updated_at(mDate)
                .build();
        mUserDbSource.put(user);

        storedUsers = mUserDbSource.getNow(LOGIN);
        assertThat(storedUsers)
                .containsExactly(user)
                .inOrder();
    }

    @Test
    public void testUpdate() {
        final GitHubUser user = GitHubUser.builder()
                .id(1L)
                .login(LOGIN)
                .avatar_url(AVATAR)
                .type(UserRepo.TYPE_USER)
                .updated_at(mDate)
                .build();
        mUserDbSource.put(user);

        final GitHubUser altered = GitHubUser.builder()
                .id(1L)
                .login(LOGIN)
                .avatar_url(AVATAR)
                .type(UserRepo.TYPE_USER)
                .updated_at(mDate.plusHours(1))
                .build();
        mUserDbSource.put(altered);

        List<GitHubUser> storedUsers = mUserDbSource.getNow(LOGIN);
        assertThat(storedUsers)
                .containsExactly(altered)
                .inOrder();
    }

    @Test
    public void testDelete() {
        final GitHubUser user = GitHubUser.builder()
                .id(1L)
                .login(LOGIN)
                .avatar_url(AVATAR)
                .type(UserRepo.TYPE_USER)
                .updated_at(mDate)
                .build();
        mUserDbSource.put(user);

        int deleted = mUserDbSource.delete(LOGIN);
        assertThat(deleted).isSameAs(1);

        List<GitHubUser> storedUsers = mUserDbSource.getNow(LOGIN);
        assertThat(storedUsers).isEmpty();
    }

    @Test
    public void testObserve() {
        final TestObserver<GitHubUser> testObserver = new TestObserver<>();
        mUserDbSource.observe(LOGIN).subscribe(testObserver);
        TestUtil.sleep(50); // ensure the chain is established

        final GitHubUser user = GitHubUser.builder()
                .id(1L)
                .login(LOGIN)
                .avatar_url(AVATAR)
                .type(UserRepo.TYPE_USER)
                .updated_at(mDate)
                .build();
        mUserDbSource.put(user);
        TestUtil.sleep(50); // ensure the triggered query completed

        final GitHubUser altered = GitHubUser.builder()
                .id(1L)
                .login(LOGIN)
                .avatar_url(AVATAR)
                .type(UserRepo.TYPE_USER)
                .updated_at(mDate.plusHours(1))
                .build();
        mUserDbSource.put(altered);
        TestUtil.sleep(50);

        mUserDbSource.delete(LOGIN);
        TestUtil.sleep(50);

        assertThat(testObserver.getEvents().get(0))
                .containsExactly(user, altered)
                .inOrder();
    }
}
