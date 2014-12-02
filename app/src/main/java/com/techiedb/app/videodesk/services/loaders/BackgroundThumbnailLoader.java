package com.techiedb.app.videodesk.services.loaders;

import android.graphics.Bitmap;

import com.techiedb.app.videodesk.ActivityStatusImpl;
import com.techiedb.app.videodesk.AppConstant;
import com.techiedb.app.videodesk.models.MediaItem;

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
public abstract class BackgroundThumbnailLoader extends Thread implements ActivityStatusImpl{
    static final String TAG = AppConstant.PREFIX + BackgroundThumbnailLoader.class.getSimpleName();
    WaitNotify mWaitNotify = new WaitNotify();
    boolean mCancel = false;
    boolean mStop = false;

    abstract void loadThumbnail(MediaItem item);


    @Override
    public void run() {
        super.run();
    }

    class WaitNotify{

        public synchronized void stop(){
            try{
                wait();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        public synchronized void resume(){
            try{
                notifyAll();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
