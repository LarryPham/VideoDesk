package com.techiedb.app.videodesk.models;

import android.media.videoeditor.Overlay;
import android.os.Bundle;

import java.util.Map;

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
 *
 * The Reppresentation of an overlay in the user interface
 */
public class MovieOverlay {
    // Overlay Type
    public static final int OVERLAY_TYPE_CENTER_1 = 0;
    public static final int OVERLAY_TYPE_BOTTOM_1 = 1;
    public static final int OVERLAY_TYPE_CENTER_2 = 2;
    public static final int OVERLAY_TYPE_BOTTOM_2 = 3;
    // Custom Overlay type for inserting an given image to the face's position
    public static final int OVERLAY_TYPE_CUSTOM = 4;
    public static final int OVERLAY_TYPE_FACE_DETECTION = 5;
    // Use attribute keys
    private static final String KEY_TYPE = "type";
    private static final String KEY_TITLE = "title";
    private static final String KEY_SUBTITLE ="sub_title";
    // Instance variables
    private final String mUniqueId;
    private long mStartTimeMs;
    private long mDurationMs;
    private String mTitle;
    private String mSubtitle;
    private int mType;

    private long mAppStartTimeMs;
    private long mAppDurationMs;

    private MovieOverlay(){
        this(null);
    }

    public MovieOverlay(Overlay overlay){
        this.mUniqueId = overlay.getId();
        this.mAppStartTimeMs = this.mStartTimeMs = overlay.getStartTime();
        this.mAppDurationMs = this.mDurationMs = overlay.getDuration();

        final Map<String,String> userAttributes = overlay.getUserAttributes();
        mTitle = userAttributes.get(KEY_TITLE);
        mSubtitle = userAttributes.get(KEY_SUBTITLE);
        mType = Integer.parseInt(userAttributes.get(KEY_TYPE));
    }

    public MovieOverlay(String id, long startTimeMs, long durationMs,String title, String subTitle,
                                int type){
        this.mUniqueId = id;
        this.mAppStartTimeMs = this.mStartTimeMs = startTimeMs;
        this.mAppDurationMs = this.mDurationMs = durationMs;
        this.mTitle = title;
        this.mSubtitle = subTitle;
        this.mType = type;
    }

    public String getId(){
        return this.mUniqueId;
    }

    public void setDuration(long durationMs){
        this.mDurationMs = durationMs;
    }

    public long getDuration(){
        return this.mDurationMs;
    }

    public void setAppDuration(long durationMs){
        this.mAppDurationMs = durationMs;
    }

    public long getAppDuration(){
        return this.mDurationMs;
    }

    public void setStartTime(long startTimeMs){
        this.mStartTimeMs = startTimeMs;
    }

    public long getStartTime(){
        return this.mStartTimeMs;
    }

    public void setAppStartTime(long appStartTimeMs){
        this.mAppStartTimeMs = appStartTimeMs;
    }

    public long getAppStartTime(){
        return this.mAppStartTimeMs;
    }

    public int getType(){
        return this.mType;
    }

    public void setType(int type){
        this.mType = type;
    }

    public boolean equals(Object obj){
        if (!(obj instanceof MovieOverlay)){
            return false;
        }
        return this.mUniqueId.equals(((MovieOverlay) obj).mUniqueId);
    }

    @Override
    public int hashCode() {
        return this.mUniqueId.hashCode();
    }

    public Bundle buildUserAttributes(){
        final Bundle userAttributes = new Bundle(4);
        userAttributes.putInt(KEY_TYPE, mType);
        userAttributes.putString(KEY_TYPE, mTitle);
        userAttributes.putString(KEY_SUBTITLE, mSubtitle);
        return userAttributes;
    }

    public static Bundle buildUserAttributes(int type, String title, String subTitle){
        final Bundle userAttributes = new Bundle(4);
        userAttributes.putInt(KEY_TYPE, type);
        userAttributes.putString(KEY_TITLE, title);
        userAttributes.putString(KEY_SUBTITLE, subTitle);
        return userAttributes;
    }

    public void updateUserAttribute(Bundle userAttributes){
        this.mType = userAttributes.getInt(KEY_TYPE);
        this.mTitle = userAttributes.getString(KEY_TITLE);
        this.mSubtitle = userAttributes.getString(KEY_SUBTITLE);
    }

    public static Class<?> getAttributeType(String name){
        if (KEY_TYPE.equals(name)){
            return Integer.class;
        }else{
            return String.class;
        }
    }

    public static int getType(Bundle userAttributes){
        return userAttributes.getInt(KEY_TYPE);
    }

    public static String getTitle(Bundle userAttributes){
        return userAttributes.getString(KEY_TITLE);
    }

    public static String getSubtitle(Bundle userAttributes){
        return userAttributes.getString(KEY_SUBTITLE);
    }
}
