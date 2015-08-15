package com.piasy.template.base.di;

import android.support.annotation.NonNull;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by Piasy{github.com/Piasy} on 15/7/24.
 */
public interface BaseMvpComponent<V extends MvpView, P extends MvpPresenter<V>> {

    @NonNull
    P presenter();
}
