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

package com.github.piasy.template.features.user.di;

import com.github.piasy.common.android.utils.net.RxUtil;
import com.github.piasy.common.android.utils.roms.MiUIUtil;
import com.github.piasy.common.di.ActivityScope;
import com.github.piasy.model.dao.GithubUserDAO;
import com.github.piasy.template.features.user.mvp.GithubUserPresenter;
import com.github.piasy.template.features.user.mvp.GithubUserPresenterImpl;
import dagger.Module;
import dagger.Provides;
import de.greenrobot.event.EventBus;

/**
 * Created by Piasy{github.com/Piasy} on 15/7/23.
 *
 * DI Module for {@link com.github.piasy.template.features.user.GithubUserActivity}.
 */
@Module
public class GithubUserModule {

    /**
     * Provide {@link GithubUserPresenter} with dependencies.
     *
     * @param bus {@link EventBus}.
     * @param githubUserDAO {@link GithubUserDAO} to operate with data.
     * @param rxErrorProcessor {@link RxUtil.RxErrorProcessor} to process errors in rx.
     * @param miUIUtil {@link MiUIUtil} to check for ROM.
     * @return {@link GithubUserPresenter} object.
     */
    @ActivityScope
    @Provides
    GithubUserPresenter provideGithubSearchPresenter(final EventBus bus,
            final GithubUserDAO githubUserDAO, final RxUtil.RxErrorProcessor rxErrorProcessor,
            final MiUIUtil miUIUtil) {
        return new GithubUserPresenterImpl(bus, githubUserDAO, rxErrorProcessor);
    }
}
