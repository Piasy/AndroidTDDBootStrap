package com.github.piasy.bootstrap.misc;

import com.google.gson.TypeAdapterFactory;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;
import javax.inject.Singleton;

/**
 * Created by Piasy{github.com/Piasy} on 20/01/2017.
 */

@Module
public class MiscModule {
    @Singleton
    @Provides
    @IntoSet
    TypeAdapterFactory provideAutoGsonAdapterFactory() {
        return ErrorGsonAdapterFactory.create();
    }
}
