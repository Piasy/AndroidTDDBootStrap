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

package com.github.piasy.common.android.utils.net;

import com.github.piasy.common.android.MockProvider;
import com.github.piasy.common.android.utils.model.ThreeTenABPDelegate;
import com.github.piasy.common.android.utils.provider.GsonProvider;
import com.github.piasy.common.android.utils.ui.ToastUtil;
import com.google.gson.Gson;
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
    private Gson mGson;

    @Before
    public void setUp() {
        mToastUtil = mock(ToastUtil.class);
        mGithubAPIErrorProcessor = new GithubAPIErrorProcessor(mToastUtil);
        mGson = GsonProvider.provideGson(mock(ThreeTenABPDelegate.class));
    }

    @Test
    public void testProcess() {
        // given
        final GithubAPIError error =
                mGson.fromJson(MockProvider.provideGithubAPIErrorStr(), GithubAPIError.class);

        // when
        mGithubAPIErrorProcessor.process(error);

        // then
        then(mToastUtil).should(only()).makeToast(error.getErrorMessage());
    }
}
