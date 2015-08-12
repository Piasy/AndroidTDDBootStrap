package com.piasy.template.ui.search.di;

import com.piasy.common.android.utils.net.RxUtil;
import com.piasy.model.dao.GithubUserDAO;
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
    GithubSearchPresenter provideGithubSearchPresenter(EventBus bus, GithubUserDAO githubUserDAO,
                                                       RxUtil.RxErrorProcessor rxErrorProcessor) {
        return new GithubSearchPresenterImpl(bus, githubUserDAO, rxErrorProcessor);
    }

}
