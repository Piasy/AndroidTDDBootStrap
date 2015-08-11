package com.piasy.model.rest;

import com.piasy.common.Constants;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by Piasy{github.com/Piasy} on 15/7/23.
 */
public class RestProvider {

    private RestProvider() {
    }

    public static RestAdapter provideRestAdapter() {
        return RestAdapterHolder.sRestAdapter;
    }

    private static class RestAdapterHolder {
        // lazy instantiate
        private static volatile RestAdapter sRestAdapter = new RestAdapter.Builder()
                .setEndpoint(Constants.GITHUB_SERVER_ENDPOINT)
                .setConverter(new GsonConverter(GsonProvider.provideGson()))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
    }

}
