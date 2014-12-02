package com.techiedb.app.videodesk;

import android.app.Application;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.techiedb.app.videodesk.activities.ActivityHandler;
import com.techiedb.app.videodesk.controllers.ControllerMessage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
 *
 * Into Application, It will be synchronized with Application's instance. It's declared into Manifest.
 * Building the List of {@link com.techiedb.app.videodesk.activities.ActivityHandler} for calling back messages from <code>Application</code>
 * object to controllers and also synchronizing the <code>DataModel</code> with the Application's state.
 */
public class VideoDeskApp extends Application {
    public static final String TAG = AppConstant.PREFIX + VideoDeskApp.class.getSimpleName();
    private List<ActivityHandler> mHandlerList = new ArrayList<ActivityHandler>();
    public AppMainHandler mMainHandler = new AppMainHandler();

    public void addHandler(ActivityHandler handler){
        Log.i(TAG, String.format("Handler -addHandler %s", handler.toString()));
        mHandlerList.add(handler);
    }

    public void addHandlerIfNotExist(ActivityHandler handler){
        boolean blnExist = false;
        try {
            final int size = mHandlerList.size();
            for (int i =0; i < size; i++){
                if (mHandlerList.get(i).hashCode() == handler.hashCode()){
                    blnExist = true;
                    break;
                }
            }

            if (!blnExist){
                Log.e(TAG, String.format("addHandlerIfNotExist"));
                addHandler(handler);
            }
        } catch (Exception ex){
            Log.e(TAG, String.format("addHandlerIfNotExist()" + "Exception: " + ex.getMessage()));
            ex.printStackTrace();
        }
    }

    public void removeHandler(ActivityHandler handler){
        Log.i(TAG, String.format("Handler -RemoveHandler %s", handler.toString()));
        mHandlerList.remove(handler);
        Log.i(TAG, String.format("Handler remain count %d", mHandlerList.size()));
    }

    public void removeAllHandlers(){
        Log.i(TAG, String.format("Handler =RemoveAllHandler %d", mHandlerList.size()));
        mHandlerList.clear();
    }

    public AppMainHandler getMainHandler(){
        return this.mMainHandler;
    }

    class AppMainHandler extends Handler{

        @Override
        public void handleMessage(Message msg){
            String fn = "handleMessage(): ";
            Log.d(TAG, String.format("[AppMainHandler] " + msg.toString()));
            switch (msg.what){
                case ControllerMessage.BASE_CONTROLLER_MESSAGGE:{
                    Log.i(TAG, "BASE_CONTROLLER_MESSAGE");
                    break;
                }
            }

            // Feeding back from application to the chain of handlers into controllers
            for(Iterator<ActivityHandler> handlerIterator = mHandlerList.iterator(); handlerIterator.hasNext(); ){
                Log.d(TAG, String.format("Message's Id: " + msg.what + " handler of "
                                            + handlerIterator.next().getClass().getSimpleName()));
                final ActivityHandler activityHandler = handlerIterator.next();
                Log.d(TAG, String.format(" handlerList's Size: " + mHandlerList.size()));
                activityHandler.callback(msg);
            }
        }
    }
}
