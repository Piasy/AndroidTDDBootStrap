package com.github.piasy.common.utils.model;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

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

        if (type.getType() instanceof ParameterizedType) {
            ParameterizedType p = (ParameterizedType) type.getType();
            Type[] actualTypeArgs = p.getActualTypeArguments();
            if (actualTypeArgs != null && actualTypeArgs.length > 0) {

            }
        }

        return (TypeAdapter<T>) gson.getAdapter(annotation.autoClass());
    }
}
