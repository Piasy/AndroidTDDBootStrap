package com.piasy.model.dao.di;

import com.piasy.model.dao.GithubUserDAO;
import com.piasy.model.dao.GithubUserDAOImpl;
import com.piasy.model.rest.github.GithubAPI;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Piasy{github.com/Piasy} on 15/8/5.
 */
@Module
public class GithubUserDAOModule {

    @Provides
    GithubUserDAO provideGithubUserDAO(GithubAPI githubAPI) {
        return new GithubUserDAOImpl(githubAPI);
    }

}
