package com.piasy.model.dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.piasy.common.android.utils.model.ThreeTenABPDelegate;
import com.piasy.common.android.utils.net.RxUtil;
import com.piasy.common.android.utils.provider.GsonProvider;
import com.piasy.common.android.utils.tests.BaseThreeTenBPTest;
import com.piasy.model.MockProvider;
import com.piasy.model.db.StorIOSQLiteDelegate;
import com.piasy.model.entities.GithubSearchResult;
import com.piasy.model.entities.GithubUser;
import com.piasy.model.rest.github.GithubAPI;
import java.util.ArrayList;
import java.util.List;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import rx.Observable;
import rx.observers.TestSubscriber;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Created by Piasy{github.com/Piasy} on 15/8/12.
 */
public class GithubUserDAOImplTest extends BaseThreeTenBPTest {

    private StorIOSQLiteDelegate mStorIOSQLite;
    private GithubAPI mGithubAPI;
    private RxUtil.RxErrorProcessor mRxErrorProcessor;
    private GithubUserDAO mGithubUserDAO;

    private GithubSearchResult<GithubUser> mEmptyResult;
    private List<GithubUser> mEmptyUserList;
    private List<GithubUser> mSingleUserList;
    private GithubSearchResult<GithubUser> mSingleResult;
    private GithubUser mSingleUser;

    @Before
    public void setUp() {
        initThreeTenABP();
        Gson gson = GsonProvider.provideGson(mock(ThreeTenABPDelegate.class));
        mEmptyResult = gson.fromJson(MockProvider.provideEmptyGithubSearchResult(),
                new TypeToken<GithubSearchResult<GithubUser>>() {
                }.getType());

        mEmptyUserList = new ArrayList<>();
        mSingleResult = gson.fromJson(MockProvider.provideSimplifiedGithubUserSearchResultStr(),
                new TypeToken<GithubSearchResult<GithubUser>>() {
                }.getType());
        mSingleUser = gson.fromJson(MockProvider.provideGithubUserStr(), GithubUser.class);
        mSingleUserList = new ArrayList<>();
        mSingleUserList.add(mSingleUser);

        mStorIOSQLite = mock(StorIOSQLiteDelegate.class);
        mGithubAPI = mock(GithubAPI.class);
        mRxErrorProcessor = mock(RxUtil.RxErrorProcessor.class);

        mGithubUserDAO = new GithubUserDAOImpl(mStorIOSQLite, mGithubAPI, mRxErrorProcessor);
    }

    @Test
    public void testGetUserNoCloudData() {
        // given
        willReturn(Observable.create(subscriber -> {
            subscriber.onNext(GithubSearchResult.from(mEmptyResult));
            subscriber.onCompleted();
        })).given(mGithubAPI).searchGithubUsers(anyString(), anyString(), anyString());

        willReturn(Observable.empty()).given(mStorIOSQLite).getAllGithubUserReactively();

        // when
        TestSubscriber<List<GithubUser>> subscriber = new TestSubscriber<>();
        mGithubUserDAO.getUsers().subscribe(subscriber);
        subscriber.awaitTerminalEvent();

        // then
        then(mStorIOSQLite).should(timeout(100)).getAllGithubUserReactively();
        then(mStorIOSQLite).should(timeout(100)).deleteAllGithubUser();
        verifyNoMoreInteractions(mStorIOSQLite);

        then(mGithubAPI).should(timeout(100).only())
                .searchGithubUsers(anyString(), anyString(), anyString());

        then(mRxErrorProcessor).shouldHaveZeroInteractions();
    }

    @Test
    public void testGetUserHasCloudDataNoLocalData() {
        // given
        willReturn(Observable.create(subscriber -> {
            subscriber.onNext(GithubSearchResult.from(mSingleResult));
            subscriber.onCompleted();
        })).given(mGithubAPI).searchGithubUsers(anyString(), anyString(), anyString());
        willReturn(Observable.create(subscriber -> {
            subscriber.onNext(GithubUser.from(mSingleUser));
            subscriber.onCompleted();
        })).given(mGithubAPI).getGithubUser(anyString());

        given(mStorIOSQLite.getAllGithubUser()).willReturn(new ArrayList<>(mEmptyUserList));
        willReturn(Observable.empty()).given(mStorIOSQLite).getAllGithubUserReactively();
        ArgumentCaptor<List<GithubUser>> capturedUsers = ArgumentCaptor.forClass(List.class);

        // when
        TestSubscriber<List<GithubUser>> subscriber = new TestSubscriber<>();
        mGithubUserDAO.getUsers().subscribe(subscriber);
        subscriber.awaitTerminalEvent();

        // then
        then(mGithubAPI).should(timeout(100))
                .searchGithubUsers(anyString(), anyString(), anyString());
        then(mGithubAPI).should(timeout(100)).getGithubUser(anyString());

        then(mStorIOSQLite).should(timeout(100)).getAllGithubUserReactively();
        then(mStorIOSQLite).should(timeout(100)).getAllGithubUser();
        then(mStorIOSQLite).should(timeout(100).times(2)).putAllGithubUser(capturedUsers.capture());
        verifyNoMoreInteractions(mStorIOSQLite);
        Assert.assertFalse(mSingleUser.equals(mSingleResult.getItems().get(0)));
        List<GithubUser> args1 = capturedUsers.getAllValues().get(0);
        Assert.assertEquals(1, args1.size());
        Assert.assertEquals(mSingleResult.getItems().get(0), args1.get(0));
        List<GithubUser> args2 = capturedUsers.getAllValues().get(1);
        Assert.assertEquals(1, args2.size());
        Assert.assertEquals(mSingleUser, args2.get(0));

        then(mRxErrorProcessor).shouldHaveZeroInteractions();
    }

    @Test
    public void testGetUserHasCloudDataHasLocalData() {
        // given
        willReturn(Observable.create(subscriber -> {
            subscriber.onNext(GithubSearchResult.from(mSingleResult));
            subscriber.onCompleted();
        })).given(mGithubAPI).searchGithubUsers(anyString(), anyString(), anyString());
        willReturn(Observable.create(subscriber -> {
            subscriber.onNext(GithubUser.from(mSingleUser));
            subscriber.onCompleted();
        })).given(mGithubAPI).getGithubUser(anyString());

        given(mStorIOSQLite.getAllGithubUser()).willReturn(new ArrayList<>(mSingleUserList));
        willReturn(Observable.empty()).given(mStorIOSQLite).getAllGithubUserReactively();
        ArgumentCaptor<List<GithubUser>> capturedUsers = ArgumentCaptor.forClass(List.class);

        // when
        TestSubscriber<List<GithubUser>> subscriber = new TestSubscriber<>();
        mGithubUserDAO.getUsers().subscribe(subscriber);
        subscriber.awaitTerminalEvent();

        // then
        then(mGithubAPI).should(timeout(100))
                .searchGithubUsers(anyString(), anyString(), anyString());
        then(mGithubAPI).should(timeout(100)).getGithubUser(anyString());

        then(mStorIOSQLite).should(timeout(100)).getAllGithubUserReactively();
        then(mStorIOSQLite).should(timeout(100)).getAllGithubUser();
        then(mStorIOSQLite).should(timeout(100)).putAllGithubUser(capturedUsers.capture());
        verifyNoMoreInteractions(mStorIOSQLite);
        List<GithubUser> args1 = capturedUsers.getAllValues().get(0);
        Assert.assertEquals(1, args1.size());
        Assert.assertEquals(mSingleUser, args1.get(0));

        then(mRxErrorProcessor).shouldHaveZeroInteractions();
    }
}
