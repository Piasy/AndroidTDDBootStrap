/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Piasy
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

package com.github.piasy.octostars.bridge;

import com.github.piasy.bootstrap.base.model.provider.ProviderModuleExposure;
import com.github.piasy.bootstrap.testbase.TestUtil;
import com.github.piasy.bootstrap.testbase.rules.ThreeTenBPRule;
import com.google.gson.TypeAdapterFactory;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import rx.functions.Action1;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;

/**
 * Created by Piasy{github.com/Piasy} on 5/5/16.
 */
public class RxNetErrorProcessorTest {

    @Rule
    public ThreeTenBPRule mThreeTenBPRule = ThreeTenBPRule.junitTest();
    @Rule
    public MockitoRule mMockitoRule = MockitoJUnit.rule();

    @Mock
    private Action1<ApiError> mApiErrorHandler;

    private TypeAdapterFactory mAdapterFactory = ErrorGsonAdapterFactory.create();

    @Test
    public void testProcessOtherException() {
        RxNetErrorProcessor rxNetErrorProcessor =
                new RxNetErrorProcessor(ProviderModuleExposure.exposeGson(mAdapterFactory));
        Throwable throwable = new IllegalArgumentException();
        assertFalse(rxNetErrorProcessor.tryWithApiError(throwable, mApiErrorHandler));
        verify(mApiErrorHandler, never()).call(any());
    }

    @Test
    public void testProcessNonApiNetworkError() {
        RxNetErrorProcessor rxNetErrorProcessor =
                new RxNetErrorProcessor(ProviderModuleExposure.exposeGson(mAdapterFactory));
        assertFalse(
                rxNetErrorProcessor.tryWithApiError(TestUtil.nonApiError(), mApiErrorHandler));
        verify(mApiErrorHandler, never()).call(any());
    }

    @Test
    public void testProcessInvalidApiError() {
        RxNetErrorProcessor rxNetErrorProcessor =
                new RxNetErrorProcessor(ProviderModuleExposure.exposeGson(mAdapterFactory));
        assertFalse(rxNetErrorProcessor.tryWithApiError(TestUtil.invalidApiError(),
                mApiErrorHandler));
        verify(mApiErrorHandler, never()).call(any());
    }

    @Test
    public void testProcessApiError() {
        RxNetErrorProcessor rxNetErrorProcessor =
                new RxNetErrorProcessor(ProviderModuleExposure.exposeGson(mAdapterFactory));
        assertTrue(rxNetErrorProcessor.tryWithApiError(TestUtil.apiError(), mApiErrorHandler));
        ApiError apiError = ProviderModuleExposure.exposeGson(mAdapterFactory)
                .fromJson(provideGithubAPIErrorStr(), ApiError.class);
        verify(mApiErrorHandler, only()).call(apiError);
    }

    private String provideGithubAPIErrorStr() {
        return "{\"message\":\"Validation Failed\",\"errors\":[{\"resource\":\"Issue\"," +
               "\"field\":\"title\",\"code\":\"missing_field\"}]}";
    }
}
