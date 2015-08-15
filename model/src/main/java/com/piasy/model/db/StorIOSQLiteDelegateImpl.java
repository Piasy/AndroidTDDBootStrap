package com.piasy.model.db;

import com.piasy.model.dao.GithubUserTableMeta;
import com.piasy.model.entities.GithubUser;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import java.util.List;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by Piasy{github.com/Piasy} on 15/8/14.
 */
public class StorIOSQLiteDelegateImpl implements StorIOSQLiteDelegate {
    private final StorIOSQLite mStorIOSQLite;

    public StorIOSQLiteDelegateImpl(StorIOSQLite mStorIOSQLite) {
        this.mStorIOSQLite = mStorIOSQLite;
    }

    @Override
    public void deleteAllGithubUser() {
        mStorIOSQLite.delete()
                .byQuery(GithubUserTableMeta.getDeleteAllQuery())
                .prepare()
                .executeAsBlocking();
    }

    @Override
    public void putAllGithubUser(List<GithubUser> users) {
        mStorIOSQLite.put().objects(users).prepare().executeAsBlocking();
    }

    @Override
    public List<GithubUser> getAllGithubUser() {
        return mStorIOSQLite.get()
                .listOfObjects(GithubUser.class)
                .withQuery(GithubUserTableMeta.sQueryAll)
                .prepare()
                .executeAsBlocking();
    }

    @Override
    public Observable<List<GithubUser>> getAllGithubUserReactively() {
        return mStorIOSQLite.get()
                .listOfObjects(GithubUser.class)
                .withQuery(GithubUserTableMeta.sQueryAll)
                .prepare()
                .createObservable()
                .subscribeOn(Schedulers.io());
    }
}
