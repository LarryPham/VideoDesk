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
public class TransitionType {
    // Transition types
    public static final int TRANSITION_TYPE_ALPHA_CONTOUR = 0;
    public static final int TRANSITION_TYPE_ALPHA_DIAGONAL = 1;
    public static final int TRANSITION_TYPE_CROSSFADE = 2;
    public static final int TRANSITION_TYPE_FADE_BLACK = 3;

    public static final int TRANSITION_TYPE_SLIDING_RIGHT_OUT_LEFT_IN = 4;
    public static final int TRANSITION_TYPE_SLIDING_LEFT_OUT_RIGHT_IN = 5;
    public static final int TRANSITION_TYPE_SLIDING_BOTTOM_OUT_TOP_IN = 6;
    public static final int TRANSITION_TYPE_SLIDING_TOP_OUT_BOTTOM_IN = 7;

    public final static int TRANSITION_RESOURCE_IDS[] = {
            R.drawable.transition_alpha_contour,
            R.drawable.transition_alpha_diagonal,
            R.drawable.transition_crossfade,
            R.drawable.transition_fade_black,
            R.drawable.transition_sliding_right_out_left_in,
            R.drawable.transition_sliding_left_out_right_in,
            R.drawable.transition_sliding_bottom_out_top_in,
            R.drawable.transition_sliding_top_out_bottom_in
    };

    public static TransitionType[] getTransitions(Context context){
        final TransitionType[] transitions = new TransitionType[8];
        transitions[0] = new TransitionType(context.getString(R.string.transitions_alpha_countour),
                TRANSITION_TYPE_ALPHA_CONTOUR);
        transitions[1] = new TransitionType(context.getString(R.string.transitions_alpha_diagonal),
                TRANSITION_TYPE_ALPHA_DIAGONAL);
        transitions[2] = new TransitionType(context.getString(R.string.transitions_crossfade),
                TRANSITION_TYPE_CROSSFADE);
        transitions[3] = new TransitionType(context.getString(R.string.transitions_fade_black),
                TRANSITION_TYPE_FADE_BLACK);
        transitions[4] = new TransitionType(context.getString(R.string.transitions_sliding_right_out_left_in),
                TRANSITION_TYPE_SLIDING_RIGHT_OUT_LEFT_IN);
        transitions[5] = new TransitionType(context.getString(R.string.transitions_sliding_left_out_right_in),
                TRANSITION_TYPE_SLIDING_LEFT_OUT_RIGHT_IN);
        transitions[6] = new TransitionType(context.getString(R.string.transitions_sliding_bottom_out_top_in),
                TRANSITION_TYPE_SLIDING_BOTTOM_OUT_TOP_IN);
        transitions[7] = new TransitionType(context.getString(R.string.transitions_sliding_top_out_bottom_in),
                TRANSITION_TYPE_SLIDING_TOP_OUT_BOTTOM_IN);
        return transitions;
    }

    private final String mName;
    private final int mType;
    public TransitionType(String name, int type){
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
