package com.github.piasy.model.dao.di;

import com.github.piasy.common.android.utils.net.RxUtil;
import com.github.piasy.model.dao.GithubUserDAO;
import com.github.piasy.model.dao.GithubUserDAOImpl;
import com.github.piasy.model.db.StorIOSQLiteDelegate;
import com.github.piasy.model.rest.github.GithubAPI;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Piasy{github.com/Piasy} on 15/8/5.
 */
@Module
public class DAOModule {

    @Provides
    GithubUserDAO provideGithubUserDAO(StorIOSQLiteDelegate storIOSQLite, GithubAPI githubAPI,
            RxUtil.RxErrorProcessor rxErrorProcessor) {
        return new GithubUserDAOImpl(storIOSQLite, githubAPI, rxErrorProcessor);
    }
}
