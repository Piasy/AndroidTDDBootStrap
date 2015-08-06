package com.piasy.common.utils;

import java.util.regex.Pattern;

public class EmailUtil {

    private static final Pattern mEmailPattern = Pattern
            .compile("^[_A-Za-z0-9-\\+]+@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

    public static boolean isValidEmail(String email) {
        return email != null && email.length() > 0 &&
                mEmailPattern.matcher(email).find();
    }

}
