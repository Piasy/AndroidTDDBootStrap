package com.github.piasy.gh.model.users;

import android.os.Parcelable;
import com.google.auto.value.AutoValue;
import com.ryanharter.auto.value.gson.annotations.AutoGson;
import java.util.List;

/**
 * Created by Piasy{github.com/Piasy} on 15/7/23.
 */
@SuppressWarnings("PMD.MethodNamingConventions")
@AutoValue
@AutoGson(AutoValue_GithubUserSearchResult.GsonTypeAdapter.class)
public abstract class GithubUserSearchResult implements Parcelable {

    public abstract int total_count();

    public abstract boolean incomplete_results();

    public abstract List<GithubUser> items();
}
