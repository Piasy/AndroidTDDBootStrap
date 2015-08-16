package com.piasy.common.utils.model;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

/**
 * https://gist.github.com/Piasy/fa507251da452d36b221
 */
public final class AutoGenTypeAdapterFactory implements TypeAdapterFactory {

    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        Class<T> rawType = (Class<T>) type.getRawType();

        AutoGson annotation = rawType.getAnnotation(AutoGson.class);
        // Only deserialize classes decorated with @AutoGson.
        if (annotation == null) {
            return null;
        }

        return (TypeAdapter<T>) gson.getAdapter(annotation.autoClass());
    }
}
