package com.piasy.model.dao;

import com.google.gson.Gson;

import com.piasy.common.android.utils.net.RxUtil;
import com.piasy.common.android.utils.provider.GsonProvider;
import com.piasy.model.rest.github.GithubAPI;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Created by Piasy{github.com/Piasy} on 15/8/12.
 */
public class GithubUserDAOImplTest {

    private StorIOSQLite mStorIOSQLite;
    private GithubAPI mGithubAPI;
    private RxUtil.RxErrorProcessor mRxErrorProcessor;
    private Gson mGson;
    private GithubUserDAO mGithubUserDAO;

    @Before
    public void setUp() {
        mGson = GsonProvider.provideGson();
        mStorIOSQLite = Mockito.mock(StorIOSQLite.class);
        mGithubAPI = Mockito.mock(GithubAPI.class);
        mRxErrorProcessor = Mockito.mock(RxUtil.RxErrorProcessor.class);

        mGithubUserDAO = new GithubUserDAOImpl(mStorIOSQLite, mGithubAPI, mRxErrorProcessor);
    }

    @Test
    public void testGetUserNoCloudData() {
        /*Mockito.when(mGithubAPI.searchGithubUsers(
                Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
                .thenReturn(Observable.just(
                        mGson.fromJson(MockProvider.provideEmptyGithubSearchResult(),
                                new TypeToken<GithubSearchResult<GithubUser>>() {
                                }.getType())));

        mGithubUserDAO.getUsers();

        Mockito.verify(mStorIOSQLite, Mockito.only()).delete();*/
    }

}
