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

import rx.functions.Action1;

/**
 * Created by Piasy{github.com/Piasy} on 15/7/24.
 *
 * used for Rx network error handling
 * Usage: Observable.subscribe(onNext, RxUtil.NetErrorProcessor)
 * run in the observeOn() thread
 * onErrorReturn run in subscribeOn thread (retrofit run in background thread, not good for
 * error handling)
 *
 * Note: if you handle onError for the net request, than you should call it manually:
 * RxUtil.NetErrorProcessor.call(throwable);
 * Otherwise this method won't be invoked
 */
public class RxUtil {

    private final RxErrorProcessor mRxErrorProcessor;

    /**
     * Create instance with the given {@link GithubAPIErrorProcessor}.
     *
     * @param githubAPIErrorProcessor the {@link GithubAPIErrorProcessor} to process API error.
     */
    public RxUtil(final GithubAPIErrorProcessor githubAPIErrorProcessor) {
        mRxErrorProcessor = new RxErrorProcessor(githubAPIErrorProcessor);
    }

    /**
     * Get {@link RxUtil.RxErrorProcessor}.
     *
     * @return the {@link RxUtil.RxErrorProcessor} instance.
     */
    public RxErrorProcessor getRxErrorProcessor() {
        return mRxErrorProcessor;
    }

    /**
     * Rx {@link Action1<Throwable>} impl class that process all Rx Error.
     */
    public static final class RxErrorProcessor implements Action1<Throwable> {

        private final GithubAPIErrorProcessor mAPIErrorProcessor;

        /**
         * Create instance with the given {@link GithubAPIErrorProcessor}. Only called by {@link
         * RxUtil}.
         *
         * @param apiErrorProcessor the given {@link GithubAPIErrorProcessor}.
         */
        RxErrorProcessor(final GithubAPIErrorProcessor apiErrorProcessor) {
            mAPIErrorProcessor = apiErrorProcessor;
        }

        @Override
        public void call(final Throwable throwable) {
            // intercept error process
            if (throwable instanceof GithubAPIError) {
                mAPIErrorProcessor.process((GithubAPIError) throwable);
            } // else ignored
        }
    }
}
