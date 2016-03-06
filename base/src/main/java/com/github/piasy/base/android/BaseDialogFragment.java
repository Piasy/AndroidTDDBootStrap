/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Piasy
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

package com.github.piasy.base.android;

import android.app.Dialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import butterknife.ButterKnife;
import com.github.piasy.base.utils.RxUtil;
import com.hannesdorfmann.fragmentargs.FragmentArgs;
import com.jakewharton.rxbinding.view.RxView;
import java.util.concurrent.TimeUnit;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by piasy on 15/5/4.
 */
public abstract class BaseDialogFragment extends DialogFragment {

    private static final float DEFAULT_DIM_AMOUNT = 0.2F;

    private static final int WINDOW_DURATION = 1;

    private CompositeSubscription mCompositeSubscription;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (hasArgs()) {
            FragmentArgs.inject(this);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        return new Dialog(getActivity(), getTheme()) {
            @Override
            public void onBackPressed() {
                if (isCanceledOnBackPressed()) {
                    super.onBackPressed();
                }
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        // Less dimmed background; see http://stackoverflow.com/q/13822842/56285
        final Window window = getDialog().getWindow();
        final WindowManager.LayoutParams params = window.getAttributes();
        params.dimAmount = getDimAmount(); // dim only a little bit
        window.setAttributes(params);

        window.setLayout(getWidth(), getHeight());
        window.setGravity(getGravity());

        // Transparent background; see http://stackoverflow.com/q/15007272/56285
        // (Needed to make dialog's alpha shadow look good)
        window.setBackgroundDrawableResource(android.R.color.transparent);

        final Resources res = getResources();
        final int titleDividerId = res.getIdentifier("titleDivider", "id", "android");
        if (titleDividerId > 0) {
            final View titleDivider = getDialog().findViewById(titleDividerId);
            if (titleDivider != null) {
                titleDivider.setBackgroundColor(res.getColor(android.R.color.transparent));
            }
        }
    }

    public void onDestroyView() {
        super.onDestroyView();
        unbindView();
        unSubscribeAll();
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container,
            @Nullable final Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(isCanceledOnTouchOutside());
        return inflater.inflate(getLayoutRes(), container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindView(view);
    }

    protected void addSubscribe(final Subscription subscription) {
        if (mCompositeSubscription == null || mCompositeSubscription.isUnsubscribed()) {
            // recreate mCompositeSubscription
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }

    protected void listenOnClickRxy(final View view, final Action1<Void> action) {
        addSubscribe(RxView.clicks(view)
                .throttleFirst(WINDOW_DURATION, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(action, RxUtil.OnErrorLogger));
    }

    protected void unSubscribeAll() {
        if (mCompositeSubscription != null && !mCompositeSubscription.isUnsubscribed()) {
            mCompositeSubscription.unsubscribe();
        }
    }

    @LayoutRes
    protected abstract int getLayoutRes();

    protected float getDimAmount() {
        return DEFAULT_DIM_AMOUNT;
    }

    protected abstract int getWidth();

    protected abstract int getHeight();

    protected int getGravity() {
        return Gravity.CENTER;
    }

    protected boolean isCanceledOnTouchOutside() {
        return true;
    }

    protected boolean isCanceledOnBackPressed() {
        return true;
    }

    /**
     * When use FragmentArgs to inject arguments, should override this and return {@code true}.
     */
    protected boolean hasArgs() {
        return false;
    }

    /**
     * When use ButterKnife to auto bind views, should override this and return {@code true}.
     * If not, should override {@link #bindView(View)} and {@link #unbindView()} to do it manually.
     */
    protected boolean autoBindViews() {
        return false;
    }

    /**
     * bind views, should override this method when bind view manually.
     */
    protected void bindView(final View rootView) {
        if (autoBindViews()) {
            ButterKnife.bind(this, rootView);
        }
    }

    /**
     * unbind views, should override this method when unbind view manually.
     */
    protected void unbindView() {
        if (autoBindViews()) {
            ButterKnife.unbind(this);
        }
    }
}
