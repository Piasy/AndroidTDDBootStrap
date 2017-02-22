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

package com.github.piasy.octostars.users;

import android.database.sqlite.SQLiteStatement;
import android.os.Parcelable;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import org.threeten.bp.ZonedDateTime;

/**
 * Created by Piasy{github.com/Piasy} on 15/7/23.
 */
@SuppressWarnings("PMD.MethodNamingConventions")
@AutoValue
public abstract class GitHubUser implements GitHubUserModel, Parcelable {

    public static TypeAdapter<GitHubUser> typeAdapter(final Gson gson) {
        return new AutoValue_GitHubUser.GsonTypeAdapter(gson);
    }

    public static Builder builder() {
        return new AutoValue_GitHubUser.Builder();
    }

    static GitHubUser fake(final long id, final String login, final String type) {
        return builder()
                .id(id)
                .login(login)
                .avatar_url("")
                .type(type)
                .build();
    }

    static SQLiteStatement delete(final Delete_by_login delete, final String login) {
        delete.program.clearBindings();
        delete.bind(login);
        return delete.program;
    }

    SQLiteStatement insert(final Insert_user insert) {
        insert.program.clearBindings();
        insert.bind(id(), login(), avatar_url(), type(), updated_at());
        return insert.program;
    }

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder id(final long id);

        public abstract Builder login(final String login);

        public abstract Builder avatar_url(final String avatarUrl);

        public abstract Builder type(final String type);

        public abstract Builder updated_at(final ZonedDateTime updatedAt);

        public abstract GitHubUser build();
    }
}
