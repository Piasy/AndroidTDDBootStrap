package com.piasy.model.db;

import com.piasy.model.entities.GithubUser;
import com.pushtorefresh.storio.sqlite.operations.delete.DeleteResult;
import com.pushtorefresh.storio.sqlite.operations.put.PutResults;
import java.util.List;
import rx.Observable;

/**
 * Created by Piasy{github.com/Piasy} on 15/8/14.
 */
public interface StorIOSQLiteDelegate {
    DeleteResult deleteAllGithubUser();

    PutResults<GithubUser> putAllGithubUser(List<GithubUser> users);

    List<GithubUser> getAllGithubUser();

    Observable<List<GithubUser>> getAllGithubUserReactively();
}
