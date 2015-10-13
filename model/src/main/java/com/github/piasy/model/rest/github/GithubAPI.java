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

package com.github.piasy.model.rest.github;

import com.github.piasy.model.entities.GithubUser;
import com.github.piasy.model.entities.GithubUserSearchResult;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by Piasy{github.com/Piasy} on 15/7/23.
 *
 * Definition of Github API.
 */
public interface GithubAPI {

    /**
     * search github users.
     *
     * @param query search query.
     * @param sort sort type, {@link com.github.piasy.common
     * .Constants#GITHUB_API_PARAMS_SEARCH_SORT_FOLLOWER},
     * {@link com.github.piasy.common.Constants#GITHUB_API_PARAMS_SEARCH_SORT_JOINED}, or {@link
     * com.github.piasy.common.Constants#GITHUB_API_PARAMS_SEARCH_SORT_REPO}
     * @param order order type, {@link com.github.piasy.common
     * .Constants#GITHUB_API_PARAMS_SEARCH_ORDER_ASC},
     * or {@link com.github.piasy.common.Constants#GITHUB_API_PARAMS_SEARCH_ORDER_DESC}.
     * @return the search result.
     */
    @GET("search/users")
    Observable<GithubUserSearchResult> searchGithubUsers(@Query("q") String query,
            @Query("sort") String sort, @Query("order") String order);

    /**
     * get full user info.
     *
     * @param username username of the user.
     * @return full user info.
     */
    @GET("users/{username}")
    Observable<GithubUser> getGithubUser(@Path("username") String username);
}
