package com.piasy.template.base;

import com.piasy.template.di.ActivityModule;
import com.piasy.template.di.AppComponent;
import com.piasy.template.di.IApplication;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * Created by Piasy{github.com/Piasy} on 15/7/23.
 */
public abstract class BaseActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getApplicationComponent().inject(this);
        initializeInjector();
        super.onCreate(savedInstanceState);
    }

    /**
     * Get the Main Application component for dependency injection.
     *
     * @return {@link AppComponent}
     */
    protected AppComponent getApplicationComponent() {
        return ((IApplication) getApplication()).component();
    }

    /**
     * Get an Activity module for dependency injection.
     *
     * @return {@link ActivityModule}
     */
    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

    protected abstract void initializeInjector();

}
