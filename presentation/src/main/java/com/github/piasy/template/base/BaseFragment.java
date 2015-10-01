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

package com.github.piasy.template.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import com.github.piasy.common.di.HasComponent;
import com.github.piasy.template.base.di.BaseMvpComponent;
import com.hannesdorfmann.mosby.mvp.MvpFragment;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.trello.rxlifecycle.FragmentEvent;
import com.trello.rxlifecycle.RxLifecycle;
import com.trello.rxlifecycle.components.FragmentLifecycleProvider;
import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Created by Piasy{github.com/Piasy} on 15/7/23.
 *
 * Base fragment class.
 *
 * @param <V> type parameter extends {@link MvpView}.
 * @param <P> type parameter extends {@link MvpPresenter}.
 * @param <C> type parameter extends {@link BaseMvpComponent}.
 */
@SuppressWarnings("PMD.TooManyMethods")
public abstract class BaseFragment<V extends MvpView, P extends MvpPresenter<V>, C extends
        BaseMvpComponent<V, P>>
        extends MvpFragment<V, P> implements FragmentLifecycleProvider {

    private C mComponent;

    // ============= copy from com.trello.rxlifecycle.components.RxFragment =============
    private final BehaviorSubject<FragmentEvent> mLifecycleSubject = BehaviorSubject.create();

    @Override
    public final Observable<FragmentEvent> lifecycle() {
        return mLifecycleSubject.asObservable();
    }

    @Override
    public final <T> Observable.Transformer<T, T> bindUntilEvent(final FragmentEvent event) {
        return RxLifecycle.bindUntilFragmentEvent(mLifecycleSubject, event);
    }

    @Override
    public final <T> Observable.Transformer<T, T> bindToLifecycle() {
        return RxLifecycle.bindFragment(mLifecycleSubject);
    }

    @Override
    public void onAttach(final Activity activity) {
        super.onAttach(activity);
        mLifecycleSubject.onNext(FragmentEvent.ATTACH);
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLifecycleSubject.onNext(FragmentEvent.CREATE);
    }

    @Override
    public void onStart() {
        super.onStart();
        mLifecycleSubject.onNext(FragmentEvent.START);
    }

    @Override
    public void onResume() {
        super.onResume();
        mLifecycleSubject.onNext(FragmentEvent.RESUME);
    }

    @Override
    public void onPause() {
        mLifecycleSubject.onNext(FragmentEvent.PAUSE);
        super.onPause();
    }

    @Override
    public void onStop() {
        mLifecycleSubject.onNext(FragmentEvent.STOP);
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        mLifecycleSubject.onNext(FragmentEvent.DESTROY_VIEW);
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        mLifecycleSubject.onNext(FragmentEvent.DESTROY);
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        mLifecycleSubject.onNext(FragmentEvent.DETACH);
        super.onDetach();
    }
    // ============= end copy from com.trello.rxlifecycle.components.RxFragment =============

    @SuppressWarnings("unchecked")
    @Override
    protected void injectDependencies() {
        mComponent = ((HasComponent<C>) getActivity()).getComponent();
        presenter = mComponent.presenter();
        setPresenter(presenter);
        this.inject();
    }

    /**
     * inject dependencies.
     */
    protected abstract void inject();

    @Override
    public P createPresenter() {
        return presenter;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
            final Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mLifecycleSubject.onNext(FragmentEvent.CREATE_VIEW);
    }

    protected C getComponent() {
        return mComponent;
    }
    
    /**
     * Show progress feedback with default text hint.
     * forward to host Activity.
     */
    public void showProgress() {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).showProgress();
        }
    }

    /**
     * Show progress feedback with the specified text hint.
     * forward to host Activity.
     *
     * @param message the specified text hint.
     */
    public void showProgress(final String message) {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).showProgress(message);
        }
    }

    /**
     * Stop the progress feedback.
     * forward to host Activity.
     *
     * @param success whether the process succeeded or not.
     */
    public void stopProgress(final boolean success) {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).stopProgress(success);
        }
    }
}
