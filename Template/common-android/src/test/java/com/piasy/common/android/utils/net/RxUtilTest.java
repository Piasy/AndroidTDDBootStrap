package com.piasy.common.android.utils.net;

import com.piasy.model.entities.GithubAPIError;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Created by Piasy{github.com/Piasy} on 15/8/9.
 */
public class RxUtilTest {

    private RxUtil mRxUtil;
    private GithubAPIErrorProcessor mGithubAPIErrorProcessor;

    @Before
    public void setUp() {
        mGithubAPIErrorProcessor = Mockito.mock(GithubAPIErrorProcessor.class);
        mRxUtil = new RxUtil(mGithubAPIErrorProcessor);
    }

    @Test
    public void testRxErrorProcessorCallThrowable() {
        Throwable throwable = new Throwable();
        mRxUtil.getRxErrorProcessor().call(throwable);
        Mockito.verify(mGithubAPIErrorProcessor, Mockito.never()).process(Mockito.any());
    }

    @Test
    public void testRxErrorProcessorCallAPIError() {
        Throwable throwable = new GithubAPIError(new Throwable());
        mRxUtil.getRxErrorProcessor().call(throwable);
        Mockito.verify(mGithubAPIErrorProcessor, Mockito.only()).process(Mockito.any());
    }

}
