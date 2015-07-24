package com.piasy.template.ui.search;

import com.piasy.common.di.HasComponent;
import com.piasy.template.base.BaseActivity;
import com.piasy.template.di.ActivityModule;

import android.os.Bundle;

public class GithubSearchActivity extends BaseActivity
        implements HasComponent<GithubSearchComponent> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction()
                .add(android.R.id.content, new GithubSearchActivityFragment())
                .commit();
    }

    GithubSearchComponent mGithubSearchComponent;

    @Override
    protected void initializeInjector() {
        mGithubSearchComponent = DaggerGithubSearchComponent.builder()
                .activityModule(new ActivityModule(this))
                .build();
        mGithubSearchComponent.inject(this);
    }

    @Override
    public GithubSearchComponent getComponent() {
        return mGithubSearchComponent;
    }
}
