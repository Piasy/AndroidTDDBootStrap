package com.github.piasy.octostars.repos;

import com.github.piasy.bootstrap.base.rx.DisperseTransformer;
import com.github.piasy.bootstrap.base.utils.RxUtil;
import com.github.piasy.yamvp.dagger2.ActivityScope;
import io.reactivex.Observable;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by Piasy{github.com/Piasy} on 22/01/2017.
 */

@ActivityScope
public class RepositoryRepo {
    public static final long INVALID_ID = -1;
    public static final GitHubRepo INVALID_REPO = GitHubRepo.fake(INVALID_ID, "fake");

    private final RepoApiSource mRepoApiSource;
    private final RepoDbSource mRepoDbSource;

    @Inject
    RepositoryRepo(final RepoApiSource repoApiSource, final RepoDbSource repoDbSource) {
        mRepoApiSource = repoApiSource;
        mRepoDbSource = repoDbSource;
    }

    public Observable<GitHubRepo> get(final String fullName, final boolean refresh) {
        return RxUtil.repoGet(
                mRepoDbSource.get(fullName),
                mRepoApiSource.repo(fullName)
                        .doOnNext(mRepoDbSource::put),
                refresh
        );
    }

    public Observable<List<GitHubRepo>> get(final Collection<String> fullNames) {
        return Observable.fromIterable(fullNames)
                .compose(new DisperseTransformer<>(500))
                .flatMap(fullName -> get(fullName, false))
                .toList()
                .toObservable();
    }
}
