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

package com.github.piasy.common.utils;

import java.util.regex.Pattern;

/**
 * Created by Piasy{github.com/Piasy} on 15/8/9.
 *
 * Email utility class.
 */
public class EmailUtil {

    private final Pattern mEmailPattern;

    /**
     * Create an {@link EmailUtil} instance with the given email re {@link Pattern}.
     *
     * @param emailPattern the email re {@link Pattern} to use in this instance.
     */
    public EmailUtil(final Pattern emailPattern) {
        mEmailPattern = emailPattern;
    }

    /**
     * Validate the given email string.
     *
     * @param email the email string to be validated.
     * @return {@code true} if the given string is a valid email, {@code false} if invalid.
     */
    public boolean isValidEmail(final String email) {
        return email != null && email.length() > 0 &&
                mEmailPattern.matcher(email).find();
    }
}
