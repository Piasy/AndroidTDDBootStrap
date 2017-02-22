/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Piasy
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

package com.github.piasy.octostars.repos;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import com.github.piasy.octostars.users.GitHubUser;
import com.github.piasy.octostars.users.UserRepo;
import com.github.piasy.yamvp.dagger2.ActivityScope;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqldelight.RowMapper;
import hu.akarnokd.rxjava.interop.RxJavaInterop;
import io.reactivex.Observable;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import javax.inject.Inject;

/**
 * Created by Piasy{github.com/Piasy} on 15/8/14.
 */
@ActivityScope
class RepoDbSource {

    private final BriteDatabase mBriteDb;
    private final UserRepo mUserRepo;

    private final RowMapper<GitHubRepo> mRepoMapper;

    private final GitHubRepo.Insert_repo mInsertion;
    private final GitHubRepo.Delete_repo mDeletion;

    @Inject
    RepoDbSource(final BriteDatabase briteDb, final UserRepo userRepo) {
        mBriteDb = briteDb;
        mUserRepo = userRepo;

        final GitHubRepo.Factory<GitHubRepo> repoFactory = new GitHubRepo.Factory<>(this::populate);
        mRepoMapper = repoFactory.select_by_full_nameMapper();

        mInsertion = new GitHubRepo.Insert_repo(mBriteDb.getWritableDatabase());
        mDeletion = new GitHubRepo.Delete_repo(mBriteDb.getWritableDatabase());
    }

    List<GitHubRepo> getNow(final String fullName) {
        final Cursor cursor = mBriteDb.query(GitHubRepo.SELECT_BY_FULL_NAME, fullName);
        try {
            if (cursor.moveToFirst()) {
                return Collections.singletonList(mRepoMapper.map(cursor));
            }
            return Collections.emptyList();
        } finally {
            cursor.close();
        }
    }

    Observable<GitHubRepo> get(final String fullName) {
        return Observable.fromCallable(() -> getNow(fullName))
                .filter(list -> !list.isEmpty())
                .map(list -> list.get(0));
    }

    Observable<GitHubRepo> observe(final String fullName) {
        return RxJavaInterop.toV2Observable(
                mBriteDb.createQuery(GitHubRepo.TABLE_NAME, GitHubRepo.SELECT_BY_FULL_NAME,
                        fullName)
                        .mapToOneOrDefault(mRepoMapper::map, RepositoryRepo.INVALID_REPO)
                        .filter(repo -> repo != RepositoryRepo.INVALID_REPO));
    }

    void put(final List<GitHubRepo> repos) {
        final BriteDatabase.Transaction transaction = mBriteDb.newTransaction();
        try {
            final int size = repos.size();
            for (int i = 0; i < size; i++) {
                mBriteDb.executeInsert(GitHubUser.TABLE_NAME, repos.get(i).insert(mInsertion));
            }
            transaction.markSuccessful();
        } finally {
            transaction.end();
        }
    }

    void put(final GitHubRepo repo) {
        mBriteDb.executeInsert(GitHubRepo.TABLE_NAME, repo.insert(mInsertion));
        mUserRepo.put(repo.owner());
    }

    @VisibleForTesting
    int deleteAll() {
        return mBriteDb.delete(GitHubRepo.TABLE_NAME, "1");
    }

    // CHECKSTYLE:OFF
    @SuppressWarnings({
            "PMD.ExcessiveParameterList", "PMD.PreserveStackTrace", "PMD.UseObjectForClearerAPI"
    })
    private GitHubRepo populate(final long id, final @NonNull String name,
            final @NonNull String fullName, final @NonNull String ownerLogin,
            final @Nullable String description, final @NonNull String htmlUrl, final boolean fork,
            final int subscribersCount, final int stargazersCount, final int forksCount) {
        // CHECKSTYLE:ON
        try {
            final GitHubUser owner = mUserRepo.get(ownerLogin, false).blockingFirst();
            return new AutoValue_GitHubRepo(id, name, fullName, ownerLogin, description, htmlUrl,
                    fork, subscribersCount, stargazersCount, forksCount, owner);
        } catch (NoSuchElementException e) {
            mBriteDb.executeUpdateDelete(GitHubRepo.TABLE_NAME, GitHubRepo.delete(mDeletion, id));
            throw new IllegalStateException(
                    String.format("Owner(%s) of repo(%s) not found!", ownerLogin, fullName));
        }
    }
}
