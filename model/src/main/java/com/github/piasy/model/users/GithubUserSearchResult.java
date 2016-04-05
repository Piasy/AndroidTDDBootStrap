package com.github.piasy.model.users;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import auto.parcel.AutoParcel;
import com.github.piasy.base.model.gson.AutoGson;
import java.util.List;

/**
 * Created by Piasy{github.com/Piasy} on 15/7/23.
 *
 * Github user search result presentation, immutable object.
 */
@AutoParcel
@AutoGson(autoClass = AutoParcel_GithubUserSearchResult.class)
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
