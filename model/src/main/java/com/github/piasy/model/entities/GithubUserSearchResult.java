package com.github.piasy.model.entities;

import android.support.annotation.NonNull;
import auto.parcel.AutoParcel;
import com.github.piasy.common.model.AutoGson;
import java.util.List;

/**
 * Created by Piasy{github.com/Piasy} on 15/7/23.
 *
 * Github user search result presentation, immutable object.
 */
@AutoParcel
@AutoGson(autoClass = AutoParcel_GithubUserSearchResult.class)
public abstract class GithubUserSearchResult {

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
     * public static factory method to create a builder.
     *
     * @return builder to build immutable object.
     */
    public static Builder builder() {
        return new AutoParcel_GithubUserSearchResult.Builder();
    }

    ///**
    // * copy the object from the existing one.
    // * NOTE !!! Bad design, this method doesn't make any sense. Immutable object should only have
    // * one copy, that's itself. Comment it off.
    // *
    // * @param result existing object
    // * @return copied object.
    // */
    //public static GithubUserSearchResult from(GithubUserSearchResult result) {
    //    // NOTE!!! this way is shallow copy
    //    //return new AutoParcel_GithubUserSearchResult.Builder(result).build();
    //
    //    // Deep copy in this way
    //    return new AutoParcel_GithubUserSearchResult.Builder().total_count(result.total_count())
    //            .incomplete_results(result.incomplete_results())
    //            .items(new ArrayList<>(result.items()))
    //            .build();
    //}

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
