package com.github.piasy.octostars.repos;

import android.annotation.SuppressLint;
import io.reactivex.Observable;

/**
 * Created by Piasy{github.com/Piasy} on 22/01/2017.
 */

public class RepositoryRepo {
    public static final long INVALID_ID = -1;
    public static final GitHubRepo INVALID_REPO = GitHubRepo.fake(INVALID_ID, "fake");

    private final RepoApiSource mRepoApiSource;
    private final RepoDbSource mRepoDbSource;

    public RepositoryRepo(final RepoApiSource repoApiSource, final RepoDbSource repoDbSource) {
        mRepoApiSource = repoApiSource;
        mRepoDbSource = repoDbSource;
    }

    // gradle build will compile code use `Objects.requireNonNull()`
    @SuppressLint("NewApi")
    public Observable<GitHubRepo> get(final String fullName) {
        final Observable<GitHubRepo> remote = mRepoApiSource.repo(fullName)
                .doOnNext(mRepoDbSource::put)
                .take(0); // only used to update db and trigger another onNext
        return Observable.merge(mRepoDbSource.observe(fullName), remote);
    }
}
