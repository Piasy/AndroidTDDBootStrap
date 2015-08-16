package com.piasy.model.rest;

import android.app.Application;
import com.google.gson.Gson;
import com.piasy.common.android.utils.provider.EventBusProvider;
import com.piasy.common.android.utils.provider.GsonProvider;
import com.piasy.common.android.utils.provider.RestProvider;
import dagger.Module;
import dagger.Provides;
import de.greenrobot.event.EventBus;
import retrofit.RestAdapter;

/**
 * Created by Piasy{github.com/Piasy} on 15/7/23.
 */
@Module
public class RestModule {

    /**
     * Not using dagger scope here because RestProvider guarantee Singleton.
     */
    @Provides
    RestAdapter provideRestAdapter(Application application) {
        return RestProvider.provideRestAdapter(application);
    }

    @Provides
    Gson provideGson(Application application) {
        return GsonProvider.provideGson(application);
    }

    @Provides
    EventBus provideEventBus() {
        return EventBusProvider.provideEventBus();
    }
}
