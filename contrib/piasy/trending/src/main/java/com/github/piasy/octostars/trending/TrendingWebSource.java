package com.github.piasy.octostars.trending;

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
