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

package com.github.piasy.gh.features.search.di;

import com.github.piasy.gh.features.search.SearchFragment;
import com.github.piasy.gh.features.search.mvp.SearchPresenter;
import com.github.piasy.gh.features.search.mvp.SearchUserView;
import com.github.piasy.base.di.ActivityModule;
import com.github.piasy.base.di.ActivityScope;
import com.github.piasy.base.di.BaseMvpComponent;
import com.github.piasy.gh.model.users.UsersModule;
import dagger.Subcomponent;

/**
 * Created by Piasy{github.com/Piasy} on 3/6/16.
 */
@ActivityScope
@Subcomponent(modules = {
        ActivityModule.class, UsersModule.class, SearchModule.class
})
public interface SearchComponent extends BaseMvpComponent<SearchUserView, SearchPresenter> {
    void inject(SearchFragment searchFragment);
}
