package com.piasy.template.ui.search;

import com.piasy.common.di.HasComponent;
import com.piasy.template.base.BaseActivity;
import com.piasy.template.ui.search.di.DaggerGithubSearchComponent;
import com.piasy.template.ui.search.di.GithubSearchComponent;

import android.os.Bundle;

public class GithubSearchActivity extends BaseActivity
        implements HasComponent<GithubSearchComponent> {

    GithubSearchComponent mGithubSearchComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction()
                .add(android.R.id.content, new GithubSearchFragment())
                .commit();
    }

    @Override
    protected void initializeInjector() {
        mGithubSearchComponent = DaggerGithubSearchComponent.builder()
                .appComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
        mGithubSearchComponent.inject(this);
    }

    @Override
    public GithubSearchComponent getComponent() {
        return mGithubSearchComponent;
    }
}
