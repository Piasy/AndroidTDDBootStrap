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
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import com.github.piasy.base.utils.RxUtil;
import com.hannesdorfmann.fragmentargs.FragmentArgs;
import com.jakewharton.rxbinding.view.RxView;
import com.trello.rxlifecycle.components.support.RxFragment;
import java.util.concurrent.TimeUnit;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Piasy{github.com/Piasy} on 15/7/23.
 *
 * Base fragment class.
 */
public abstract class BaseFragment extends RxFragment {

    private static final int WINDOW_DURATION = 1;

    private CompositeSubscription mCompositeSubscription;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (hasArgs()) {
            FragmentArgs.inject(this);
        }
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindView(view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbindView();
        unSubscribeAll();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {
        setHasOptionsMenu(shouldHaveOptionsMenu());
        return inflater.inflate(getLayoutRes(), container, false);
    }

    protected void addSubscribe(final Subscription subscription) {
        if (mCompositeSubscription == null || mCompositeSubscription.isUnsubscribed()) {
            // recreate mCompositeSubscription
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }

    protected void listenOnClickRxy(final View view, final Action1<Void> action) {
        addSubscribe(RxView.clicks(view)
                .throttleFirst(WINDOW_DURATION, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(action, RxUtil.OnErrorLogger));
    }

    protected void unSubscribeAll() {
        if (mCompositeSubscription != null && !mCompositeSubscription.isUnsubscribed()) {
            mCompositeSubscription.unsubscribe();
        }
    }

    /**
     * layout resource id
     *
     * @return layout resource id
     */
    @LayoutRes
    protected abstract int getLayoutRes();

    /**
     * override and return {@code true} to enable option menu.
     */
    protected boolean shouldHaveOptionsMenu() {
        return false;
    }

    /**
     * When use FragmentArgs to inject arguments, should override this and return {@code true}.
     */
    protected boolean hasArgs() {
        return false;
    }

    /**
     * When use ButterKnife to auto bind views, should override this and return {@code true}.
     * If not, should override {@link #bindView(View)} and {@link #unbindView()} to do it manually.
     */
    protected boolean autoBindViews() {
        return false;
    }

    /**
     * bind views, should override this method when bind view manually.
     */
    protected void bindView(final View rootView) {
        if (autoBindViews()) {
            ButterKnife.bind(this, rootView);
        }
    }

    /**
     * unbind views, should override this method when unbind view manually.
     */
    protected void unbindView() {
        if (autoBindViews()) {
            ButterKnife.unbind(this);
        }
    }
}
