package com.piasy.model.db;

import com.piasy.model.entities.GithubUser;
import java.util.List;
import rx.Observable;

/**
 * Created by Piasy{github.com/Piasy} on 15/8/14.
 */
public interface StorIOSQLiteDelegate {
    void deleteAllGithubUser();

    void putAllGithubUser(List<GithubUser> users);

    List<GithubUser> getAllGithubUser();

    Observable<List<GithubUser>> getAllGithubUserReactively();
}
