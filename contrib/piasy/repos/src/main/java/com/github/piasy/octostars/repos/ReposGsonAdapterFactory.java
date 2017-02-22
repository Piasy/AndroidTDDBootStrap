package com.github.piasy.octostars.repos;

import com.github.piasy.bootstrap.base.model.gson.NullSafeTypeAdapterFactory;
import com.google.gson.TypeAdapterFactory;
import com.ryanharter.auto.value.gson.GsonTypeAdapterFactory;

/**
 * Created by Piasy{github.com/Piasy} on 7/31/16.
 */
@GsonTypeAdapterFactory
abstract class ReposGsonAdapterFactory implements TypeAdapterFactory {
    public static TypeAdapterFactory create() {
        return new NullSafeTypeAdapterFactory(new AutoValueGson_ReposGsonAdapterFactory());
    }
}
