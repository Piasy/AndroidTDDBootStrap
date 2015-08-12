package com.piasy.model.entities;

import com.piasy.common.utils.model.AutoGson;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Date;

import auto.parcel.AutoParcel;

/**
 * Created by Piasy{github.com/Piasy} on 15/7/23.
 */
@AutoParcel
@AutoGson(autoParcelClass = AutoParcel_GithubUser.class)
public abstract class GithubUser {

    @NonNull
    public static Builder builder() {
        return new AutoParcel_GithubUser.Builder();
    }

    public abstract long id();

    @NonNull
    public abstract String login();

    @NonNull
    public abstract String avatar_url();

    @NonNull
    public abstract String type();

    @Nullable
    public abstract String email();

    public abstract int followers();

    public abstract int following();

    @Nullable
    public abstract Date created_at();

    public interface GithubUserType {
        String USER = "User";
        String ORGANIZATION = "Organization";
    }

    @AutoParcel.Builder
    public abstract static class Builder {

        @NonNull
        public abstract Builder login(@NonNull String login);

        @NonNull
        public abstract Builder id(long id);

        @NonNull
        public abstract Builder avatar_url(@NonNull String avatar_url);

        @NonNull
        public abstract Builder type(@NonNull String type);

        @NonNull
        public abstract Builder email(String email);

        @NonNull
        public abstract Builder followers(int followers);

        @NonNull
        public abstract Builder following(int following);

        @NonNull
        public abstract Builder created_at(Date created_at);

        @NonNull
        public abstract GithubUser build();

    }
}
