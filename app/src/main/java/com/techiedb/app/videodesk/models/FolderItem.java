package com.techiedb.app.videodesk.models;

import android.os.Parcel;
import android.os.Parcelable;

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
public class FolderItem extends MediaObject implements Parcelable {
    public static final int NEW_FOLDER_ID = 0;
    private long mBucketId;
    private String mDisplayName;
    private boolean mSelected = false;

    private String mPath;
    private int mItemCount;
    private boolean mPreSelected = false;
    private boolean mIsProtect = false;


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
