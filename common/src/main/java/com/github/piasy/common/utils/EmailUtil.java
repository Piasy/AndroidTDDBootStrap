package com.github.piasy.common.utils;

import java.util.regex.Pattern;

public class EmailUtil {

    private final Pattern mEmailPattern;

    public EmailUtil(Pattern emailPattern) {
        mEmailPattern = emailPattern;
    }

    public boolean isValidEmail(String email) {
        return email != null && email.length() > 0 &&
                mEmailPattern.matcher(email).find();
    }
}
