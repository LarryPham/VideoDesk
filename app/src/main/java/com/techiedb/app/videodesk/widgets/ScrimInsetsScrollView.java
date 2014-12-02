package com.techiedb.app.videodesk.widgets;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.WindowInsets;
import android.widget.ScrollView;

import com.techiedb.app.videodesk.R;

/**
 * Copyright (C) 2014 Adways Vietnam Inc . All rights reserved.
 * Mobile UX Promotion Division.
 * This software and its documentation are confidential and proprietary
 * information of Adways Vietnam Inc .  No part of the software and
 * documents may be copied, reproduced, transmitted, translated, or reduced to
 * any electronic medium or machine-readable form without the prior written
 * consent of Adways Vietnam Inc.
 * Adways Vietnam Inc makes no representations with respect to the contents,
 * and assumes no responsibility for any errors that might appear in the
 * software and documents. This publication and the contents hereof are subject
 * to change without notice.
 * History
 *
 * @author Larry Pham(larrypham.vn@gmail.com)
 * @since December.01.2014
 * A Layout that draws something in the insets passed to {@link #fitSystemWindows(android.graphics.Rect)}, i.e the area above UI chrome
 * ( status and navigations bars, overlay actions bars).
 */
public class ScrimInsetsScrollView extends ScrollView {
    private Drawable mInsetForeground;
    private Rect mInsets;
    private Rect mTempRect = new Rect();
    private OnInsetsCallback mOnInsetCallback;

    public ScrimInsetsScrollView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public ScrimInsetsScrollView(Context context, AttributeSet attrs){
        super(context, attrs);
        init(context, attrs,0);
    }

    public ScrimInsetsScrollView(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    public Drawable getInsetForeground(){
        return this.mInsetForeground;
    }

    public void setInsetForeground(Drawable insetForeground){
        this.mInsetForeground = insetForeground;
    }

    private void init(Context context, AttributeSet attrs, int defStyle){
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ScrimInsetsView, defStyle, 0);
        if (a == null){
            return;
        }
        mInsetForeground = a.getDrawable(R.styleable.ScrimInsetsView_insetForeground);
        a.recycle();
        setWillNotDraw(true);
    }

    @Override
    protected boolean fitSystemWindows(Rect insets) {
        mInsets = new Rect(insets);
        setWillNotDraw(mInsetForeground == null);
        //ViewCompat.postInvalidateOnAnimation(this);
        if (mOnInsetCallback != null){
            mOnInsetCallback.onInsetsChanged(insets);
        }
        return true;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT_WATCH)
    @Override
    public WindowInsets onApplyWindowInsets(WindowInsets insets) {
        insets = super.onApplyWindowInsets(insets);
        handleWindowInsets(new Rect(insets.getSystemWindowInsetLeft(),
                insets.getSystemWindowInsetTop(),
                insets.getSystemWindowInsetRight(),
                insets.getSystemWindowInsetBottom()));
        return insets;
    }

    public void handleWindowInsets(Rect insets){
        this.mInsets = new Rect(insets);
        setWillNotDraw(mInsetForeground == null);
        if (mOnInsetCallback != null){
            mOnInsetCallback.onInsetsChanged(new Rect(mInsets));
        }
    }
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        int width = getWidth();
        int height = getHeight();
        if (mInsets != null && mInsetForeground != null){
            int sc = canvas.save();
            canvas.translate(getScrollX(), getScrollY());

            // Top
            mTempRect.set(0, 0, width, mInsets.top);
            mInsetForeground.setBounds(mTempRect);
            mInsetForeground.draw(canvas);

            // Bottom
            mTempRect.set(0, height - mInsets.bottom, width, height);
            mInsetForeground.setBounds(mTempRect);
            mInsetForeground.draw(canvas);
            // Left
            mTempRect.set(0, mInsets.top, mInsets.left, height - mInsets.bottom);
            mInsetForeground.setBounds(mTempRect);
            mInsetForeground.draw(canvas);
            //Right
            mTempRect.set(width - mInsets.right, mInsets.top, width, height - mInsets.bottom);
            mInsetForeground.setBounds(mTempRect);
            mInsetForeground.draw(canvas);

            canvas.restoreToCount(sc);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mInsetForeground != null){
            mInsetForeground.setCallback(this);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mInsetForeground != null){
            mInsetForeground.setCallback(null);
        }
    }

    /**
     * Allows the calling container to specify a callback for custom processing when insets change (i.e when
     * {@link #fitSystemWindows(android.graphics.Rect)} is called. This is useful for setting padding on UI
     * elements based on UI chrome insets (i.e Google Map or a ListView). When using with ListView or GridView,
     * remember to set clipToPadding to false.
     * @param callback
     */
    public void setInsetsCallback(OnInsetsCallback callback){
        this.mOnInsetCallback = callback;
    }

    public static interface OnInsetsCallback{
        public void onInsetsChanged(Rect insets);
    }
}
