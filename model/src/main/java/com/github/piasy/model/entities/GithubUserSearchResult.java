package com.github.piasy.model.entities;

import android.support.annotation.NonNull;
import auto.parcel.AutoParcel;
import com.github.piasy.common.model.AutoGson;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Piasy{github.com/Piasy} on 15/7/23.
 */
@AutoParcel
@AutoGson(autoClass = AutoParcel_GithubUserSearchResult.class)
public abstract class GithubUserSearchResult {
    public abstract int total_count();

    public abstract boolean incomplete_results();

    @NonNull
    public abstract List<GithubUser> items();

    @AutoParcel.Builder
    public static abstract class Builder {

        @NonNull
        public abstract Builder total_count(int total_count);

        @NonNull
        public abstract Builder incomplete_results(boolean incomplete_results);

        @NonNull
        public abstract Builder items(@NonNull List<GithubUser> items);

        @NonNull
        public abstract GithubUserSearchResult build();
    }

    public static Builder builder() {
        return new AutoParcel_GithubUserSearchResult.Builder();
    }

    public static GithubUserSearchResult from(GithubUserSearchResult result) {
        // NOTE!!! this way is shallow copy
        //return new AutoParcel_GithubUserSearchResult.Builder(result).build();

        // Deep copy in this way
        return new AutoParcel_GithubUserSearchResult.Builder().total_count(result.total_count())
                .incomplete_results(result.incomplete_results())
                .items(new ArrayList<>(result.items()))
                .build();
    }
}
