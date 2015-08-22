package com.github.piasy.common.android.utils.net;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.only;

/**
 * Created by Piasy{github.com/Piasy} on 15/8/9.
 */
public class RxUtilTest {

    private RxUtil mRxUtil;
    private GithubAPIErrorProcessor mGithubAPIErrorProcessor;

    @Before
    public void setUp() {
        mGithubAPIErrorProcessor = mock(GithubAPIErrorProcessor.class);
        mRxUtil = new RxUtil(mGithubAPIErrorProcessor);
    }

    @Test
    public void testRxErrorProcessorCallThrowable() {
        // given
        Throwable throwable = new Throwable();

        // when
        mRxUtil.getRxErrorProcessor().call(throwable);

        // then
        then(mGithubAPIErrorProcessor).shouldHaveZeroInteractions();
    }

    @Test
    public void testRxErrorProcessorCallAPIError() {
        // given
        GithubAPIError githubAPIError = new GithubAPIError(new Throwable());

        // when
        mRxUtil.getRxErrorProcessor().call(githubAPIError);

        // then
        then(mGithubAPIErrorProcessor).should(only()).process(githubAPIError);
    }
}
