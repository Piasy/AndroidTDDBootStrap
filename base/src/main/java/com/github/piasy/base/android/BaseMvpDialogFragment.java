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

package com.github.piasy.base.android;

import android.os.Bundle;
import com.github.piasy.base.di.BaseMvpComponent;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.hannesdorfmann.mosby.mvp.delegate.BaseMvpDelegateCallback;
import com.hannesdorfmann.mosby.mvp.delegate.FragmentMvpDelegate;
import com.hannesdorfmann.mosby.mvp.delegate.FragmentMvpDelegateImpl;

/**
 * Created by piasy on 15/5/4.
 */
@SuppressWarnings("PMD.EmptyMethodInAbstractClassShouldBeAbstract")
public abstract class BaseMvpDialogFragment<V extends MvpView, P extends MvpPresenter<V>, C
        extends BaseMvpComponent<V, P>>
        extends BaseDIDialogFragment<C> implements BaseMvpDelegateCallback<V, P> {
    protected FragmentMvpDelegate<V, P> mMvpDelegate;
    protected P mPresenter;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMvpDelegate = new FragmentMvpDelegateImpl<>(this);
        mPresenter = createPresenter();
    }

    @Override
    public final P getPresenter() {
        return mPresenter;
    }

    @Override
    public final void setPresenter(final P presenter) {
    }
}
