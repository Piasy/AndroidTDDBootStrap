package com.piasy.common.android.utils.net;

import com.piasy.common.android.utils.ui.ToastUtil;
import com.piasy.model.entities.GithubAPIError;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Created by Piasy{github.com/Piasy} on 15/8/9.
 */
public class GithubAPIErrorProcessorTest {

    private ToastUtil mToastUtil;
    private GithubAPIErrorProcessor mGithubAPIErrorProcessor;

    @Before
    public void setUp() {
        mToastUtil = Mockito.mock(ToastUtil.class);
        mGithubAPIErrorProcessor = new GithubAPIErrorProcessor(mToastUtil);
    }

    @Test
    public void testProcess() {
        GithubAPIError error = new GithubAPIError(new Throwable());
        String errorMessage = "Test Fake Error!";
        error.setMessage(errorMessage);
        mGithubAPIErrorProcessor.process(error);
        Mockito.verify(mToastUtil, Mockito.only()).makeToast(errorMessage);
    }

}
