package com.piasy.template.ui.search;

import com.piasy.model.rest.github.GithubAPIModule;
import com.piasy.template.di.ActivityComponent;
import com.piasy.template.di.ActivityModule;

import dagger.Component;

/**
 * Created by Piasy{github.com/Piasy} on 15/7/23.
 */
@Component(
        modules = {
                GithubAPIModule.class,
                GithubSearchModule.class,

                ActivityModule.class
        })
public interface GithubSearchComponent extends ActivityComponent {
    void inject(GithubSearchActivity activity);
    void inject(GithubSearchActivityFragment fragment);
}
