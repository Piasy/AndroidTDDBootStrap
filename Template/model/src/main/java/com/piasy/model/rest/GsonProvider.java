package com.piasy.model.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.piasy.common.Constants;
import com.piasy.common.utils.model.AutoParcelTypeAdapterFactory;

/**
 * Created by Piasy{github.com/Piasy} on 15/7/23.
 */
public class GsonProvider {

    private GsonProvider() {
    }

    public static Gson provideGson() {
        return GsonHolder.sGson;
    }

    private static class GsonHolder {
        // lazy instantiate
        private static volatile Gson sGson = new GsonBuilder()
                //.excludeFieldsWithoutExposeAnnotation()   //not exclude for auto parcel
                .registerTypeAdapterFactory(new AutoParcelTypeAdapterFactory())
                .setDateFormat(Constants.TimeFormat.ISO_8601)
                .setPrettyPrinting()
                .create();
    }

}
