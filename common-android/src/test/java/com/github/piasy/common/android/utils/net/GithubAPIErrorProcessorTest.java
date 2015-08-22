package com.github.piasy.common.android.utils.net;

import com.github.piasy.common.android.utils.ui.ToastUtil;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.only;

/**
 * Created by Piasy{github.com/Piasy} on 15/8/9.
 */
public class GithubAPIErrorProcessorTest {

    private ToastUtil mToastUtil;
    private GithubAPIErrorProcessor mGithubAPIErrorProcessor;

    @Before
    public void setUp() {
        mToastUtil = mock(ToastUtil.class);
        mGithubAPIErrorProcessor = new GithubAPIErrorProcessor(mToastUtil);
    }

    @Test
    public void testProcess() {
        // given
        GithubAPIError error = new GithubAPIError(new Throwable());
        String errorMessage = "Test Fake Error!";
        error.setMessage(errorMessage);

        // when
        mGithubAPIErrorProcessor.process(error);

        // then
        then(mToastUtil).should(only()).makeToast(errorMessage);
    }
}
