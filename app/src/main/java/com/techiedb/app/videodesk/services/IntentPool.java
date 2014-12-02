package com.techiedb.app.videodesk.services;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.techiedb.app.videodesk.AppConstant;

import java.util.ArrayList;
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
 */
public class IntentPool {
    public static final String TAG = AppConstant.PREFIX + IntentPool.class.getSimpleName();
    private final List<Intent> mIntentPool;

    /**
     * Constructor for initializing the Intent's PoolStorage with given size
     * @param initialSize The Initialized Size for PoolStorage.
     */
    public IntentPool(int initialSize){
        this.mIntentPool = new ArrayList<Intent>(initialSize);
    }

    /**
     * @return The Intent is retrieved from the pool or if the pool is empty
     *      a new Intent is allocated.
     */
    public synchronized Intent get(Context context, Class<?> clzz){
        final Intent intent = get();
        intent.setComponent(new ComponentName(context, clzz));
        return intent;
    }

    /**
     * @return The Intent is retrieved from the Pool's Storage or if the pool is empty
     *      a new Intent is allocated.
     */
    public synchronized Intent get(){
        if (mIntentPool.size() > 0){
            final Intent intent = mIntentPool.remove(0);
            // Clear the content of the Intent
            final Bundle extras = intent.getExtras();
            for (String keys : extras.keySet()){
                intent.removeExtra(keys);
            }
            intent.setComponent(null);
            return intent;
        } else {
            if (Log.isLoggable(TAG, Log.DEBUG)){
                Log.d(TAG, "Pool enlarged");
            }
            return new Intent();
        }
    }
    /**
     * @param intent Return an Intent to the pool
     */
    public synchronized void put(Intent intent){
        this.mIntentPool.add(intent);
    }

    /**
     * Removing all intents of the Intent's PoolStorage.
     */
    public synchronized void clear(){
        this.mIntentPool.clear();
    }
}
