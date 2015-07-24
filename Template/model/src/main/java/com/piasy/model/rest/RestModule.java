package com.piasy.model.rest;

import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;
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
    RestAdapter provideRestAdapter() {
        return RestProvider.provideRestAdapter();
    }

    @Provides
    Gson provideGson() {
        return GsonProvider.provideGson();
    }

}
