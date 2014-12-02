package com.techiedb.app.videodesk.widgets;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.nineoldandroids.view.animation.AnimatorProxy;
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
public class FloatLabeledEditText extends FrameLayout {
    private static final int DEFAULT_PADDING_LEFT = 2;

    private TextView mHintTextView;
    private EditText mEditText;
    private Context mContext;

    public FloatLabeledEditText(Context context) {
        super(context);
        this.mContext = context;
    }

    public FloatLabeledEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        setAttributes(attrs);
    }

    public FloatLabeledEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        setAttributes(attrs);
    }

    private void setAttributes(AttributeSet attrs){
        this.mHintTextView = new TextView(this.mContext);

        final TypedArray styledArray = mContext.obtainStyledAttributes(attrs, R.styleable.FloatLabeledEdiText);
        final int padding = styledArray.getDimensionPixelSize(R.styleable.FloatLabeledEdiText_flextPadding, 0);
        final int paddingLeft = styledArray.getDimensionPixelSize(R.styleable.FloatLabeledEdiText_flextPaddingLeft, 0);
        final int paddingRight = styledArray.getDimensionPixelSize(R.styleable.FloatLabeledEdiText_flextPaddingRight, 0);
        final int paddingTop = styledArray.getDimensionPixelSize(R.styleable.FloatLabeledEdiText_flextPaddingTop, 0);
        final int paddingBottom = styledArray.getDimensionPixelSize(R.styleable.FloatLabeledEdiText_flextPaddingBottom, 0);

        if (padding != 0){
            mHintTextView.setPadding(padding, padding, padding, padding);
        }else{
            mHintTextView.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
        }

        mHintTextView.setVisibility(View.INVISIBLE);
        AnimatorProxy.wrap(mHintTextView).setAlpha(0);
        addView(mHintTextView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        styledArray.recycle();
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (child instanceof EditText){
            if (mEditText != null){
                throw new IllegalArgumentException("Can only have one EditText subview");
            }

            final FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(params);
            layoutParams.gravity = Gravity.BOTTOM;
            layoutParams.topMargin = (int) (mHintTextView.getTextSize() + mHintTextView.getPaddingBottom() +
                    mHintTextView.getPaddingTop());
            params = layoutParams;
            setEditText((EditText) child);
        }
        super.addView(child, index, params);
    }

    private void setEditText(EditText editText){
        this.mEditText = editText;
        this.mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                setShowHint(!TextUtils.isEmpty(s));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        this.mEditText.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                onFocusChanged(hasFocus);
            }
        });
    }

    public void onFocusChanged(boolean gotFocus){
        if (gotFocus && mHintTextView.getVisibility() == View.VISIBLE){
            ObjectAnimator.ofFloat(mHintTextView, "alpha", 0.33f, 1f).start();
        } else if (mHintTextView.getVisibility() == View.VISIBLE){
            AnimatorProxy.wrap(mHintTextView).setAlpha(1f); // need this for compat reasons
            ObjectAnimator.ofFloat(mHintTextView,"alpha", 1f, 0.33f).start();
        }
    }

    private void setShowHint(final boolean show){
        AnimatorSet animation = null;
        if ((mHintTextView.getVisibility() == View.VISIBLE) && !show){
            animation = new AnimatorSet();
            ObjectAnimator move = ObjectAnimator.ofFloat(mHintTextView, "translatorY", 0, mHintTextView.getHeight() / 8);
            ObjectAnimator fade = ObjectAnimator.ofFloat(mHintTextView, "alpha", 1, 0);
            animation.playTogether(move,fade);
        } else if ((mHintTextView.getVisibility() != View.VISIBLE) && show){
            animation = new AnimatorSet();
            ObjectAnimator move = ObjectAnimator.ofFloat(mHintTextView, "translatorY", mHintTextView.getHeight()/ 8, 0);
            ObjectAnimator fade;
            if (mEditText.isPressed()){
                fade = ObjectAnimator.ofFloat(mHintTextView, "alpha", 1, 0);
            }else{
                fade = ObjectAnimator.ofFloat(mHintTextView, "alpha", 0, 0.33f);
            }
            animation.playTogether(move,fade);
        }

        if (animation != null){
            animation.addListener( new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    mHintTextView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mHintTextView.setVisibility(show ? View.VISIBLE: View.INVISIBLE);
                    AnimatorProxy.wrap(mHintTextView).setAlpha(show ? 1 : 0);
                }
            });
            animation.start();
        }
    }

    public EditText getEditText(){
        return this.mEditText;
    }

    public void setHint(String hint){
        this.mEditText.setHint(hint);
        this.mHintTextView.setText(hint);
    }

    public CharSequence getHint(){
        return this.mHintTextView.getHint();
    }
}
