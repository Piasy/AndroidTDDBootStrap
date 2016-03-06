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

package com.github.piasy.base.test;

/**
 * Created by Piasy{github.com/Piasy} on 15/8/9.
 */
public final class MockProvider {

    private MockProvider() {
        // no instance
    }

    public static String provideGithubAPIErrorStr() {
        return "{\"message\":\"Validation Failed\",\"errors\":[{\"resource\":\"Issue\"," +
                "\"field\":\"title\",\"code\":\"missing_field\"}]}";
    }

    public static String provideGithubUserStr() {
        return "{\"login\":\"Piasy\",\"id\":3098704,\"avatar_url\":\"https://avatars" +
                ".githubusercontent.com/u/3098704?v=3\",\"gravatar_id\":\"\"," +
                "\"url\":\"https://api.github.com/users/Piasy\",\"html_url\":\"https://github" +
                ".com/Piasy\",\"followers_url\":\"https://api.github.com/users/Piasy/followers\"," +
                "\"following_url\":\"https://api.github.com/users/Piasy/following{/other_user}\"," +
                "\"gists_url\":\"https://api.github.com/users/Piasy/gists{/gist_id}\"," +
                "\"starred_url\":\"https://api.github.com/users/Piasy/starred{/owner}{/repo}\"," +
                "\"subscriptions_url\":\"https://api.github.com/users/Piasy/subscriptions\"," +
                "\"organizations_url\":\"https://api.github.com/users/Piasy/orgs\"," +
                "\"repos_url\":\"https://api.github.com/users/Piasy/repos\"," +
                "\"events_url\":\"https://api.github.com/users/Piasy/events{/privacy}\"," +
                "\"received_events_url\":\"https://api.github.com/users/Piasy/received_events\"," +
                "\"type\":\"User\",\"site_admin\":false,\"name\":\"Xu Jianlin\"," +
                "\"company\":\"YOLO\",\"blog\":\"http://piasy.github.io/\"," +
                "\"location\":\"Beijing, China\",\"email\":\"xz4215@gmail.com\"," +
                "\"hireable\":true,\"bio\":null,\"public_repos\":21,\"public_gists\":3," +
                "\"followers\":3,\"following\":25,\"created_at\":\"2012-12-21T14:23:30Z\"," +
                "\"updated_at\":\"2015-07-23T07:27:29Z\"}";
    }

    public static String provideSimplifiedGithubUserStr() {
        return "{\"login\":\"Piasy\",\"avatar_url\":\"https://avatars.githubusercontent" +
                ".com/u/3098704?v=3\",\"type\":\"User\",\"created_at\":\"2012-12-21T14:23:30Z\"}";
    }

    public static String provideEmptyGithubSearchResult() {
        return "{\"total_count\":0,\"incomplete_results\":false,\"items\":[]}";
    }

    public static String provideSimplifiedGithubUserSearchResultStr() {
        return "{\"total_count\":1,\"incomplete_results\":false,\"items\":[{\"login\":\"Piasy\"," +
                "\"avatar_url\":\"https://avatars.githubusercontent.com/u/3098704?v=3\"," +
                "\"type\":\"User\"}]}";
    }
}
