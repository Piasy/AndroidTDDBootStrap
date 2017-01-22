package com.github.piasy.octostars.repos;

import android.database.sqlite.SQLiteStatement;
import android.os.Parcelable;
import com.github.piasy.octostars.users.GitHubUser;
import com.github.piasy.octostars.users.UserRepo;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

/**
 * Created by Piasy{github.com/Piasy} on 22/01/2017.
 */

@AutoValue
public abstract class GitHubRepo implements GitHubRepoModel, Parcelable {

    public static TypeAdapter<GitHubRepo> typeAdapter(final Gson gson) {
        return new AutoValue_GitHubRepo.GsonTypeAdapter(gson);
    }

    static GitHubRepo fake(final long id, final String name) {
        final GitHubUser owner = UserRepo.INVALID_USER;
        return new AutoValue_GitHubRepo(id, name, "", owner.login(), "", "", false, 0, 0, 0, owner);
    }

    static SQLiteStatement delete(final Delete_repo delete, final long id) {
        delete.program.clearBindings();
        delete.bind(id);
        return delete.program;
    }

    SQLiteStatement insert(final Insert_repo insert) {
        insert.program.clearBindings();
        insert.bind(id(), name(), full_name(), owner().login(), description(), html_url(), fork(),
                subscribers_count(), stargazers_count(), forks_count());
        return insert.program;
    }

    public abstract GitHubUser owner();
}
