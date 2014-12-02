package com.techiedb.app.videodesk.activities;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
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
public abstract class BaseActivity extends Activity{
    protected static final String TAG = AppConstant.PREFIX + BaseActivity.class.getSimpleName();
    protected BaseController mController = null;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * Removing the handler when the current activity should be decoupled controller component.
     */
    @Override
    protected void onDestroy(){
        if (mController != null){
            mController.onDestroy();
        }
        super.onDestroy();
    }

    /**
     * Get TopActivity for navigating the Activity during while we tried to send the Intent with special
     * Flags like: #Intent.FLAG_CLEAR_TOP or #Intent.SINGLE_TOP.
     * This method is useful for managing back-stack.
     * @return String Type. The Above Activity that current screen must be below into this screen.
     */
    protected String getTopActivity(){
        String fn = "getTopActivity(): ";
        Log.d(TAG, fn + "Getting Above Screen: ");
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName topActivity = am.getRunningTasks(1).get(0).topActivity;
        Log.d(TAG, fn + topActivity.getPackageName() + ", ClassName: " + topActivity.getClassName());
        return topActivity.getClassName();
    }

    /**
     * Method invalidate() used to handle the current Activity when the model or service component which
     * have been changing.
     */
    public abstract void invalidate();
    public abstract void invalidate(Object... params);
}
