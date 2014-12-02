package com.techiedb.app.videodesk.widgets;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Outline;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.AbsListView;
import android.widget.ImageButton;

import com.nineoldandroids.view.ViewHelper;
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
public class FloatingActionButton extends ImageButton {
    private static final int TRANSLATE_DURATION_MILLIS = 200;
    /**
     * Define the kinds of FloatingActionButton that was included: TYPE_NORMAL, TYPE_MINI
     */
    @IntDef({TYPE_NORMAL, TYPE_MINI})
    public @interface TYPE{

    }
    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_MINI = 1;

    private boolean mVisible;
    private int mNormalColor;
    private int mPressedColor;
    private int mRippleColor;

    private boolean mShadow;
    private int mType;

    private int mShadowSize;
    private int mScrollThreshold;
    private boolean mMarginSet;

    private final Interpolator mInterpolator = new AccelerateDecelerateInterpolator();

    public FloatingActionButton(Context context) {
        this(context, null);
    }

    public FloatingActionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public FloatingActionButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int size = getDimension(mType == TYPE_NORMAL ? R.dimen.fab_size_normal : R.dimen.fab_size_mini);
        if (mShadow && !hasLollipopApi()){
            size += mShadowSize * 2;
        }
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (!hasLollipopApi() && !mMarginSet){
            if (getLayoutParams() instanceof ViewGroup.MarginLayoutParams){
                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
                int leftMargin = layoutParams.leftMargin - mShadowSize;
                int topMargin = layoutParams.topMargin - mShadowSize;
                int rightMargin = layoutParams.rightMargin - mShadowSize;
                int bottomMargin = layoutParams.bottomMargin - mShadowSize;
                layoutParams.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);

                setLayoutParams(layoutParams);
                mMarginSet = true;
            }
        }
    }

    private void init(Context context, AttributeSet attrs){
        this.mVisible = true;
        this.mNormalColor = getColor(R.color.material_blue_500);
        this.mPressedColor = getColor(R.color.material_blue_600);
        this.mRippleColor =  getColor(android.R.color.white);
        this.mType = TYPE_NORMAL;
        this.mShadow = true;
        this.mScrollThreshold = getResources().getDimensionPixelOffset(R.dimen.fab_scroll_threshold);
        this.mShadowSize = getDimension(R.dimen.fab_shadow_size);
        if (attrs != null){
            initAttributes(context, attrs);
        }
        updateBackground();
    }

    private void initAttributes(Context context, AttributeSet attrs){
        TypedArray attr = getTypedArray(context, attrs, R.styleable.FloatingActionButton);
        if (attr != null){
            try {
                mNormalColor  = attr.getColor(R.styleable.FloatingActionButton_fab_colorNormal, getColor(R.color.material_blue_500));
                mPressedColor = attr.getColor(R.styleable.FloatingActionButton_fab_colorPressed, getColor(R.color.material_blue_600));
                mRippleColor = attr.getColor(R.styleable.FloatingActionButton_fab_colorRipple, getColor(android.R.color.white));
                mShadow = attr.getBoolean(R.styleable.FloatingActionButton_fab_shadow, true);
                mType = attr.getInt(R.styleable.FloatingActionButton_fab_type, TYPE_NORMAL);
            }finally {
                attr.recycle();
            }
        }
    }

    private int getColor(@ColorRes int id){
        return getResources().getColor(id);
    }

    private int getDimension(@DimenRes int id){
        return getResources().getDimensionPixelSize(id);
    }

    private void updateBackground(){
        StateListDrawable drawable = new StateListDrawable();
        drawable.addState(new int[]{android.R.attr.state_pressed}, createDrawable(mPressedColor));
        drawable.addState(new int[]{}, createDrawable(mNormalColor));
        setBackgroundCompat(drawable);
    }

    private Drawable createDrawable(int color){
        OvalShape ovalShape = new OvalShape();
        ShapeDrawable shapeDrawable = new ShapeDrawable(ovalShape);
        shapeDrawable.getPaint().setColor(color);

        if (mShadow && !hasLollipopApi()){
            Drawable shadowDrawable = getResources().getDrawable(mType == TYPE_NORMAL ? R.drawable.shadow : R.drawable.shadow_mini);
            LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{shadowDrawable, shapeDrawable});
            layerDrawable.setLayerInset(1, mShadowSize, mShadowSize, mShadowSize, mShadowSize);
            return layerDrawable;
        } else {
            return shapeDrawable;
        }
    }

    private void setBackgroundCompat(Drawable drawable){
        if (hasLollipopApi()){
            setElevation(mShadow ?  getDimension(R.dimen.fab_elevation_lollipop) : 0.0f);
            RippleDrawable rippleDrawable = new RippleDrawable(new ColorStateList(new int[][]{{}},
                    new int[]{mRippleColor}), drawable, null);
            setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    int size = getDimension(mType == TYPE_NORMAL ? R.dimen.fab_size_normal : R.dimen.fab_size_mini);
                    outline.setOval(0, 0, size, size);
                }
            });
            setClipToOutline(true);
            setBackground(rippleDrawable);
        }else if (hasJellyBeanApi()){
            setBackground(drawable);
        }else{
            setBackgroundDrawable(drawable);
        }
    }

    private int getMarginBottom(){
        int marginBottom = 0;
        final ViewGroup.LayoutParams layoutParams = getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams){
            marginBottom = ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
        }
        return marginBottom;
    }

    private void setNormalColor(int color){
        if (color != mNormalColor){
            mNormalColor = color;
            updateBackground();
        }
    }

    public void setNormalColorResId(@ColorRes int colorResId){
        setNormalColor(getColor(colorResId));
    }

    public int getNormalColor(){
        return this.mNormalColor;
    }

    public void setPressedColor(int color){
        if (color != mPressedColor){
            mPressedColor = color;
            updateBackground();
        }
    }

    public void setPressedColorResId(@ColorRes int colorResId){
        setPressedColor(getColor(colorResId));
    }

    public int getPressedColor(){
        return this.mPressedColor;
    }

    public void setRippleColor(int color){
        if (color != mRippleColor){
            mRippleColor = color;
            updateBackground();
        }
    }

    public void setShadow(boolean shadow){
        if (shadow != mShadow){
            mShadow = shadow;
            updateBackground();
        }
    }

    public boolean hasShadow(){
        return this.mShadow;
    }

    public void setType(@TYPE int type){
        if (type != mType){
            mType = type;
            updateBackground();
        }
    }

    @TYPE
    public int getType(){
        return this.mType;
    }

    public boolean isVisible(){
        return this.mVisible;
    }

    public void show(){
        show(true);
    }

    public void hide(){
        hide(true);
    }

    public void show(boolean animate){
        toggle(true, animate, false);
    }

    public void hide(boolean animate){
        toggle(false, animate, false);
    }

    private void toggle(final boolean visible, final boolean animate, boolean force){
        if (mVisible != visible || force){
            mVisible = visible;
            int height = getHeight();
            if (height == 0 && !force){
                final ViewTreeObserver observer = getViewTreeObserver();
                if (observer.isAlive()){
                    observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener(){
                        @Override
                        public boolean onPreDraw() {
                            ViewTreeObserver currentObserver = getViewTreeObserver();
                            if (observer.isAlive()){
                                currentObserver.removeOnPreDrawListener(this);
                            }
                            toggle(visible, animate, true);
                            return true;
                        }
                    });
                    return;
                }
            }
            int translationY = visible ? 0 : height + getMarginBottom();
            if (animate){
                com.nineoldandroids.view.ViewPropertyAnimator.animate(this)
                                                             .setInterpolator(mInterpolator)
                                                             .setDuration(TRANSLATE_DURATION_MILLIS)
                                                             .translationY(translationY);
            }else{
                ViewHelper.setTranslationY(this, translationY);
            }
        }
    }

    public void attachToListView(@NonNull AbsListView listView){
        attachToListView(listView, null);
    }

    public void attachToRecyclerView(@NonNull ObservableScrollView scrollView){
        attacheToScrollView(scrollView, null);
    }

    public void attachToRecyclerView(@NonNull RecyclerView recyclerView){
        attachToRecyclerView(recyclerView, null);
    }

    public void attachToListView(@NonNull AbsListView listView, ScrollDirectionListener listener){
        AbsListViewScrollDetectorImpl scrollDetector = new AbsListViewScrollDetectorImpl();
        scrollDetector.setListener(listener);
        scrollDetector.setListView(listView);
        scrollDetector.setScrollThreshold(mScrollThreshold);
        listView.setOnScrollListener(scrollDetector);
    }

    public void attacheToScrollView(@NonNull ObservableScrollView scrollView, ScrollDirectionListener listener){
        ScrollViewDetectorImpl scrollDetector = new ScrollViewDetectorImpl();
        scrollDetector.setListener(listener);
        scrollDetector.setScrollThreshold(mScrollThreshold);
        scrollView.setOnScrollChangedListener(scrollDetector);
    }

    public void attachToRecyclerView(@NonNull RecyclerView recyclerView, ScrollDirectionListener listener){
        RecyclerViewScrollDetectorImpl scrollDetector = new RecyclerViewScrollDetectorImpl();
        scrollDetector.setListener(listener);
        scrollDetector.setScrollThreshold(mScrollThreshold);
        recyclerView.setOnScrollListener(scrollDetector);
    }

    public void setRippleColorResId(@ColorRes int colorResId){
        setRippleColor(getColor(colorResId));
    }


    private boolean hasLollipopApi(){
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    private boolean hasJellyBeanApi(){
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }

    private TypedArray getTypedArray(Context context, AttributeSet attrs, int[] attr){
        return context.obtainStyledAttributes(attrs,attr, 0, 0);
    }

    private class AbsListViewScrollDetectorImpl extends AbsListViewScrollDetector{
        private ScrollDirectionListener mListener;

        public void setListener(ScrollDirectionListener listener){
            this.mListener = listener;
        }

        @Override
        void onScrollUp() {
            show();
            if (mListener != null){
                mListener.onScrollDown();
            }
        }

        @Override
        void onScrollDown() {
            hide();
            if (mListener != null){
                mListener.onScrollUp();
            }
        }
    }

    private class RecyclerViewScrollDetectorImpl extends RecyclerViewScrollDetector{
        private ScrollDirectionListener mListener;

        public void setListener(ScrollDirectionListener listener){
            this.mListener = listener;
        }

        @Override
        void onScrollUp() {
            show();
            if (mListener != null){
                mListener.onScrollDown();
            }
        }

        @Override
        void onScrollDown() {
            hide();
            if (mListener != null){
                mListener.onScrollUp();
            }
        }
    }

    private class ScrollViewDetectorImpl extends ScrollViewScrollDetector{
        private ScrollDirectionListener mListener;

        private void setListener(ScrollDirectionListener listener){
            this.mListener = listener;
        }

        @Override
        void onScrollUp() {
            show();
            if (mListener != null){
                mListener.onScrollDown();
            }
        }

        @Override
        void onScrollDown() {
            hide();
            if (mListener != null){
                mListener.onScrollUp();
            }
        }
    }
}
