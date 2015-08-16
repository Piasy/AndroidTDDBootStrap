package com.piasy.model.entities;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import auto.parcel.AutoParcel;
import com.piasy.common.utils.model.AutoGson;
import org.threeten.bp.ZonedDateTime;

/**
 * Created by Piasy{github.com/Piasy} on 15/7/23.
 */
@AutoParcel
@AutoGson(autoClass = AutoParcel_GithubUser.class)
public abstract class GithubUser {

    @NonNull
    public static Builder builder() {
        return new AutoParcel_GithubUser.Builder();
    }

    @NonNull
    public static GithubUser from(GithubUser user) {
        return new AutoParcel_GithubUser.Builder(user).build();
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
    public abstract ZonedDateTime created_at();

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
        public abstract Builder created_at(ZonedDateTime created_at);

        @NonNull
        public abstract GithubUser build();
    }
}
