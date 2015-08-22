package com.github.piasy.common;

import java.util.regex.Pattern;

/**
 * Created by Piasy{github.com/Piasy} on 15/7/23.
 */
public class Constants {
    public static final String GITHUB_SERVER_ENDPOINT = "https://api.github.com";

    public interface GithubAPIParams {
        String SEARCH_SORT_FOLLOWER = "followers";
        String SEARCH_SORT_REPO = "repositories";
        String SEARCH_SORT_JOINED = "joined";

        String SEARCH_ORDER_ASC = "asc";
        String SEARCH_ORDER_DESC = "desc";
    }

    public interface TimeFormat {
        String ISO_8601 = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    }

    public interface REPatterns {
        Pattern EMAIL_PATTERN = Pattern.compile(
                "^[_A-Za-z0-9-\\+]+@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    }

    public interface IconifyIcons {
        String USER = "{md-person}";
        String ORG = "{md-people}";
    }
}
