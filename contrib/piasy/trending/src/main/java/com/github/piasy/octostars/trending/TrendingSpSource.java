package com.github.piasy.octostars.trending;

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
