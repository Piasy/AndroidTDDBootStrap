package com.github.piasy.gh.model.users.dao;

import com.github.piasy.base.model.provider.GsonProviderExposure;
import com.github.piasy.gh.model.GithubApi;
import com.github.piasy.gh.model.users.DbUserDelegate;
import com.github.piasy.gh.model.users.GithubUser;
import com.github.piasy.gh.model.users.GithubUserRepo;
import com.github.piasy.gh.model.users.GithubUserSearchResult;
import com.github.piasy.test.TestUtil;
import com.github.piasy.test.mock.MockProvider;
import com.github.piasy.test.rules.ThreeTenBPRule;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.List;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Subscriber;
import rx.observers.TestSubscriber;

import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Matchers.anyListOf;
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
        mEmptyResult = gson.fromJson(MockProvider.provideEmptyGithubSearchResult(),
                new TypeToken<GithubUserSearchResult>() {
                }.getType());

        mGithubUserRepo = new GithubUserRepo(mDbUserDelegate, mGithubApi);
    }

    @Test
    public void testSearchUserSuccess() {
        // given
        willReturn(Observable.create(new Observable.OnSubscribe<GithubUserSearchResult>() {
            @Override
            public void call(final Subscriber<? super GithubUserSearchResult> subscriber) {
                subscriber.onNext(mEmptyResult);
                subscriber.onCompleted();
            }
        })).given(mGithubApi).searchGithubUsers(anyString(), anyString(), anyString());

        // when
        final TestSubscriber<List<GithubUser>> subscriber = new TestSubscriber<>();
        mGithubUserRepo.searchUser("Piasy").subscribe(subscriber);
        subscriber.awaitTerminalEvent();

        // then
        then(mDbUserDelegate).should(timeout(100)).putAllGithubUser(anyListOf(GithubUser.class));
        verifyNoMoreInteractions(mDbUserDelegate);
        subscriber.assertNoErrors();

        then(mGithubApi).should(timeout(100).only())
                .searchGithubUsers(anyString(), anyString(), anyString());
    }

    @Test
    public void testSearchUserApiError() {
        // given
        willReturn(Observable.create(
                (Observable.OnSubscribe<GithubUserSearchResult>) subscriber -> {
                    subscriber.onError(TestUtil.apiError());
                })).given(mGithubApi).searchGithubUsers(anyString(), anyString(), anyString());

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
}
