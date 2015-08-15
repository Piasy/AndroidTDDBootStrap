package com.piasy.common.utils.model;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

/**
 * copy from: https://gist.github.com/JakeWharton/0d67d01badcee0ae7bc9
 */
public class AutoParcelTypeAdapterFactory implements TypeAdapterFactory {

    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        Class<T> rawType = (Class<T>) type.getRawType();

        AutoGson annotation = rawType.getAnnotation(AutoGson.class);
        // Only deserialize classes decorated with @AutoGson.
        if (annotation == null) {
            return null;
        }

        return (TypeAdapter<T>) gson.getAdapter(annotation.autoParcelClass());
    }
}
