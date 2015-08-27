package com.github.piasy.common.android.utils.ui;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.github.piasy.common.android.R;

/**
 * Created by Piasy{github.com/Piasy} on 15/8/27.
 */
public class CenterTitleSideButtonBar extends RelativeLayout {
    public CenterTitleSideButtonBar(@NonNull Context context) {
        this(context, null, 0);
    }

    public CenterTitleSideButtonBar(@NonNull Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private boolean mHasLeftButton = false;
    private boolean mHasRightButton = false;
    private boolean mHasTitle = true;
    private
    @DrawableRes
    int mLeftButtonSrc = 0;
    private
    @DrawableRes
    int mLeftButtonBg = 0;
    private
    @DrawableRes
    int mRightButtonSrc = 0;
    private
    @DrawableRes
    int mRightButtonBg = 0;
    private String mTitle = "";
    private
    @ColorInt
    int mTitleColor = 0;
    private int mTitleSize = 26;
    private int mTitleGravity = 0;
    private int mLayoutHeight = 44;

    public CenterTitleSideButtonBar(@NonNull Context context, AttributeSet attrs,
            int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getLayoutAttrs(context, attrs);
        initAttrs(context, attrs, defStyleAttr);
        initChild(context);
    }

    private void getLayoutAttrs(@NonNull Context context, AttributeSet attrs) {
        int[] systemAttrs = { android.R.attr.layout_height };
        TypedArray a = context.obtainStyledAttributes(attrs, systemAttrs);
        mLayoutHeight = a.getDimensionPixelSize(0, ViewGroup.LayoutParams.WRAP_CONTENT);
        a.recycle();
    }

    private void initAttrs(@NonNull Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.CenterTitleSideButtonBar, defStyleAttr,
                        0);
        mHasLeftButton = a.getBoolean(R.styleable.CenterTitleSideButtonBar_hasLeftButton, false);
        mLeftButtonSrc = a.getResourceId(R.styleable.CenterTitleSideButtonBar_leftButtonSrc, 0);
        mLeftButtonBg = a.getResourceId(R.styleable.CenterTitleSideButtonBar_leftButtonBg, 0);

        mHasRightButton = a.getBoolean(R.styleable.CenterTitleSideButtonBar_hasRightButton, false);
        mRightButtonSrc = a.getResourceId(R.styleable.CenterTitleSideButtonBar_leftButtonSrc, 0);
        mRightButtonBg = a.getResourceId(R.styleable.CenterTitleSideButtonBar_leftButtonBg, 0);

        mHasTitle = a.getBoolean(R.styleable.CenterTitleSideButtonBar_hasTitle, true);
        mTitle = a.getString(R.styleable.CenterTitleSideButtonBar_centerTitle);
        if (TextUtils.isEmpty(mTitle)) {
            mTitle = getAppLabel(context);
        }
        mTitleColor =
                a.getColor(R.styleable.CenterTitleSideButtonBar_centerTitleTextColor, 0x333333);
        mTitleSize =
                a.getDimensionPixelSize(R.styleable.CenterTitleSideButtonBar_centerTitleTextSize,
                        26);
        mTitleGravity =
                a.getInteger(R.styleable.CenterTitleSideButtonBar_centerTitleTextGravity, 0);
        a.recycle();
    }

    private ImageButton mLeftButton = null;
    private ImageButton mRightButton = null;
    private TextView mTitleTextView = null;

    private void initChild(Context context) {
        if (mHasLeftButton && (mLeftButtonBg != 0 || mLeftButtonSrc != 0)) {
            mLeftButton = new ImageButton(context);
            RelativeLayout.LayoutParams params;
            if (mLayoutHeight != ViewGroup.LayoutParams.WRAP_CONTENT) {
                params = new LayoutParams(mLayoutHeight, mLayoutHeight);
            } else {
                params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
            params.addRule(CENTER_VERTICAL);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                params.addRule(ALIGN_PARENT_START);
            } else {
                params.addRule(ALIGN_PARENT_LEFT);
            }
            mLeftButton.setLayoutParams(params);
            if (mLeftButtonBg != 0) {
                mLeftButton.setBackgroundResource(mLeftButtonBg);
            }
            if (mLeftButtonSrc != 0) {
                mLeftButton.setImageResource(mLeftButtonSrc);
            }
            addView(mLeftButton);
        }

        if (mHasTitle) {
            mTitleTextView = new TextView(context);
            RelativeLayout.LayoutParams params =
                    new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT);
            if (mLeftButton != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    params.addRule(END_OF, mLeftButton.getId());
                } else {
                    params.addRule(RIGHT_OF, mLeftButton.getId());
                }
            }
            if (mRightButton != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    params.addRule(START_OF, mRightButton.getId());
                } else {
                    params.addRule(LEFT_OF, mRightButton.getId());
                }
            }
            mTitleTextView.setLayoutParams(params);
            mTitleTextView.setText(mTitle);
            mTitleTextView.setTextSize(mTitleSize);
            mTitleTextView.setTextColor(mTitleColor);
            switch (mTitleGravity) {
                case 1:
                    // left
                    mTitleTextView.setGravity(
                            (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH
                                    ? Gravity.START : Gravity.LEFT) | Gravity.CENTER_VERTICAL);
                    break;
                case 2:
                    // right
                    mTitleTextView.setGravity(
                            (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH
                                    ? Gravity.END : Gravity.RIGHT) | Gravity.CENTER_VERTICAL);
                    break;
                case 0:
                    // center
                default:
                    mTitleTextView.setGravity(Gravity.CENTER);
                    break;
            }
            addView(mTitleTextView);
        }

        if (mHasRightButton && (mRightButtonBg != 0 || mRightButtonSrc != 0)) {
            mRightButton = new ImageButton(context);
            RelativeLayout.LayoutParams params;
            if (mLayoutHeight != ViewGroup.LayoutParams.WRAP_CONTENT) {
                params = new LayoutParams(mLayoutHeight, mLayoutHeight);
            } else {
                params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
            params.addRule(CENTER_VERTICAL);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                params.addRule(ALIGN_PARENT_END);
            } else {
                params.addRule(ALIGN_PARENT_RIGHT);
            }
            mRightButton.setLayoutParams(params);
            if (mRightButtonBg != 0) {
                mRightButton.setBackgroundResource(mRightButtonBg);
            }
            if (mRightButtonSrc != 0) {
                mRightButton.setImageResource(mRightButtonSrc);
            }
            addView(mRightButton);
        }
    }

    @Override
    public boolean isInEditMode() {
        return true;
    }

    private String getAppLabel(@NonNull Context context) {
        PackageManager packageManager = context.getPackageManager();
        ApplicationInfo lApplicationInfo = null;
        try {
            lApplicationInfo =
                    packageManager.getApplicationInfo(context.getApplicationInfo().packageName, 0);
        } catch (final PackageManager.NameNotFoundException e) {
            // return "App Title"
        }
        return (String) (lApplicationInfo != null ? packageManager.getApplicationLabel(
                lApplicationInfo) : "App Title");
    }
}
