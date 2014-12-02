package com.techiedb.app.videodesk.widgets.drawers;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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
public class IcsLinearLayout extends LinearLayout {
    private static final int[] R_styleable_LinearLayout = new int[]{
            android.R.attr.divider,
            android.R.attr.measureWithLargestChild,
            android.R.attr.showDividers,
            android.R.attr.dividerPadding
    };

    private static final int LinearLayout_divider = 0;
    private static final int Linearlayout_measureWithLargestChild = 1;
    private static final int LinearLayout_showDividers = 2;
    private static final int LinearLayout_dividerPadding = 3;

    public static final int SHOW_DIVIDER_NONE = 0;
    public static final int SHOW_DIVIDER_BEGINNING = 1;
    public static final int SHOW_DIVIDER_MIDDLE = 2;
    public static final int SHOW_DIVIDER_END = 3;

    private static final boolean IS_HONEYCOMB = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    private Drawable mDivider;
    protected int mDividerHeight;
    protected int mDividerWidth;
    private int mShowDividers;
    private int mDividerpadding;

    private boolean mClipDivider;
    private boolean mUseLargestChild;

    public IcsLinearLayout(Context context, AttributeSet attrs){
        super(context, attrs);
        TypedArray styledArray = context.obtainStyledAttributes(attrs,R_styleable_LinearLayout);
        setDividerDrawable(styledArray.getDrawable(/*com.android.internal.R.styleable.*/LinearLayout_divider));

        mShowDividers = styledArray.getInt(/*com.android.internal.R.styleable.*/LinearLayout_dividerPadding, 0);
        mUseLargestChild = styledArray.getBoolean(/*com.android.internal.R.styleable.*/Linearlayout_measureWithLargestChild, false);
        styledArray.recycle();
    }

    public void setShowDividers(int showDividers){
        if (showDividers != mShowDividers){
            requestLayout();
            invalidate();
        }
        mShowDividers = showDividers;
    }

    public int getShowDividers(){
        return this.mShowDividers;
    }

    public void setDividerDrawable(Drawable divider){
        if (divider == mDivider){
            return;
        }
        this.mDivider = divider;
        this.mClipDivider = divider instanceof ColorDrawable;
        if (divider != null){
            mDividerWidth = divider.getIntrinsicWidth();
            mDividerHeight = divider.getIntrinsicHeight();
        } else {
            mDividerWidth = 0;
            mDividerHeight = 0;
        }
        setWillNotDraw(divider == null);
        requestLayout();
    }

    public void setDividerPadding(int padding){
        this.mDividerpadding = padding;
    }

    public int getDividerPadding(){
        return this.mDividerpadding;
    }

    public int getDividerWidth(){
        return this.mDividerWidth;
    }

    @Override
    protected void measureChildWithMargins(View child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
        final int index = indexOfChild(child);
        final int orientation = getOrientation();
        final LayoutParams params = (LayoutParams) child.getLayoutParams();
        if (hasDividerBeforeChildAt(index)){
            if (orientation == VERTICAL){
                // Account for the divider by pushing everything up
                params.topMargin = mDividerHeight;
            }else{
                // Account for the divider by pushing everything left
                params.leftMargin = mDividerWidth;
            }
        }

        final int count = getChildCount();
        if (index == count - 1){
            if (hasDividerBeforeChildAt(count)){
                if (orientation == VERTICAL){
                    params.bottomMargin = mDividerHeight;
                }else{
                    params.rightMargin = mDividerWidth;
                }
            }
        }
        super.measureChildWithMargins(child, parentWidthMeasureSpec, widthUsed, parentHeightMeasureSpec, heightUsed);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mDivider != null){
            if (getOrientation() == VERTICAL){
                drawDividersVertical(canvas);
            }else {
                drawDividersHorizontal(canvas);
            }
        }
        super.onDraw(canvas);
    }

    void drawDividersVertical(Canvas canvas){
        final int count = getChildCount();
        for (int i =0; i<count; i++){
            final View child = getChildAt(i);
            if (child != null && child.getVisibility() != GONE){
                if (hasDividerBeforeChildAt(i)){
                    final LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
                    final int top = child.getTop() - layoutParams.topMargin;
                    drawHorizontalDividers(canvas, top);
                }
            }
        }

        if (hasDividerBeforeChildAt(count)){
            final View child = getChildAt(count - 1);
            int bottom = 0;
            if (child == null){
                bottom = getHeight() - getPaddingBottom() - mDividerHeight;
            }else{
                bottom = child.getBottom();
            }
            drawHorizontalDividers(canvas, bottom);
        }
    }

    void drawDividersHorizontal(Canvas canvas){
        final int count = getChildCount();
        for (int i = 0; i< count; i++){
            final View child = getChildAt(i);
            if (child != null && child.getVisibility() != GONE){
                if (hasDividerBeforeChildAt(i)){
                    final LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
                    final int left = child.getLeft() - layoutParams.leftMargin;
                    drawVerticalDividers(canvas,left);
                }
            }
        }

        if (hasDividerBeforeChildAt(count)){
            final View child = getChildAt(count - 1);
            int right = 0;
            if (child == null){
                right = getWidth() - getPaddingRight() - mDividerWidth;
            } else {
                right = child.getRight();
            }
            drawVerticalDividers(canvas,right);
        }
    }


    void drawHorizontalDividers(Canvas canvas, int top){
        if (mClipDivider && !IS_HONEYCOMB){
            canvas.save();
            canvas.clipRect(getPaddingLeft() + mDividerpadding, top, getWidth() - getPaddingRight() - mDividerpadding,
                    top + mDividerHeight);
            mDivider.draw(canvas);
            canvas.restore();
        } else {
            mDivider.setBounds(getPaddingLeft()  + mDividerpadding, top,  getWidth() - getPaddingRight()
                    - mDividerpadding, top + mDividerpadding);
            mDivider.draw(canvas);
        }
    }

    void drawVerticalDividers(Canvas canvas, int left){
        if (mClipDivider && !IS_HONEYCOMB){
            canvas.save();
            canvas.clipRect(left, getPaddingTop() + mDividerpadding, left + mDividerWidth,
                    getHeight() - getPaddingBottom() - mDividerpadding);
            mDivider.draw(canvas);
            canvas.restore();
        } else {
            mDivider.setBounds(left, getPaddingTop() + mDividerpadding, left + mDividerWidth,
                    getHeight() - getPaddingBottom() - mDividerpadding);
            mDivider.draw(canvas);
        }
    }
    protected boolean hasDividerBeforeChildAt(int childIndex){
        if (childIndex == 0){
            return (mShowDividers & SHOW_DIVIDER_BEGINNING) != 0;
        } else if (childIndex == getChildCount()){
            return (mShowDividers & SHOW_DIVIDER_END) != 0;
        } else if ((mShowDividers & SHOW_DIVIDER_MIDDLE) != 0){
            boolean hasVisibleViewBefore =false;
            for (int i = childIndex -1; i>0; i--){
                if (getChildAt(i).getVisibility() != View.GONE){
                    hasVisibleViewBefore = true;
                    break;
                }
            }
            return hasVisibleViewBefore;
        }
        return false;
    }

    public boolean isMeasureWithLargestChildEnabled(){
        return this.mUseLargestChild;
    }
    public void setMeasureWithLargestChildEnabled(boolean enabled){
        this.mUseLargestChild = enabled;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mUseLargestChild){
            final int orientation = getOrientation();
            switch (orientation){
                case HORIZONTAL:
                    useLargestchildHorizontal();
                    break;
                case VERTICAL:
                    useLargestChildVertical();
                    break;
            }
        }
    }

    private void useLargestchildHorizontal(){
        final int childCount = getChildCount();

        int largestChildWidth = 0;
        for (int i =0; i < childCount; i++){
            final View child = getChildAt(i);
            largestChildWidth = Math.max(child.getMeasuredWidth(), largestChildWidth);
        }

        int totalWidth = 0;
        for (int i = 0; i < childCount; i++){
            final View child = getChildAt(i);
            if (child == null || child.getVisibility() == View.GONE){
                continue;
            }

            final LayoutParams params = (LayoutParams) child.getLayoutParams();
            float childExtras = params.weight;
            if (childExtras > 0){
                child.measure(MeasureSpec.makeMeasureSpec(largestChildWidth, MeasureSpec.EXACTLY),
                              MeasureSpec.makeMeasureSpec(child.getMeasuredHeight(), MeasureSpec.EXACTLY));
                totalWidth += largestChildWidth;
            }else{
                totalWidth += largestChildWidth;
            }
            totalWidth += params.leftMargin + params.rightMargin;
        }
        totalWidth += getPaddingLeft() + getPaddingRight();
        setMeasuredDimension(totalWidth, getMeasuredHeight());
    }

    private void useLargestChildVertical(){
        final int childCount = getChildCount();
        int largestChildHeight = 0;
        for (int i = 0; i < childCount; i++){
            final View child = getChildAt(i);
            largestChildHeight = Math.max(child.getMeasuredHeight(), largestChildHeight);
        }
        int totalHeight = 0;
        for (int i = 0; i < childCount; i++){
            final View child = getChildAt(i);
            if (child == null || child.getVisibility() == View.GONE) {
                continue;
            }
            final LayoutParams params = (LayoutParams) child.getLayoutParams();
            float childExtra = params.weight;
            if (childExtra > 0){
                child.measure(MeasureSpec.makeMeasureSpec(child.getMeasuredWidth(), MeasureSpec.EXACTLY),
                        MeasureSpec.makeMeasureSpec(largestChildHeight, MeasureSpec.EXACTLY));
                totalHeight += largestChildHeight;
            }else{
                totalHeight += child.getMeasuredHeight();
            }
            totalHeight += params.leftMargin + params.rightMargin;
        }

        totalHeight += getPaddingLeft() + getPaddingRight();
        setMeasuredDimension(getMeasuredWidth(), totalHeight);

    }
}
