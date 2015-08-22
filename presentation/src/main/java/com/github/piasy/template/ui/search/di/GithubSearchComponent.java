package com.github.piasy.template.ui.search.di;

import com.github.piasy.common.di.PerActivity;
import com.github.piasy.model.dao.di.DAOModule;
import com.github.piasy.model.rest.github.GithubAPIModule;
import com.github.piasy.template.base.di.ActivityComponent;
import com.github.piasy.template.base.di.ActivityModule;
import com.github.piasy.template.base.di.AppComponent;
import com.github.piasy.template.base.di.BaseMvpComponent;
import com.github.piasy.template.ui.search.GithubSearchActivity;
import com.github.piasy.template.ui.search.GithubSearchFragment;
import com.github.piasy.template.ui.search.mvp.GithubSearchPresenter;
import com.github.piasy.template.ui.search.mvp.GithubSearchView;
import dagger.Component;

/**
 * Created by Piasy{github.com/Piasy} on 15/7/23.
 */
@PerActivity
@Component(
        dependencies = AppComponent.class,
        modules = {
                GithubAPIModule.class, DAOModule.class, GithubSearchModule.class,

                ActivityModule.class
        })
public interface GithubSearchComponent
        extends ActivityComponent, BaseMvpComponent<GithubSearchView, GithubSearchPresenter> {

    void inject(GithubSearchActivity activity);

    void inject(GithubSearchFragment fragment);
}
