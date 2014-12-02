package com.techiedb.app.videodesk.fragments;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.techiedb.app.videodesk.AppConstant;
import com.techiedb.app.videodesk.controllers.BaseController;

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
 * @since November.23.2014
 */
public abstract class BaseFragment extends Fragment{
    protected static final String TAG = AppConstant.PREFIX + BaseFragment.class.getSimpleName();
    protected BaseController mController = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onResume(){
        super.onResume();
    }

    /**
     * When the fragment will be destroyed, remember we should to destruct the controller's handler.
     * It will reduce the thread-processing for optimizing the controller's handler.
     */
    @Override
    public void onDestroy(){
        if (mController != null){
            mController.onDestroy();
        }
        super.onDestroy();
    }

    public String geTopActivity(){
        Log.d(TAG, String.format("getTopActivity(): "));
        ActivityManager am = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName topActivity = am.getRunningTasks(1).get(0).topActivity;
        Log.d(TAG, "TopActivity: " + topActivity.getPackageName() + " , ClassName: " + topActivity.getClassName());
        return topActivity.getClassName();
    }

    public abstract void invalidate();
    public abstract void invalidate(Object... params);
}
