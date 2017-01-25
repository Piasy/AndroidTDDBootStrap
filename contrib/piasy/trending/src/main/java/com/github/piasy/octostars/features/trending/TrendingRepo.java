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

import android.support.annotation.StringDef;
import com.github.piasy.bootstrap.base.utils.RxUtil;
import com.github.piasy.octostars.repos.GitHubRepo;
import com.github.piasy.octostars.repos.RepositoryRepo;
import com.github.piasy.yamvp.dagger2.ActivityScope;
import io.reactivex.Observable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by Piasy{github.com/Piasy} on 23/01/2017.
 */

@ActivityScope
public class TrendingRepo {
    public static final String SINCE_DAILY = "daily";
    public static final String SINCE_WEEKLY = "weekly";
    public static final String SINCE_MONTHLY = "monthly";

    private final TrendingWebSource mWebSource;
    private final TrendingSpSource mSpSource;
    private final RepositoryRepo mRepositoryRepo;

    @Inject
    TrendingRepo(final TrendingWebSource webSource, final TrendingSpSource spSource,
            final RepositoryRepo repositoryRepo) {
        mWebSource = webSource;
        mSpSource = spSource;
        mRepositoryRepo = repositoryRepo;
    }

    public Observable<List<GitHubRepo>> trending(final String lang,
            final @TrendingRepo.Since String since, final boolean refresh) {
        return RxUtil.repoGet(
                mSpSource.get(lang, since),
                mWebSource.trending(lang, since)
                        .doOnNext(trending -> mSpSource.put(trending, lang, since)),
                refresh)
                .flatMap(mRepositoryRepo::get);
    }

    @Retention(RetentionPolicy.SOURCE)
    @StringDef(value = { SINCE_DAILY, SINCE_WEEKLY, SINCE_MONTHLY })
    public @interface Since {
    }
}
