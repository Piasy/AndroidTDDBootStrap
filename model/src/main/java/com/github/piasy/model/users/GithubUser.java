/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Piasy
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.piasy.model.users;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.auto.value.AutoValue;
import com.ryanharter.auto.value.gson.annotations.AutoGson;
import org.threeten.bp.ZonedDateTime;

/**
 * Created by Piasy{github.com/Piasy} on 15/7/23.
 *
 * Github User representation, immutable.
 */
@SuppressWarnings("PMD.TooManyMethods")
@AutoValue
@AutoGson(AutoValue_GithubUser.GsonTypeAdapter.class)
public abstract class GithubUser implements Parcelable {

    public static final String GITHUB_USER_TYPE_USER = "User";
    public static final String GITHUB_USER_TYPE_ORGANIZATION = "Organization";

    public static final String ICONIFY_ICONS_USER = "{md-person}";
    public static final String ICONIFY_ICONS_ORG = "{md-people}";

    /**
     * Get the {@link Builder} instance to build a {@link GithubUser} instance.
     *
     * @return the {@link Builder} instance to build a {@link GithubUser} instance.
     */
    @NonNull
    public static Builder builder() {
        return new AutoValue_GithubUser.Builder();
    }

    /**
     * Get the login property.
     *
     * @return the login property.
     */
    @NonNull
    public abstract String login();

    /**
     * Get the avatar_url property.
     *
     * @return the avatar_url property.
     */
    @SuppressWarnings("PMD.MethodNamingConventions")
    @NonNull
    public abstract String avatar_url();

    /**
     * Get the type property.
     *
     * @return the type property.
     */
    @NonNull
    public abstract String type();

    /**
     * Get the created_at property.
     *
     * @return the created_at property.
     */
    @SuppressWarnings("PMD.MethodNamingConventions")
    @Nullable
    public abstract ZonedDateTime created_at();

    /**
     * The builder class to build {@link GithubUser} instance.
     */
    @AutoValue.Builder
    public abstract static class Builder {

        /**
         * Set the login property.
         *
         * @param login the login property to set.
         * @return the builder after login property is set.
         */
        @NonNull
        public abstract Builder login(@NonNull String login);

        /**
         * Set the avatar_url property.
         *
         * @param avatarUrl the login property to set.
         * @return the builder after avatar_url property is set.
         */
        @SuppressWarnings("PMD.MethodNamingConventions")
        @NonNull
        public abstract Builder avatar_url(@NonNull String avatarUrl);

        /**
         * Set the type property.
         *
         * @param type the login property to set.
         * @return the builder after type property is set.
         */
        @NonNull
        public abstract Builder type(@NonNull String type);

        /**
         * Set the created_at property.
         *
         * @param createdAt the login property to set.
         * @return the builder after created_at property is set.
         */
        @SuppressWarnings("PMD.MethodNamingConventions")
        @NonNull
        public abstract Builder created_at(ZonedDateTime createdAt);

        /**
         * Build the {@link GithubUser} instance.
         *
         * @return the built {@link GithubUser} instance.
         */
        @NonNull
        public abstract GithubUser build();
    }
}
