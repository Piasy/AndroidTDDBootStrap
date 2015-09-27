package com.github.piasy.template.base;

import android.app.Service;
import com.github.piasy.template.app.di.AppComponent;
import com.github.piasy.template.app.di.IApplication;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.EventBusException;
import javax.inject.Inject;
import timber.log.Timber;

/**
 * Created by Piasy{github.com/Piasy} on 15/7/23.
 *
 * Base Service class.
 */
public abstract class BaseService extends Service {
    @Inject
    protected EventBus mBus;

    @Override
    public void onCreate() {
        super.onCreate();
        if (!mBus.isRegistered(this)) {
            try {
                mBus.register(this);
            } catch (EventBusException e) {
                Timber.d("No subscriber at " + this.getClass().getName());
            }
        }
    }

    @Override
    public void onDestroy() {
        if (mBus.isRegistered(this)) {
            mBus.unregister(this);
        }
        super.onDestroy();
    }

    protected AppComponent getApplicationComponent() {
        return ((IApplication) getApplication()).appComponent();
    }
}
