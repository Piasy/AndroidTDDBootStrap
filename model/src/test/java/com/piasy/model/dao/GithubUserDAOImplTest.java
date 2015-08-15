package com.piasy.model.dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.piasy.common.android.utils.net.RxUtil;
import com.piasy.common.android.utils.provider.GsonProvider;
import com.piasy.model.MockProvider;
import com.piasy.model.db.StorIOSQLiteDelegate;
import com.piasy.model.entities.GithubSearchResult;
import com.piasy.model.entities.GithubUser;
import com.piasy.model.rest.github.GithubAPI;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import rx.Observable;
import rx.observers.TestSubscriber;

import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Created by Piasy{github.com/Piasy} on 15/8/12.
 */
public class GithubUserDAOImplTest {

    private StorIOSQLiteDelegate mStorIOSQLite;
    private GithubAPI mGithubAPI;
    private RxUtil.RxErrorProcessor mRxErrorProcessor;
    private Gson mGson;
    private GithubUserDAO mGithubUserDAO;

    @Before
    public void setUp() {
        mGson = GsonProvider.provideGson();
        mStorIOSQLite = mock(StorIOSQLiteDelegate.class);
        mGithubAPI = mock(GithubAPI.class);
        mRxErrorProcessor = mock(RxUtil.RxErrorProcessor.class);

        mGithubUserDAO = new GithubUserDAOImpl(mStorIOSQLite, mGithubAPI, mRxErrorProcessor);
    }

    @Test
    public void testGetUserNoCloudData() {
        // given
        willReturn(Observable.create(subscriber -> {
            GithubSearchResult<GithubUser> result =
                    mGson.fromJson(MockProvider.provideEmptyGithubSearchResult(),
                            new TypeToken<GithubSearchResult<GithubUser>>() {
                            }.getType());
            subscriber.onNext(result);
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

        then(mRxErrorProcessor).shouldHaveZeroInteractions();

        then(mGithubAPI).should(only()).searchGithubUsers(anyString(), anyString(), anyString());
    }
}
