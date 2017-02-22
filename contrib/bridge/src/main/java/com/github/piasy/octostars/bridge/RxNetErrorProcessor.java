/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Piasy
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.piasy.octostars.bridge;

import android.text.TextUtils;
import com.google.gson.Gson;
import javax.inject.Inject;
import javax.inject.Singleton;
import retrofit2.HttpException;
import rx.functions.Action1;
import timber.log.Timber;

/**
 * Created by Piasy{github.com/Piasy} on 15/7/24.
 *
 * used for Rx network error handling
 * Usage: Observable.subscribe(onNext, RxNetErrorProcessor.NetErrorProcessor)
 * run in the observeOn() thread
 * onErrorReturn run in subscribeOn thread (retrofit run in background thread, not good for
 * error handling)
 *
 * Note: if you handle onError for the net request, than you should call it manually:
 * RxNetErrorProcessor.NetErrorProcessor.call(throwable);
 * Otherwise this method won't be invoked
 */
@Singleton
public class RxNetErrorProcessor implements Action1<Throwable> {

    private final Gson mGson;

    @Inject
    RxNetErrorProcessor(final Gson gson) {
        mGson = gson;
    }

    /**
     * Final path of network error processing, submit it to server at production build.
     */
    @Override
    public void call(final Throwable throwable) {
        Timber.e(throwable, "RxNetErrorProcessor");
    }

    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    public boolean tryWithApiError(final Throwable throwable, final Action1<ApiError> handler) {
        if (throwable instanceof HttpException) {
            final HttpException exception = (HttpException) throwable;
            try {
                final String errorBody = exception.response().errorBody().string();
                final ApiError apiError = mGson.fromJson(errorBody, ApiError.class);
                if (!TextUtils.isEmpty(apiError.message())) {
                    handler.call(apiError);
                    return true;
                }
            } catch (Exception e) {
                call(throwable);
            }
        } else {
            call(throwable);
        }
        return false;
    }
}
