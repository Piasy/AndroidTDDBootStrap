package com.github.piasy.octostars.trending;

import com.github.piasy.bootstrap.base.utils.RxUtil;
import com.github.piasy.yamvp.dagger2.ActivityScope;
import com.github.piasy.yamvp.rx.YaRxPresenter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import javax.inject.Inject;

/**
 * Created by Piasy{github.com/Piasy} on 20/09/2016.
 */

@ActivityScope
class TrendingPresenter extends YaRxPresenter<TrendingView> {
    private final TrendingRepo mTrendingRepo;

    @Inject
    TrendingPresenter(final TrendingRepo trendingRepo) {
        super();
        mTrendingRepo = trendingRepo;
    }

    void loadTrending(final String lang, final @TrendingRepo.Since String since) {
        addUtilDestroy(mTrendingRepo.trending(lang, since, false)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribe(getView()::showTrending, RxUtil.OnErrorLogger));
    }
}
