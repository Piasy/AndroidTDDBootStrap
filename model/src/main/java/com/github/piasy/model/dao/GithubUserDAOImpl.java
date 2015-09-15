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

package com.github.piasy.model.dao;

import android.support.annotation.NonNull;
import com.github.piasy.common.Constants;
import com.github.piasy.common.android.utils.net.RxUtil;
import com.github.piasy.model.db.StorIOSQLiteDelegate;
import com.github.piasy.model.entities.GithubUser;
import com.github.piasy.model.rest.github.GithubAPI;
import java.util.ArrayList;
import java.util.List;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by Piasy{github.com/Piasy} on 15/8/5.
 *
 * Implementation of {@link GithubUserDAO}.
 */
public final class GithubUserDAOImpl implements GithubUserDAO {

    private final StorIOSQLiteDelegate mStorIOSQLite;
    private final GithubAPI mGithubAPI;
    private final RxUtil.RxErrorProcessor mRxErrorProcessor;

    /**
     * Provide {@link GithubUserDAO} with the given {@link StorIOSQLiteDelegate}, {@link GithubAPI}
     * and {@link RxUtil.RxErrorProcessor}.
     *
     * @param storIOSQLite stor io SQLite delegate.
     * @param githubAPI GithubAPI instance.
     * @param rxErrorProcessor Rx error processor.
     */
    public GithubUserDAOImpl(final StorIOSQLiteDelegate storIOSQLite, final GithubAPI githubAPI,
            final RxUtil.RxErrorProcessor rxErrorProcessor) {
        mStorIOSQLite = storIOSQLite;
        mGithubAPI = githubAPI;
        mRxErrorProcessor = rxErrorProcessor;
    }

    @NonNull
    @Override
    public Observable<List<GithubUser>> getUsers() {
        // search from cloud
        mGithubAPI.searchGithubUsers("piasy", Constants.GITHUB_API_PARAMS_SEARCH_SORT_JOINED,
                Constants.GITHUB_API_PARAMS_SEARCH_ORDER_DESC)
                .subscribeOn(Schedulers.io())
                .subscribe(searchResult -> {
                    if (searchResult.items().isEmpty()) {
                        // if no cloud data, remove local data
                        mStorIOSQLite.deleteAllGithubUser();
                    } else {
                        // else update local data totally
                        final List<GithubUser> local = mStorIOSQLite.getAllGithubUser();

                        // NOTE!!! create a copy to avoid modify caller's state
                        // there will be two extra List creation, but they will be GCed quickly(if
                        // no memory leak happens), but object creation may have bad effect in
                        // Android platform, so is this a good practice?

                        // UPDATE: create snapshot for params is the caller's duty
                        final List<GithubUser> cloud = searchResult.items();
                        if (local.isEmpty()) {
                            // first time, no local data, show partly cloud data at first
                            // NOTE!!! create a snapshot, to avoid callee see changed params
                            // NOTE again!!! `Collections.unmodifiableList` just limit that the
                            // callee could not modify this List, but modification in caller side
                            // will still be visible in callee!
                            mStorIOSQLite.putAllGithubUser(new ArrayList<>(cloud));
                        }

                        for (int i = 0; i < cloud.size(); i++) {
                            final GithubUser fullUserInfo =
                                    mGithubAPI.getGithubUser(cloud.get(i).login())
                                            .toBlocking()
                                            .single();
                            cloud.set(i, fullUserInfo);
                        }
                        // won't use `cloud` any more, no need to create snapshot for it
                        mStorIOSQLite.putAllGithubUser(cloud);
                    }
                }, mRxErrorProcessor);

        return mStorIOSQLite.getAllGithubUserReactively();
    }
}
