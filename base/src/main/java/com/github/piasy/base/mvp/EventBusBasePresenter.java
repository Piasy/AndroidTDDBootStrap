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
import com.hannesdorfmann.mosby.mvp.MvpView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.EventBusException;
import timber.log.Timber;

/**
 * Created by Piasy{github.com/Piasy} on 15/7/24.
 *
 * Base Presenter with rx support, and null object pattern, and event bus integration.
 *
 * @param <V> type parameter extends {@link MvpView}
 */
public abstract class EventBusBasePresenter<V extends MvpView> extends NullObjRxBasePresenter<V> {

    protected final EventBus mEventBus;

    protected EventBusBasePresenter(@NonNull final EventBus eventBus) {
        super();
        mEventBus = eventBus;
    }

    @Override
    public void attachView(final V view) {
        super.attachView(view);
        if (!mEventBus.isRegistered(this)) {
            try {
                mEventBus.register(this);
            } catch (EventBusException e) {
                Timber.w(e, "No subscriber at %s", this.getClass().getName());
            }
        }
    }

    @Override
    public void detachView(final boolean retainInstance) {
        super.detachView(retainInstance);
        if (mEventBus.isRegistered(this)) {
            mEventBus.unregister(this);
        }
    }
}
