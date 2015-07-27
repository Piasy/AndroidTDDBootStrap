package com.piasy.common.android.utils.net;


import rx.functions.Action1;

/**
 * Created by Piasy{github.com/Piasy} on 15/7/24.
 */
public class RxUtil {

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
    public static Action1<Throwable> NetErrorProcessor = new Action1<Throwable>() {
        @Override
        public void call(Throwable throwable) {
            // intercept error process
        }
    };

}
