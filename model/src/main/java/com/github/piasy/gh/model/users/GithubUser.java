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

package com.github.piasy.gh.model.users;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.github.piasy.base.model.jsr310.ZonedDateTimeDelightAdapter;
import com.github.piasy.gh.model.DateTimeFormatterProvider;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.squareup.sqldelight.RowMapper;
import org.threeten.bp.ZonedDateTime;

/**
 * Created by Piasy{github.com/Piasy} on 15/7/23.
 */
@SuppressWarnings("PMD.MethodNamingConventions")
@AutoValue
public abstract class GithubUser implements GithubUserModel, Parcelable {

    public static final String GITHUB_USER_TYPE_USER = "User";
    public static final String GITHUB_USER_TYPE_ORGANIZATION = "Organization";

    public static final String ICONIFY_ICONS_USER = "{md-person}";
    public static final String ICONIFY_ICONS_ORG = "{md-people}";

    public static final Factory<GithubUser> FACTORY = new Factory<>(AutoValue_GithubUser::new,
            new ZonedDateTimeDelightAdapter(DateTimeFormatterProvider.provideDateTimeFormatter()));

    public static final RowMapper<GithubUser> MAPPER = FACTORY.get_allMapper();

    public static TypeAdapter<GithubUser> typeAdapter(final Gson gson) {
        return new AutoValue_GithubUser.GsonTypeAdapter(gson);
    }

    @NonNull
    public static Builder builder() {
        return new AutoValue_GithubUser.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder id(@Nullable Long id);

        public abstract Builder login(String login);

        public abstract Builder avatar_url(String avatarUrl);

        public abstract Builder type(String type);

        public abstract Builder created_at(@Nullable ZonedDateTime createdAt);

        public abstract GithubUser build();
    }
}
