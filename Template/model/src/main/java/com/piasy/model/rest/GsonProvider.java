package com.piasy.model.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by Piasy{github.com/Piasy} on 15/7/23.
 */
public class GsonProvider {

    public static Gson provideGson() {
        return GsonHolder.sGson;
    }

    private static class GsonHolder {
        private static volatile Gson sGson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();
    }

}
