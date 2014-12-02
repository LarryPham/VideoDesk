package com.techiedb.app.videodesk.models;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Parcel;

import com.techiedb.app.videodesk.services.loaders.ThreadPool;
import com.techiedb.app.videodesk.utils.MediaUtils;

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
 * @since November.28.2014
 */
public class ImageItem extends MediaItem {
    private int mOrientation;
    private int mThumbnailOrientation;

    private boolean mIsSAM;
    private boolean mInitSAM;

    /**
     * {@link android.database.Cursor} to create a image items.
     * @param cursor MediaStore of the cursor.
     */
    public ImageItem(Cursor cursor) {
        super(cursor);

        this.mOrientation = cursor.getInt(MediaUtils.COL_ORIENTATION);
        this.mThumbnailOrientation = cursor.getInt(MediaUtils.COL_ORIENTATION);
    }

    /**
     * Only view Item from web
     * @param path The file path
     */
    public ImageItem(String path){
        super(path);
    }

    /**
     * {@link android.os.Parcel} to create a image item.
     * @param in {@link android.os.Parcel}
     */
    public ImageItem(Parcel in){
        super(in);
        this.mOrientation = in.readInt();
        this.mThumbnailOrientation = in.readInt();
    }

    public static final Creator<MediaItem> CREATOR = new Creator<MediaItem>() {
        @Override
        public MediaItem createFromParcel(Parcel source) {
            return new ImageItem(source);
        }

        @Override
        public MediaItem[] newArray(int size) {
            return new MediaItem[size];
        }
    };

    @Override
    public ThreadPool.Job<Bitmap> requestImage(int store) {
        return null;
    }

    @Override
    public String getPath() {
        return null;
    }

    @Override
    public int getType() {
        return 0;
    }

    @Override
    public boolean isSelected() {
        return false;
    }

    @Override
    public void setSelected(boolean selected) {

    }

    @Override
    public long getId() {
        return 0;
    }

    @Override
    public String getDisplayName() {
        return null;
    }

    @Override
    public void setProtected(boolean protect) {

    }

    @Override
    public void changeProtectedStatus() {

    }

    @Override
    public boolean isProtected() {
        return false;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
