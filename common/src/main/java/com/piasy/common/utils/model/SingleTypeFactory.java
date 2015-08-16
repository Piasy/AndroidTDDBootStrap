/*
package com.piasy.common.utils.model;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.$Gson$Preconditions;
import com.google.gson.reflect.TypeToken;

*/
/**
 * Created by Piasy{github.com/Piasy} on 15/8/16.
 *//*

public class SingleTypeFactory implements TypeAdapterFactory {
    private final TypeToken<?> exactType;
    private final boolean matchRawType;
    private final Class<?> hierarchyType;
    private final JsonSerializer<?> serializer;
    private final JsonDeserializer<?> deserializer;

    private SingleTypeFactory(Object typeAdapter, TypeToken<?> exactType, boolean matchRawType,
            Class<?> hierarchyType) {
        serializer = typeAdapter instanceof JsonSerializer
                ? (JsonSerializer<?>) typeAdapter
                : null;
        deserializer = typeAdapter instanceof JsonDeserializer
                ? (JsonDeserializer<?>) typeAdapter
                : null;
        $Gson$Preconditions.checkArgument(serializer != null || deserializer != null);
        this.exactType = exactType;
        this.matchRawType = matchRawType;
        this.hierarchyType = hierarchyType;
    }

    @SuppressWarnings("unchecked") // guarded by typeToken.equals() call
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        boolean matches = exactType != null
                ? exactType.equals(type) || matchRawType && exactType.getType() == type.getRawType()
                : hierarchyType.isAssignableFrom(type.getRawType());
        return matches
                ? new TreeTypeAdapter<T>((JsonSerializer<T>) serializer,
                (JsonDeserializer<T>) deserializer, gson, type, this)
                : null;
    }
}
*/
