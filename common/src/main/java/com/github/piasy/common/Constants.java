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

package com.github.piasy.common;

import java.util.regex.Pattern;

/**
 * Created by Piasy{github.com/Piasy} on 15/7/23.
 *
 * A non-instantiable utility class that hold all constants definitions.
 */
public final class Constants {

    // ============= begin Server Endpoint =============
    /**
     * Server endpoint, github service endpoint.
     */
    public static final String GITHUB_SERVER_ENDPOINT = "https://api.github.com/";
    // ============= begin Server Endpoint =============

    // ============= begin GithubAPIParams =============
    /**
     * Search service params, sort by followers.
     */
    public static final String GITHUB_API_PARAMS_SEARCH_SORT_FOLLOWER = "followers";

    /**
     * Search service params, sort by rrepositoriese.
     */
    public static final String GITHUB_API_PARAMS_SEARCH_SORT_REPO = "repositories";

    /**
     * Search service params, sort by joined date.
     */
    public static final String GITHUB_API_PARAMS_SEARCH_SORT_JOINED = "joined";

    /**
     * Search service params, ordered asc.
     */
    public static final String GITHUB_API_PARAMS_SEARCH_ORDER_ASC = "asc";

    /**
     * Search service params, ordered desc.
     */
    public static final String GITHUB_API_PARAMS_SEARCH_ORDER_DESC = "desc";
    // ============= end GithubAPIParams =============

    // ============= begin TimeFormat =============
    /**
     * Time format ISO_8601.
     */
    public static final String TIME_FORMAT_ISO_8601 = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    // ============= end TimeFormat =============

    // ============= begin REPatterns =============
    /**
     * Email re pattern.
     */
    public static final Pattern RE_PATTERNS_EMAIL_PATTERN =
            Pattern.compile("^[_A-Za-z0-9-\\+]+@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    // ============= end REPatterns =============

    // ============= begin IconifyIcons =============
    /**
     * Iconify icon {user}.
     */
    public static final String ICONIFY_ICONS_USER = "{md-person}";

    /**
     * Iconify icon {org}.
     */
    public static final String ICONIFY_ICONS_ORG = "{md-people}";
    // ============= end IconifyIcons =============

    private Constants() {
        // no instance
    }
}
