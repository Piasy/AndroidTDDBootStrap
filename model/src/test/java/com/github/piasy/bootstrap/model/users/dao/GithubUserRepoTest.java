package com.github.piasy.bootstrap.model.users.dao;

import com.github.piasy.bootstrap.base.model.provider.GsonProviderExposure;
import com.github.piasy.bootstrap.model.GithubApi;
import com.github.piasy.bootstrap.model.users.DbUserDelegate;
import com.github.piasy.bootstrap.model.users.GithubUser;
import com.github.piasy.bootstrap.model.users.GithubUserRepo;
import com.github.piasy.bootstrap.model.users.GithubUserSearchResult;
import com.github.piasy.bootstrap.testbase.TestUtil;
import com.github.piasy.bootstrap.testbase.rules.ThreeTenBPRule;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.subscribers.TestSubscriber;
import java.util.List;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * Created by Piasy{github.com/Piasy} on 15/8/12.
 */
public class GithubUserRepoTest {

    @Rule
    public ThreeTenBPRule mThreeTenBPRule = ThreeTenBPRule.junitTest();
    @Rule
    public MockitoRule mMockitoRule = MockitoJUnit.rule();

    @Mock
    private DbUserDelegate mDbUserDelegate;
    @Mock
    private GithubApi mGithubApi;
    private GithubUserRepo mGithubUserRepo;

    private GithubUserSearchResult mEmptyResult;

    @Before
    public void setUp() {
        final Gson gson = GsonProviderExposure.exposeGson();
        mEmptyResult = gson.fromJson(provideEmptyGithubSearchResult(),
                new TypeToken<GithubUserSearchResult>() {
                }.getType());

        mGithubUserRepo = new GithubUserRepo(mDbUserDelegate, mGithubApi);
    }

    @Test
    public void testSearchUserSuccess() {
        // given
        willReturn(Flowable.create(emitter -> {
            emitter.onNext(mEmptyResult);
            emitter.onComplete();
        }, BackpressureStrategy.BUFFER))
                .given(mGithubApi)
                .searchGithubUsers(anyString(), anyString(), anyString());

        // when
        final TestSubscriber<List<GithubUser>> subscriber = new TestSubscriber<>();
        mGithubUserRepo.searchUser("Piasy").subscribe(subscriber);
        subscriber.awaitTerminalEvent();

        // then
        then(mDbUserDelegate).should(timeout(100)).putAllGithubUser(anyList());
        verifyNoMoreInteractions(mDbUserDelegate);
        subscriber.assertNoErrors();

        then(mGithubApi).should(timeout(100).only())
                .searchGithubUsers(anyString(), anyString(), anyString());
    }

    @Test
    public void testSearchUserApiError() {
        // given
        willReturn(Flowable.create(emitter -> emitter.onError(TestUtil.apiError()),
                BackpressureStrategy.BUFFER))
                .given(mGithubApi)
                .searchGithubUsers(anyString(), anyString(), anyString());

        // when
        final TestSubscriber<List<GithubUser>> subscriber = new TestSubscriber<>();
        mGithubUserRepo.searchUser("Piasy").subscribe(subscriber);
        subscriber.awaitTerminalEvent();

        // then
        verifyZeroInteractions(mDbUserDelegate);
        subscriber.assertNoValues();
        subscriber.assertError(HttpException.class);

        then(mGithubApi).should(timeout(100).only())
                .searchGithubUsers(anyString(), anyString(), anyString());
    }

    private String provideEmptyGithubSearchResult() {
        return "{\"total_count\":0,\"incomplete_results\":false,\"items\":[]}";
    }
}
