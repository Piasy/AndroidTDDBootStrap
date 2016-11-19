package com.github.piasy.bootstrap.model.users;

import android.os.Parcelable;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import java.util.List;

/**
 * Created by Piasy{github.com/Piasy} on 15/7/23.
 */
@SuppressWarnings("PMD.MethodNamingConventions")
@AutoValue
public abstract class GithubUserSearchResult implements Parcelable {

    public abstract int total_count();

    public abstract boolean incomplete_results();

    public abstract List<GithubUser> items();

    public static TypeAdapter<GithubUserSearchResult> typeAdapter(final Gson gson) {
        return new $AutoValue_GithubUserSearchResult.GsonTypeAdapter(gson);
    }
}
