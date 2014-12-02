package com.techiedb.app.videodesk.widgets;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Property;
import android.widget.FrameLayout;

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
 */
public class DrawerShadowFrameLayout extends FrameLayout {
    private Drawable mShadowDrawable;
    private NinePatchDrawable mShadowNinePatchDrawable;
    private int mShadowTopOffset;
    private boolean mShadowVisible;
    private int mWidth, mHeight;
    private ObjectAnimator mAnimator;
    private float mAlpha = 1f;

    public DrawerShadowFrameLayout(Context context){
        this(context, null, 0);
    }

    public DrawerShadowFrameLayout(Context context, AttributeSet attrs){
        super(context, attrs, 0);
    }

    public DrawerShadowFrameLayout(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
        final TypedArray  a  = context.obtainStyledAttributes(attrs, R.styleable.DrawerShadowFrameLayout, 0, 0);
        mShadowDrawable= a.getDrawable(R.styleable.DrawerShadowFrameLayout_shadowDrawable);
        if (mShadowDrawable != null){
            mShadowDrawable.setCallback(this);
            if (mShadowDrawable instanceof  NinePatchDrawable){
                mShadowNinePatchDrawable = (NinePatchDrawable) mShadowDrawable;
            }
        }
        mShadowVisible = a.getBoolean(R.styleable.DrawerShadowFrameLayout_shadowVisible, true);
        setWillNotDraw(!mShadowVisible || mShadowDrawable == null);
        a.recycle();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mWidth = w;
        this.mHeight = h;
        updateShadowBounds();
    }

    private void updateShadowBounds(){
        if (mShadowDrawable != null){
            mShadowDrawable.setBounds(0, mShadowTopOffset, mWidth, mHeight);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (mShadowDrawable != null && mShadowVisible){
            if (mShadowNinePatchDrawable != null){
                mShadowNinePatchDrawable.getPaint().setAlpha((int)(255 * mAlpha));
            }
            mShadowDrawable.draw(canvas);
        }
    }

    public void setShadowTopOffset(int shadowTopOffset){
        this.mShadowTopOffset = shadowTopOffset;
        updateShadowBounds();
        ViewCompat.postInvalidateOnAnimation(this);
    }

    public void setShadowVisible(boolean shadowVisible, boolean animate){
        this.mShadowVisible = shadowVisible;
        if (mAnimator != null){
            mAnimator.cancel();
            mAnimator = null;
        }

        if (animate && mShadowDrawable != null){
            mAnimator = ObjectAnimator.ofFloat(this, SHADOW_ALPHA, shadowVisible ? 0f : 1f,
                    shadowVisible ? 1f : 0f);
            mAnimator.setDuration(1000);
            mAnimator.start();
        }

        ViewCompat.postInvalidateOnAnimation(this);
        setWillNotDraw(!mShadowVisible || mShadowDrawable == null);
    }

    private static Property<DrawerShadowFrameLayout, Float> SHADOW_ALPHA = new Property<DrawerShadowFrameLayout, Float>(Float.class,"shadowAlpha") {
        @Override
        public Float get(DrawerShadowFrameLayout object) {
            return object.mAlpha;
        }

        @Override
        public void set(DrawerShadowFrameLayout object, Float value) {
            object.mAlpha = value;
            ViewCompat.postInvalidateOnAnimation(object);
        }
    };
}
