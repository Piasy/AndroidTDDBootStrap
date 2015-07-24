package com.piasy.template.di;

import com.piasy.common.di.PerActivity;
import com.piasy.template.base.BaseFragment;

import android.app.Activity;

import dagger.Component;

/**
 * Created by Piasy{github.com/Piasy} on 15/7/23.
 */
@PerActivity
@Component(modules = ActivityModule.class, dependencies = AppComponent.class)
public interface ActivityComponent {

    void inject(BaseFragment fragment);

    Activity activity();
}
