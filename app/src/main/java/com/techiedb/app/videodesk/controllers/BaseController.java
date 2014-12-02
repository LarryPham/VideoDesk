package com.techiedb.app.videodesk.controllers;

import android.content.Intent;
import android.os.Message;
import android.util.Log;

import com.techiedb.app.videodesk.ActionCounter;
import com.techiedb.app.videodesk.AppConstant;
import com.techiedb.app.videodesk.VideoDeskApp;
import com.techiedb.app.videodesk.activities.ActivityHandler;
import com.techiedb.app.videodesk.activities.BaseActivity;
import com.techiedb.app.videodesk.fragments.BaseFragment;
import com.techiedb.app.videodesk.models.DataModel;
import com.techiedb.app.videodesk.models.MediaItem;
import com.techiedb.app.videodesk.services.Action;
import com.techiedb.app.videodesk.services.VideoDeskService;

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
public abstract class BaseController {
    protected static final String TAG = AppConstant.PREFIX + BaseController.class.getSimpleName();
    protected BaseActivity mActivity;
    protected BaseFragment mFragment;   // When scaffolding the fragment into UI components
    protected VideoDeskApp mApp;

    protected ActivityBaseHandler mHandler = new ActivityBaseHandler();
    protected ActionCounter mCounter = new ActionCounter();

    protected DataModel mDataModel;

    public class ActivityBaseHandler extends ActivityHandler{
        @Override
        public void callback(Message msg) {
            handleMessage(msg);
        }
    }

    public BaseController(BaseActivity activity, DataModel model){
        this.mActivity = activity;
        this.mDataModel = model;
        this.mApp = (VideoDeskApp) mActivity.getApplicationContext();
        onCreate();
    }

    public BaseController(BaseActivity activity, DataModel model, boolean blnFirstRunning){
        this.mActivity = activity;
        this.mDataModel = model;
        this.mApp = (VideoDeskApp) mActivity.getApplicationContext();
        if (blnFirstRunning){
            // mApp.removeAllHandlers();
        }
        onCreate();
    }
    public ActionCounter getCounter(){
        return this.mCounter;
    }

    public void setCounter(ActionCounter counter){
        this.mCounter = counter;
    }

    public void onCreate(){

    }
    /**
     * Handle the received message from an Application's object, processing the updating data-changed
     * into UI components or navigating the processes into the Activity which will send message to
     * current instance.
     * @param msg a received message {@link android.os.Message}
     */
    public abstract void handleMessage(Message msg);

    /**
     * Send the given message which has specified 'content', and code for next-targets
     * @param what User-defined message code.
     * @param obj An arbitrary object to send the recipient.
     */
    public void sendMessage(int what, Object obj){
        final Message msg = new Message();
        msg.what = what;
        msg.obj = obj;
        handleMessage(msg);
    }

    protected Intent obtainIntent(int message) {
        Intent intent = new Intent(mActivity, VideoDeskService.class);
        intent.putExtra(Action.REQUEST_OWNER, this.hashCode());
        intent.putExtra(Action.REQUEST_MESSAGE, message);
        return intent;
    }

    protected boolean checkOwner(Message msg){
        if (msg.arg1 == this.hashCode()){
            return true;
        }else{
            Log.d(TAG, String.format("[PROCESS]-Checking owner false %d:%d", msg.arg1,this.hashCode()));
            return false;
        }
    }

    public abstract void onDestroy();
}
