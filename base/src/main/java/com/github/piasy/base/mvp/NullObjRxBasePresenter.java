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

package com.github.piasy.base.mvp;

import android.support.annotation.NonNull;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;
import java.lang.ref.WeakReference;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Piasy{github.com/Piasy} on 15/7/24.
 *
 * Base Presenter with rx support, and null object pattern.
 *
 * @param <V> type parameter extends {@link MvpView}
 */
public abstract class NullObjRxBasePresenter<V extends MvpView> implements MvpPresenter<V> {

    private CompositeSubscription mSubscription;

    private WeakReference<V> mViewRef;

    /**
     * add a {@link Subscription} to the CompositeSubscription.
     *
     * @param subscription {@link Subscription} to add.
     */
    protected void addSubscription(final Subscription subscription) {
        if (mSubscription == null || mSubscription.isUnsubscribed()) {
            mSubscription = new CompositeSubscription();
        }
        mSubscription.add(subscription);
    }

    /**
     * unsubscribe all {@link Subscription}.
     */
    protected void unSubscribeAll() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

    @Override
    public void attachView(final V view) {
        mViewRef = new WeakReference<>(view);
    }

    @Override
    public void detachView(final boolean retainInstance) {
        if (mViewRef != null) {
            mViewRef.clear();
        }

        if (!retainInstance) {
            unSubscribeAll();
        }
    }

    /**
     * Get the attached view.
     *
     * @return the concrete view instance
     * @throws NullPointerException, if view is not attached
     */
    @NonNull
    public V getView() {
        if (mViewRef == null || mViewRef.get() == null) {
            throw new IllegalStateException(
                    "MvpView reference is null. Have you called attachView()?");
        }
        return mViewRef.get();
    }
}
