package com.piasy.model.dao.di;

import com.piasy.common.android.utils.net.RxUtil;
import com.piasy.model.dao.GithubUserDAO;
import com.piasy.model.dao.GithubUserDAOImpl;
import com.piasy.model.rest.github.GithubAPI;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Piasy{github.com/Piasy} on 15/8/5.
 */
@Module
public class GithubUserDAOModule {

    @Provides
    GithubUserDAO provideGithubUserDAO(StorIOSQLite storIOSQLite, GithubAPI githubAPI,
                                       RxUtil.RxErrorProcessor rxErrorProcessor) {
        return new GithubUserDAOImpl(storIOSQLite, githubAPI, rxErrorProcessor);
    }

}
