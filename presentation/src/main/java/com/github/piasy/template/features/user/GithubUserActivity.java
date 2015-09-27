/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Piasy
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.piasy.template.features.user;

import android.os.Bundle;
import com.github.piasy.common.di.HasComponent;
import com.github.piasy.template.app.TemplateApp;
import com.github.piasy.template.base.BaseActivity;
import com.github.piasy.template.features.user.di.GithubUserComponent;
import com.github.piasy.template.features.user.di.GithubUserModule;
import com.github.piasy.template.features.splash.GithubSearchFragment;

/**
 * Activity for search github user feature.
 */
public class GithubUserActivity extends BaseActivity
        implements HasComponent<GithubUserComponent> {

    private GithubUserComponent mGithubUserComponent;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction()
                .add(android.R.id.content, new GithubSearchFragment())
                .commit();
    }

    @Override
    protected void initializeInjector() {
        mGithubUserComponent = TemplateApp.get(this)
                .userComponent()
                .plus(getActivityModule(), new GithubUserModule());
        mGithubUserComponent.inject(this);
    }

    @Override
    public GithubUserComponent getComponent() {
        return mGithubUserComponent;
    }
}
