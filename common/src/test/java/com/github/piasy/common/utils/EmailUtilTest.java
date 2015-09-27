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

import com.github.piasy.common.Constants;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Piasy{github.com/Piasy} on 15/8/9.
 */
public class EmailUtilTest {

    private EmailUtil mEmailUtil;

    @Before
    public void setUp() {
        mEmailUtil = new EmailUtil(Constants.RE_PATTERNS_EMAIL_PATTERN);
    }

    @Test
    public void testValidEmail() {
        final String email1 = "xz4215@gmail.com";
        Assert.assertThat(email1, mEmailUtil.isValidEmail(email1), Is.is(true));

        final String email2 = "xjl11@tsinghua.edu.cn";
        Assert.assertThat(email2, mEmailUtil.isValidEmail(email2), Is.is(true));

        final String email3 = "i@piasy.io";
        Assert.assertThat(email3, mEmailUtil.isValidEmail(email3), Is.is(true));
    }

    @Test
    public void testInvalidEmail() {
        final String email1 = "xz4215.gmail.com";
        Assert.assertThat(email1, mEmailUtil.isValidEmail(email1), Is.is(false));

        final String email2 = "xjl11@tsinghua";
        Assert.assertThat(email2, mEmailUtil.isValidEmail(email2), Is.is(false));

        final String email3 = "@piasy.io";
        Assert.assertThat(email3, mEmailUtil.isValidEmail(email3), Is.is(false));

        final String email4 = "i@piasy.io.1234";
        Assert.assertThat(email4, mEmailUtil.isValidEmail(email4), Is.is(false));
    }
}
