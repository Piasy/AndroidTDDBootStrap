package com.piasy.common.android.utils.net;


import com.piasy.model.entities.GithubAPIError;

import rx.functions.Action1;

/**
 * Created by Piasy{github.com/Piasy} on 15/7/24.
 */

/**
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

    public RxUtil(GithubAPIErrorProcessor githubAPIErrorProcessor) {
        mRxErrorProcessor = new RxErrorProcessor(githubAPIErrorProcessor);
    }

    public RxErrorProcessor getRxErrorProcessor() {
        return mRxErrorProcessor;
    }

    public static class RxErrorProcessor implements Action1<Throwable> {

        private final GithubAPIErrorProcessor mAPIErrorProcessor;

        private RxErrorProcessor(GithubAPIErrorProcessor apiErrorProcessor) {
            mAPIErrorProcessor = apiErrorProcessor;
        }

        @Override
        public void call(Throwable throwable) {
            // intercept error process
            if (throwable != null && throwable instanceof GithubAPIError) {
                mAPIErrorProcessor.process((GithubAPIError) throwable);
            } else {
                // ignored
            }
        }
    }

}
