package com.techiedb.app.videodesk.widgets.drawers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

import java.util.ArrayList;

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
public class DrawerProfile {
    private Drawable mProfileDrawable;
    private Drawable mBackground;
    private String mName;
    private String mDescription;
    private OnProfileClickListener mProfileClickListener;
    private ArrayAdapter<DrawerProfile> mAdapter;

    public DrawerProfile setAvatar(Drawable profile){
        this.mProfileDrawable = profile;
        this.notifyDataChanged();
        return this;
    }

    public DrawerProfile setAvatar(Context context, Bitmap avatar){
        this.mProfileDrawable = new BitmapDrawable(context.getResources(),avatar);
        notifyDataChanged();
        return this;
    }

    public Drawable getAvatar(){
        return this.mProfileDrawable;
    }

    public boolean hasAvatar(){
        return this.mProfileDrawable != null;
    }

    public DrawerProfile removeProfileDrawable(){
        this.mProfileDrawable = null;
        this.notifyDataChanged();
        return this;
    }

    public DrawerProfile setBackgroundDrawable(Drawable background){
        this.mBackground = background;
        this.notifyDataChanged();
        return this;
    }

    public Drawable getBackground(){
        return this.mBackground;
    }

    public DrawerProfile removeBackground(){
        this.mProfileDrawable = null;
        this.notifyDataChanged();
        return this;
    }

    public String getName(){
        return this.mName;
    }

    public DrawerProfile setName(String name){
        this.mName = name;
        this.notifyDataChanged();
        return this;
    }

    public boolean hasName(){
        return this.mName != null && !mName.equals("");
    }

    public DrawerProfile removeName(){
        this.mName = null;
        this.notifyDataChanged();
        return this;
    }

    public DrawerProfile setDescription(String description){
        this.mDescription = description;
        this.notifyDataChanged();
        return this;
    }

    public String getDescription(){
        return this.mDescription;
    }

    public boolean hasDescription(){
        return this.mDescription != null && !mDescription.equals("");
    }

    public DrawerProfile removeDescription(){
        this.mDescription = null;
        this.notifyDataChanged();
        return this;
    }

    public DrawerProfile detach(){
        this.mAdapter = null;
        return this;
    }

    public DrawerProfile attachTo(ArrayAdapter<DrawerProfile> adapter){
        this.mAdapter = adapter;
        this.notifyDataChanged();
        return this;
    }
    protected void notifyDataChanged(){
        if (mAdapter != null){
            mAdapter.notifyDataSetChanged();
        }
    }

    public DrawerProfile setOnProfileClickListener(OnProfileClickListener listener){
        this.mProfileClickListener = listener;
        this.notifyDataChanged();
        return this;
    }

    public OnProfileClickListener getOnProfileClickListener(){
        return this.mProfileClickListener;
    }

    public DrawerProfile removeOnProfileClickListener(){
        this.mProfileClickListener = null;
        this.notifyDataChanged();
        return this;
    }

    public boolean hasOnProfileClickListener(){
        return this.mProfileClickListener != null;
    }

    public interface OnProfileClickListener{
        public void onClick(DrawerProfile item);
    }
}
