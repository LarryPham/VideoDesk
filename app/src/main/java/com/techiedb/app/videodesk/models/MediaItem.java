package com.techiedb.app.videodesk.models;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.techiedb.app.videodesk.AppConstant;
import com.techiedb.app.videodesk.services.loaders.ThreadPool;

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
 */
public abstract class MediaItem extends MediaObject implements Parcelable {
    public static final String TAG = AppConstant.PREFIX + MediaItem.class.getSimpleName();

    protected long mId;
    protected String mTitle;
    protected String mDisplayName;
    protected String mPath;
    protected String mDateToken;
    protected String mMimeType;

    protected double mLatitude = 0f;
    protected double mLongitude = 0f;

    protected boolean mSelected = false;
    protected boolean mIsProtect = false;

    abstract public ThreadPool.Job<Bitmap> requestImage(int store);

    /**
     * Create the MediaItem's object
     * @param cursor MediaStore of the Cursor
     * @param type IMAGE, VIDEO
     * @return
     */
    public static MediaItem createItem(Cursor cursor, int type){
        if (type == IMAGE){
            //return new MediaImageItem(cursor);
        }
        return null;
    }

    public MediaItem(Cursor cursor){

    }

    public MediaItem(String path){

    }

    public MediaItem(Parcel parcel){

    }
}
