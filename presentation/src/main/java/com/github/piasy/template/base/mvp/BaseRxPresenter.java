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

package com.github.piasy.template.base.mvp;

import android.support.annotation.NonNull;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.EventBusException;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

/**
 * Created by Piasy{github.com/Piasy} on 15/7/24.
 *
 * Base Presenter with rx support.
 *
 * @param <V> type parameter extends {@link MvpView}
 */
public abstract class BaseRxPresenter<V extends MvpView> extends MvpBasePresenter<V> {

    private CompositeSubscription mSubscription;

    /**
     * get the {@link EventBus} object.
     *
     * @return the {@link EventBus} object.
     */
    @NonNull
    protected abstract EventBus getEventBus();

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
        super.attachView(view);

        if (!getEventBus().isRegistered(this)) {
            try {
                getEventBus().register(this);
            } catch (EventBusException e) {
                Timber.w(e, "No subscriber at %s", this.getClass().getName());
            }
        }
    }

    @Override
    public void detachView(final boolean retainInstance) {
        super.detachView(retainInstance);

        if (!retainInstance) {
            unSubscribeAll();
        }
        if (getEventBus().isRegistered(this)) {
            getEventBus().unregister(this);
        }
    }
}
