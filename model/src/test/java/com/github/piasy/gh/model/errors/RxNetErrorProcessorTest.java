package com.github.piasy.gh.model.errors;

import com.github.piasy.base.model.provider.GsonProviderExposure;
import com.github.piasy.test.TestUtil;
import com.github.piasy.test.mock.MockProvider;
import com.github.piasy.test.rules.ThreeTenBPRule;
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

    @Test
    public void testProcessOtherException() {
        RxNetErrorProcessor rxNetErrorProcessor =
                new RxNetErrorProcessor(GsonProviderExposure.exposeGson());
        Throwable throwable = new IllegalArgumentException();
        assertFalse(rxNetErrorProcessor.tryWithApiError(throwable, mApiErrorHandler));
        verify(mApiErrorHandler, never()).call(any());
    }

    @Test
    public void testProcessNonApiNetworkError() {
        RxNetErrorProcessor rxNetErrorProcessor =
                new RxNetErrorProcessor(GsonProviderExposure.exposeGson());
        assertFalse(
                rxNetErrorProcessor.tryWithApiError(TestUtil.nonApiError(), mApiErrorHandler));
        verify(mApiErrorHandler, never()).call(any());
    }

    @Test
    public void testProcessInvalidApiError() {
        RxNetErrorProcessor rxNetErrorProcessor =
                new RxNetErrorProcessor(GsonProviderExposure.exposeGson());
        assertFalse(rxNetErrorProcessor.tryWithApiError(TestUtil.invalidApiError(),
                mApiErrorHandler));
        verify(mApiErrorHandler, never()).call(any());
    }

    @Test
    public void testProcessApiError() {
        RxNetErrorProcessor rxNetErrorProcessor =
                new RxNetErrorProcessor(GsonProviderExposure.exposeGson());
        assertTrue(rxNetErrorProcessor.tryWithApiError(TestUtil.apiError(), mApiErrorHandler));
        ApiError apiError = GsonProviderExposure.exposeGson()
                .fromJson(MockProvider.provideGithubAPIErrorStr(), ApiError.class);
        verify(mApiErrorHandler, only()).call(apiError);
    }
}
