package com.piasy.common.utils;

import com.piasy.common.Constants;

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
        mEmailUtil = new EmailUtil(Constants.REPatterns.EMAIL_PATTERN);
    }

    @Test
    public void testValidEmail() {
        String email1 = "xz4215@gmail.com";
        Assert.assertThat(email1, mEmailUtil.isValidEmail(email1), Is.is(true));

        String email2 = "xjl11@tsinghua.edu.cn";
        Assert.assertThat(email2, mEmailUtil.isValidEmail(email2), Is.is(true));

        String email3 = "i@piasy.io";
        Assert.assertThat(email3, mEmailUtil.isValidEmail(email3), Is.is(true));
    }

    @Test
    public void testInvalidEmail() {
        String email1 = "xz4215.gmail.com";
        Assert.assertThat(email1, mEmailUtil.isValidEmail(email1), Is.is(false));

        String email2 = "xjl11@tsinghua";
        Assert.assertThat(email2, mEmailUtil.isValidEmail(email2), Is.is(false));

        String email3 = "@piasy.io";
        Assert.assertThat(email3, mEmailUtil.isValidEmail(email3), Is.is(false));

        String email4 = "i@piasy.io.1234";
        Assert.assertThat(email4, mEmailUtil.isValidEmail(email4), Is.is(false));
    }

}
