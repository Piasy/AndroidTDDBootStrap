package com.piasy.template.base.mvp;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;

import android.support.annotation.NonNull;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.EventBusException;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Piasy{github.com/Piasy} on 15/7/24.
 */
public abstract class BaseRxPresenter<V extends MvpView> extends MvpBasePresenter<V> {

    private CompositeSubscription mSubscription = null;

    protected abstract
    @NonNull
    EventBus getEventBus();

    protected void addSubscription(Subscription subscription) {
        if (mSubscription == null || mSubscription.isUnsubscribed()) {
            mSubscription = new CompositeSubscription();
        }
        mSubscription.add(subscription);
    }

    protected void unSubscribeAll() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
        mSubscription = null;
    }

    @Override
    public void attachView(V view) {
        super.attachView(view);

        if (!getEventBus().isRegistered(this)) {
            try {
                getEventBus().register(this);
            } catch (EventBusException e) {
                // ignore non subscriber exception
            }
        }
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);

        if (!retainInstance) {
            unSubscribeAll();
        }
        if (getEventBus().isRegistered(this)) {
            getEventBus().unregister(this);
        }
    }
}
