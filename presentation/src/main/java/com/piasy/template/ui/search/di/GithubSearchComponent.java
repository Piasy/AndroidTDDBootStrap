package com.piasy.template.ui.search.di;

import com.piasy.common.di.PerActivity;
import com.piasy.model.dao.di.DAOModule;
import com.piasy.model.rest.github.GithubAPIModule;
import com.piasy.template.base.di.ActivityComponent;
import com.piasy.template.base.di.ActivityModule;
import com.piasy.template.base.di.AppComponent;
import com.piasy.template.base.di.BaseMvpComponent;
import com.piasy.template.ui.search.GithubSearchActivity;
import com.piasy.template.ui.search.GithubSearchFragment;
import com.piasy.template.ui.search.mvp.GithubSearchPresenter;
import com.piasy.template.ui.search.mvp.GithubSearchView;
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
