package com.piasy.template.base;

import com.hannesdorfmann.mosby.mvp.MvpFragment;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.piasy.common.di.HasComponent;
import com.piasy.template.base.di.BaseMvpComponent;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import butterknife.ButterKnife;

/**
 * Created by Piasy{github.com/Piasy} on 15/7/23.
 */
public abstract class BaseFragment<
        V extends MvpView,
        P extends MvpPresenter<V>,
        C extends BaseMvpComponent<V, P>>

        extends MvpFragment<V, P> {

    private C mComponent;

    @SuppressWarnings("unchecked")
    @Override
    protected void injectDependencies() {
        mComponent = ((HasComponent<C>) getActivity()).getComponent();
        presenter = mComponent.presenter();
        setPresenter(presenter);
        this.inject();
    }

    protected abstract void inject();

    @Override
    public P createPresenter() {
        return presenter;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    protected C getComponent() {
        return mComponent;
    }

    /**
     * Shows a {@link android.widget.Toast} message.
     *
     * @param message An string representing a message to be shown.
     */
    protected void showToastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    public void showProgress(String message) {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).showProgress(message);
        }
    }

    public void stopProgress(boolean success) {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).stopProgress(success);
        }
    }

    public void showProgress() {
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).showProgress();
        }
    }

}
