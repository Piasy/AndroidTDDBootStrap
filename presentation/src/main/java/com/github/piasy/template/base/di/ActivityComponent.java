package com.github.piasy.template.base.di;

import android.app.Activity;
import com.github.piasy.common.di.PerActivity;
import dagger.Component;

/**
 * Created by Piasy{github.com/Piasy} on 15/7/23.
 */
@PerActivity
@Component(modules = ActivityModule.class, dependencies = AppComponent.class)
public interface ActivityComponent {

    Activity activity();
}
