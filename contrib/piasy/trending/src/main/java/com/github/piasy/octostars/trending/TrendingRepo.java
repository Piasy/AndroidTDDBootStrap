package com.github.piasy.octostars.trending;

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
