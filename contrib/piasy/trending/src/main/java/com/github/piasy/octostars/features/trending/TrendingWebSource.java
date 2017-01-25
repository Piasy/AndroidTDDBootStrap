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

import android.support.annotation.VisibleForTesting;
import com.github.piasy.yamvp.dagger2.ActivityScope;
import io.reactivex.Observable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Created by Piasy{github.com/Piasy} on 23/01/2017.
 */

@ActivityScope
class TrendingWebSource {
    private static final String TRENDING_URL_FORMATTER = "https://github.com/trending/%s?since=%s";

    private final OkHttpClient mOkHttpClient;

    @Inject
    TrendingWebSource(final OkHttpClient okHttpClient) {
        mOkHttpClient = okHttpClient;
    }

    Observable<List<String>> trending(final String lang,
            final @TrendingRepo.Since String since) {
        return Observable.create(emitter -> {
            final String html = trendingHtml(lang, since);
            if (emitter.isDisposed()) {
                return;
            }

            final List<String> trending = parseTrending(Jsoup.parse(html));
            if (emitter.isDisposed()) {
                return;
            }

            emitter.onNext(trending);
            emitter.onComplete();
        });
    }

    private String trendingHtml(final String lang,
            final @TrendingRepo.Since String since) throws IOException {
        final Request request = new Request.Builder()
                .url(String.format(TRENDING_URL_FORMATTER, lang, since))
                .get()
                .build();
        final Response response = mOkHttpClient.newCall(request).execute();
        return response.body().string();
    }

    @VisibleForTesting
    List<String> parseTrending(final Document document) {
        final List<String> trending = new ArrayList<>();
        final Elements repoList = document.getElementsByClass("repo-list");
        if (repoList == null || repoList.isEmpty()) {
            return trending;
        }

        final Elements repos = repoList.get(0).getElementsByTag("li");
        if (repos == null || repos.isEmpty()) {
            return trending;
        }

        final int size = repos.size();
        for (int i = 0; i < size; i++) {
            final Elements fullName = repos.get(i).getElementsByTag("h3");
            if (fullName != null && !fullName.isEmpty()) {
                trending.add(fullName.get(0).text().replace(" ", ""));
            }
        }

        return trending;
    }
}
