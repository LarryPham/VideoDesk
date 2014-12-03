package com.techiedb.app.videodesk.models;

import android.content.Context;
import android.media.videoeditor.Transition;
import android.media.videoeditor.TransitionAlpha;
import android.media.videoeditor.TransitionCrossfade;
import android.media.videoeditor.TransitionFadeBlack;
import android.media.videoeditor.TransitionSliding;

import com.techiedb.app.videodesk.AppConstant;
import com.techiedb.app.videodesk.R;
import com.techiedb.app.videodesk.TransitionType;
import com.techiedb.app.videodesk.services.VideoDeskService;
import com.techiedb.app.videodesk.utils.FileUtils;

import java.io.IOException;

/**
 * Copyright (C) 2014 Adways Vietnam Inc. All rights reserved.
 * Mobile UX Promotion Division.
 * This software and its documentation are confidential and proprietary
 * information of Adways Vietnam Inc.  No part of the software and
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
 * @since December.03.2014
 * This class represents a transition in the user interface
 **/
public class MovieTransition {
    public static final String TAG = AppConstant.PREFIX + MovieTransition.class.getSimpleName();
    // The Unique Id of the transition
    private final String mUniqueId;
    private final Class<?> mTypeClass;
    private final int mType;
    private final int mBehavior;
    private final int mSlidingDirection;
    private final String mAlphaMaskFilename;
    private final int mAlphaMaskResId;
    private final int mAlphaMaskBlendingPercent;
    private final boolean mAlphaInvert;

    private long mDurationMs;
    private long mAppDurationMs;

    public MovieTransition(Transition transition){
        this.mTypeClass = transition.getClass();
        this.mUniqueId = transition.getId();
        this.mAppDurationMs = mDurationMs = transition.getDuration();
        this.mBehavior = transition.getBehavior();
        if (transition instanceof TransitionSliding){
            this.mSlidingDirection = ((TransitionSliding) transition).getDirection();
        } else{
            mSlidingDirection = -1;
        }
        if (transition instanceof TransitionAlpha){
            final TransitionAlpha alpha = (TransitionAlpha) transition;
            mAlphaMaskFilename = alpha.getMaskFilename();
            mAlphaMaskResId = 0;
            mAlphaMaskBlendingPercent = alpha.getBlendingPercent();
            mAlphaInvert = alpha.isInvert();
        } else {
            mAlphaMaskFilename = null;
            mAlphaMaskResId = 0;
            mAlphaMaskBlendingPercent = 0;
            mAlphaInvert = false;
        }
        mType = toType();
    }

    private int toType(){
        if (TransitionCrossfade.class.equals(mTypeClass)){
            return TransitionType.TRANSITION_TYPE_CROSSFADE;
        }else if (TransitionType.class.equals(mTypeClass)){
            final int rawId = FileUtils.getMaskRawId(mAlphaMaskFilename);
            switch (rawId){
                case R.raw.mask_contour:{
                    return TransitionType.TRANSITION_TYPE_ALPHA_CONTOUR;
                }
                case R.raw.mask_diagonal:{
                    return TransitionType.TRANSITION_TYPE_ALPHA_DIAGONAL;
                }
                default:{
                    throw new IllegalArgumentException("Unknow id for: " + mAlphaMaskFilename);
                }
            }
        }else if (TransitionFadeBlack.class.equals(mTypeClass)){
            return TransitionType.TRANSITION_TYPE_FADE_BLACK;
        }else if (TransitionSliding.class.equals(mTypeClass)){
            switch (mSlidingDirection){
                case TransitionSliding.DIRECTION_BOTTOM_OUT_TOP_IN:{
                    return TransitionType.TRANSITION_TYPE_SLIDING_BOTTOM_OUT_TOP_IN;
                }
                case TransitionSliding.DIRECTION_LEFT_OUT_RIGHT_IN:{
                    return TransitionType.TRANSITION_TYPE_SLIDING_LEFT_OUT_RIGHT_IN;
                }
                case TransitionSliding.DIRECTION_RIGHT_OUT_LEFT_IN:{
                    return TransitionType.TRANSITION_TYPE_SLIDING_RIGHT_OUT_LEFT_IN;
                }
                case TransitionSliding.DIRECTION_TOP_OUT_BOTTOM_IN:{
                    return TransitionType.TRANSITION_TYPE_SLIDING_TOP_OUT_BOTTOM_IN;
                }
                default:{
                    throw new IllegalArgumentException("Unknow direction" + mSlidingDirection);
                }
            }
        }else{
            throw new IllegalArgumentException("Unknown type: " + mTypeClass);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MovieTransition)){
            return false;
        }
        return mUniqueId.equals(((MovieTransition) obj).mUniqueId);
    }

    @Override
    public int hashCode() {
        return mUniqueId.hashCode();
    }
}
