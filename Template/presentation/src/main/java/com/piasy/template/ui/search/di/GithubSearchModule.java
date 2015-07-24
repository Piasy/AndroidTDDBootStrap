package com.piasy.template.ui.search.di;

import com.piasy.model.rest.github.GithubAPI;
import com.piasy.template.ui.search.mvp.GithubSearchPresenter;
import com.piasy.template.ui.search.mvp.GithubSearchPresenterImpl;

import dagger.Module;
import dagger.Provides;
import de.greenrobot.event.EventBus;

/**
 * Created by Piasy{github.com/Piasy} on 15/7/23.
 */
@Module
public class GithubSearchModule {

    @Provides
    GithubSearchPresenter provideGithubSearchPresenter(EventBus bus, GithubAPI api) {
        return new GithubSearchPresenterImpl(bus, api);
    }

}
