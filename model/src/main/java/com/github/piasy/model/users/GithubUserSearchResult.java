package com.github.piasy.model.users;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import com.google.auto.value.AutoValue;
import com.ryanharter.auto.value.gson.annotations.AutoGson;
import java.util.List;

/**
 * Created by Piasy{github.com/Piasy} on 15/7/23.
 *
 * Github user search result presentation, immutable object.
 */
@AutoValue
@AutoGson(AutoValue_GithubUserSearchResult.GsonTypeAdapter.class)
public abstract class GithubUserSearchResult implements Parcelable {

    /**
     * get total count for search result.
     *
     * @return total count for search result.
     */
    @SuppressWarnings("PMD.MethodNamingConventions")
    public abstract int total_count();

    /**
     * is incomplete results.
     *
     * @return is incomplete results.
     */
    @SuppressWarnings("PMD.MethodNamingConventions")
    public abstract boolean incomplete_results();

    /**
     * result items.
     *
     * @return result items.
     */
    @NonNull
    public abstract List<GithubUser> items();
}
