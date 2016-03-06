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
     * public static factory method to create a builder.
     *
     * @return builder to build immutable object.
     */
    public static Builder builder() {
        return new AutoParcel_GithubUserSearchResult.Builder();
    }

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

    /**
     * Builder class to build immutable {@link GithubUserSearchResult} object.
     */
    @AutoParcel.Builder
    public abstract static class Builder {

        /**
         * set total_count.
         *
         * @param totalCount value to set.
         * @return Builder instance with value set.
         */
        @SuppressWarnings("PMD.MethodNamingConventions")
        @NonNull
        public abstract Builder total_count(int totalCount);

        /**
         * set incomplete_results
         *
         * @param incompleteResults value to set.
         * @return Builder instance with value set.
         */
        @SuppressWarnings("PMD.MethodNamingConventions")
        @NonNull
        public abstract Builder incomplete_results(boolean incompleteResults);

        /**
         * set items.
         *
         * @param items value to set.
         * @return Builder instance with value set.
         */
        @NonNull
        public abstract Builder items(@NonNull List<GithubUser> items);

        /**
         * build the immutable {@link GithubUserSearchResult} object.
         *
         * @return the immutable {@link GithubUserSearchResult} object.
         */
        @NonNull
        public abstract GithubUserSearchResult build();
    }
}
