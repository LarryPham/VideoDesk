package com.techiedb.app.videodesk;

import android.content.Context;

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
 */
public class EffectType {
    public static final int CATEGORY_IMAGE = 0;
    public static final int CATEGORY_VIDEO = 1;
    //Effect types
    public static final int EFFECT_KEN_BURNS = 0;
    public static final int EFFECT_COLOR_GRADIENT = 1;
    public static final int EFFECT_COLOR_SEPIA = 2;
    public static final int EFFECT_COLOR_NEGATIVE = 3;
    public static final int EFFECT_VIDEO_CUSTOM = 4;
    public static EffectType[] getEffects(Context context, int category){
        final EffectType[] effects;
        switch (category){
            case CATEGORY_IMAGE:{
                effects = new EffectType[4];
                effects[0] = new EffectType(context.getString(R.string.effect_pan_zoom), EFFECT_KEN_BURNS);
                effects[1] = new EffectType(context.getString(R.string.effect_gradient), EFFECT_COLOR_GRADIENT);
                effects[2] = new EffectType(context.getString(R.string.effect_sepia), EFFECT_COLOR_SEPIA);
                effects[3] = new EffectType(context.getString(R.string.effect_negative), EFFECT_COLOR_NEGATIVE);
                break;
            }
            case CATEGORY_VIDEO:{
                effects = new EffectType[4];
                effects[0] = new EffectType(context.getString(R.string.effect_gradient), EFFECT_COLOR_GRADIENT);
                effects[1] = new EffectType(context.getString(R.string.effect_sepia), EFFECT_COLOR_SEPIA);
                effects[2] = new EffectType(context.getString(R.string.effect_negative), EFFECT_COLOR_NEGATIVE);
                effects[4] = new EffectType(context.getString(R.string.effect_custom), EFFECT_VIDEO_CUSTOM);
                break;
            }
            default:{
                effects = new EffectType[0];
                break;
            }
        }
        return effects;
    }

    private final String mName;
    private final int mType;
    private EffectType(String name, int type){
        this.mName = name;
        this.mType = type;
    }

    public String getName(){
        return this.mName;
    }

    public int getType(){
        return this.mType;
    }
}
