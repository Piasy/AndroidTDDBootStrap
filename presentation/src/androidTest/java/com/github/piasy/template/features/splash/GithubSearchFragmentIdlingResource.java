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

package com.github.piasy.template.features.splash;

import android.support.test.espresso.IdlingResource;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Piasy{github.com/Piasy} on 15/9/28.
 *
 * IdlingResource waiting for the SplashFragment disappear.
 *
 * @deprecated using this class at test case (after test activity is launched), it won't work
 * properly, but using this in setup method is also impractical, because the test activity could
 * not be obtained at this time.
 */
@Deprecated
public class GithubSearchFragmentIdlingResource implements IdlingResource {

    private ResourceCallback mResourceCallback;
    private final AppCompatActivity mActivity;

    public GithubSearchFragmentIdlingResource(final AppCompatActivity activity) {
        mActivity = activity;
    }

    @Override
    public String getName() {
        return GithubSearchFragmentIdlingResource.class.getName();
    }

    @Override
    public boolean isIdleNow() {
        final boolean idle = isGithubSearchFragmentVisible();
        if (idle && mResourceCallback != null) {
            mResourceCallback.onTransitionToIdle();
        }
        return idle;
    }

    @Override
    public void registerIdleTransitionCallback(final ResourceCallback callback) {
        mResourceCallback = callback;
    }

    private boolean isGithubSearchFragmentVisible() {
        final Fragment fragment =
                mActivity.getSupportFragmentManager().findFragmentByTag("GithubSearchFragment");
        return fragment != null && fragment.isVisible();
    }
}
