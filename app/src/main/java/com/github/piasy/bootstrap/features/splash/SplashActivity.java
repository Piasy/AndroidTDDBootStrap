/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Piasy
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

package com.github.piasy.bootstrap.features.splash;

import android.os.Bundle;
import com.github.piasy.bootstrap.BootstrapActivity;
import com.github.piasy.bootstrap.BootstrapApp;
import com.github.piasy.bootstrap.R;

import static com.github.piasy.safelyandroid.fragment.SupportFragmentTransactionBuilder.transaction;

/**
 * Created by Piasy{github.com/Piasy} on 15/9/19.
 *
 * Splash activity. Init app and handle other Intent action. I imitate the way in
 * <a href="http://frogermcs.github.io/dagger-graph-creation-performance/">frogermcs'  blog:
 * Dagger
 * 2 - graph creation performance</a> to avoid activity state loss.
 */
public class SplashActivity
        extends BootstrapActivity<SplashView, SplashPresenter, SplashComponent> {

    private static final String SPLASH_FRAGMENT = "SplashFragment";

    private SplashComponent mSplashComponent;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null
            || getSupportFragmentManager().findFragmentByTag(SPLASH_FRAGMENT) == null) {
            safeCommit(transaction(getSupportFragmentManager())
                    .add(android.R.id.content, new SplashFragment(), SPLASH_FRAGMENT)
                    .build());
        }
    }

    @Override
    protected void initializeDi() {
        mSplashComponent = BootstrapApp.get().appComponent().splashComponent();
    }

    @Override
    public SplashComponent getComponent() {
        return mSplashComponent;
    }
}
