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

package com.github.piasy.octostars.features.trending;

import com.f2prateek.rx.preferences2.RxSharedPreferences;
import com.github.piasy.yamvp.dagger2.ActivityScope;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.reactivex.Observable;
import java.lang.reflect.Type;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by Piasy{github.com/Piasy} on 23/01/2017.
 */

@ActivityScope
class TrendingSpSource {
    private static final String CACHE_KEY_FORMATTER = "trending_%s_%s";

    private final RxSharedPreferences mPreferences;
    private final Gson mGson;

    @Inject
    TrendingSpSource(final RxSharedPreferences preferences, final Gson gson) {
        mPreferences = preferences;
        mGson = gson;
    }

    Observable<List<String>> get(final String lang,
            final @TrendingRepo.Since String since) {
        return Observable.create(emitter -> {
            final String cache = mPreferences.getString(cacheKey(lang, since), "[]").get();
            if ("[]".equals(cache)) {
                emitter.onComplete();
                return;
            }
            final Type type = new TypeToken<List<String>>() {
            }.getType();
            emitter.onNext(mGson.fromJson(cache, type));
            emitter.onComplete();
        });
    }

    void put(final List<String> trending, final String lang,
            final @TrendingRepo.Since String since) {
        mPreferences.getString(cacheKey(lang, since))
                .set(mGson.toJson(trending));
    }

    private String cacheKey(final String lang, final @TrendingRepo.Since String since) {
        return String.format(CACHE_KEY_FORMATTER, lang, since);
    }
}
