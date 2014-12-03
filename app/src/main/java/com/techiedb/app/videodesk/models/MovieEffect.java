package com.techiedb.app.videodesk.models;

import android.graphics.Rect;
import android.media.videoeditor.Effect;
import android.media.videoeditor.EffectColor;
import android.media.videoeditor.EffectKenBurns;

import com.techiedb.app.videodesk.EffectType;

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
 *
 * An effect can only applied to a single media item
 */
public class MovieEffect {
    private final String mUniqueId;
    private final int mType;
    private long mDurationMillis;
    private long mStartTimeMillis;
    private Rect mStartRect, mEndRect;

    private MovieEffect(){
        this(null);
    }
    public MovieEffect(Effect effect){
        this.mUniqueId = effect.getId();
        this.mStartTimeMillis = effect.getStartTime();
        this.mDurationMillis = effect.getDuration();
        if (effect instanceof EffectKenBurns){
            mStartRect = ((EffectKenBurns) effect).getStartRect();
            mEndRect = ((EffectKenBurns) effect).getEndRect();
        }else{
            mStartRect = null;
            mEndRect = null;
        }
        this.mType = toType(effect);
    }

    public int getType(){
        return this.mType;
    }

    public String getId(){
        return this.mUniqueId;
    }

    public void setDuration(long durationMillis){
        this.mDurationMillis = durationMillis;
    }

    public long getDuration(){
        return this.mDurationMillis;
    }

    public void setStartTime(long startTimeMillis){
        this.mStartTimeMillis = startTimeMillis;
    }

    public long getStartTime(){
        return this.mStartTimeMillis;
    }

    public void setRectangle(Rect startRect, Rect endRect){
        this.mStartRect = startRect;
        this.mEndRect = endRect;
    }

    public Rect getStartRect(){
        return this.mStartRect;
    }

    public Rect getEndRect(){
        return this.mEndRect;
    }

    private static int toType(Effect effect){
        if (effect instanceof EffectKenBurns){
           return EffectType.EFFECT_KEN_BURNS;
        } else if (effect instanceof EffectColor){
            final EffectColor colorEffect = (EffectColor) effect;
            switch (colorEffect.getType()){
                case EffectColor.TYPE_GRADIENT:{
                    return EffectType.EFFECT_COLOR_GRADIENT;
                }
                case EffectColor.TYPE_SEPIA:{
                    return EffectType.EFFECT_COLOR_SEPIA;
                }
                case EffectColor.TYPE_NEGATIVE:{
                    return EffectType.EFFECT_COLOR_NEGATIVE;
                }
                case EffectColor.TYPE_COLOR:
                default:
                    throw new IllegalArgumentException("Unsupported color type effect: " + colorEffect.getType());
            }
        }else{
            throw new IllegalArgumentException("Unsupported effect: " + effect.getClass());
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MovieEffect)){
            return false;
        }
        return mUniqueId.equals(((MovieEffect) obj).mUniqueId);
    }

    @Override
    public int hashCode() {
        return mUniqueId.hashCode();
    }
}
