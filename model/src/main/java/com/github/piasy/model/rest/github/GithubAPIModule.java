package com.github.piasy.model.rest.github;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;

/**
 * Created by Piasy{github.com/Piasy} on 15/7/23.
 */
@Module
public class GithubAPIModule {

    @Provides
    GithubAPI provideGithubAPI(RestAdapter restAdapter) {
        return restAdapter.create(GithubAPI.class);
    }
}
