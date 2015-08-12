package com.piasy.template.base.di;

import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;

import android.support.annotation.NonNull;

/**
 * Created by Piasy{github.com/Piasy} on 15/7/24.
 */
public interface BaseMvpComponent<V extends MvpView, P extends MvpPresenter<V>> {

    @NonNull
    P presenter();

}
