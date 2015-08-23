package com.github.piasy.model.rest.github;

import com.github.piasy.model.entities.GithubUserSearchResult;
import com.github.piasy.model.entities.GithubUser;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by Piasy{github.com/Piasy} on 15/7/23.
 */
public interface GithubAPI {

    @GET("/search/users")
    Observable<GithubUserSearchResult> searchGithubUsers(@Query("q") String query,
            @Query("sort") String sort, @Query("order") String order);

    @GET("/users/{username}")
    Observable<GithubUser> getGithubUser(@Path("username") String username);
}
