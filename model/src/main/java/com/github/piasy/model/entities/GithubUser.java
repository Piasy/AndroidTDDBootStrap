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

package com.github.piasy.model.entities;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import auto.parcel.AutoParcel;
import com.github.piasy.common.model.AutoGson;
import org.threeten.bp.ZonedDateTime;

/**
 * Created by Piasy{github.com/Piasy} on 15/7/23.
 *
 * Github User representation, immutable.
 */
@SuppressWarnings("PMD.TooManyMethods")
@AutoParcel
@AutoGson(autoClass = AutoParcel_GithubUser.class)
public abstract class GithubUser {

    // ============= begin GithubUserType =============
    /**
     * Github User Type User.
     */
    public static final String GITHUB_USER_TYPE_USER = "User";

    /**
     * Github User Type Organization.
     */
    public static final String GITHUB_USER_TYPE_ORGANIZATION = "Organization";
    // ============= end GithubUserType =============

    /**
     * Get the {@link Builder} instance to build a {@link GithubUser} instance.
     *
     * @return the {@link Builder} instance to build a {@link GithubUser} instance.
     */
    @NonNull
    public static Builder builder() {
        return new AutoParcel_GithubUser.Builder();
    }

    ///**
    // * Copy the content from the given instance.
    // * NOTE !!! Bad design, this method doesn't make any sense. Immutable object should only have
    // * one copy, that's itself. Comment it off.
    // *
    // * @param user the instance to copy
    // * @return the copied instance.
    // */
    //@NonNull
    //public static GithubUser from(GithubUser user) {
    //    return new AutoParcel_GithubUser.Builder(user).build();
    //}

    /**
     * Get the id property.
     *
     * @return the id property.
     */
    public abstract long id();

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
     * Get the email property.
     *
     * @return the email property.
     */
    @Nullable
    public abstract String email();

    /**
     * Get the followers property.
     *
     * @return the followers property.
     */
    public abstract int followers();

    /**
     * Get the following property.
     *
     * @return the following property.
     */
    public abstract int following();

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
    @AutoParcel.Builder
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
         * Set the id property.
         *
         * @param id the login property to set.
         * @return the builder after id property is set.
         */
        @NonNull
        public abstract Builder id(long id);

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
         * Set the email property.
         *
         * @param email the login property to set.
         * @return the builder after email property is set.
         */
        @NonNull
        public abstract Builder email(String email);

        /**
         * Set the followers property.
         *
         * @param followers the login property to set.
         * @return the builder after followers property is set.
         */
        @NonNull
        public abstract Builder followers(int followers);

        /**
         * Set the following property.
         *
         * @param following the login property to set.
         * @return the builder after following property is set.
         */
        @NonNull
        public abstract Builder following(int following);

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
