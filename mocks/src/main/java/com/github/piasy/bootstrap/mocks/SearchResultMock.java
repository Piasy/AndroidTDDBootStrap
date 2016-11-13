package com.github.piasy.bootstrap.mocks;

/**
 * Created by Piasy{github.com/Piasy} on 13/11/2016.
 */

public final class SearchResultMock {
    private SearchResultMock() {
        // no instance
    }

    public static String simplified() {
        return "{\"total_count\":1,\"incomplete_results\":false,\"items\":[{\"login\":\"Piasy\"," +
               "\"avatar_url\":\"https://avatars.githubusercontent.com/u/3098704?v=3\"," +
               "\"type\":\"User\"}]}";
    }
}
