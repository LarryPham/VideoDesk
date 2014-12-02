package com.techiedb.app.videodesk.widgets.drawers;

import android.graphics.drawable.Drawable;
import android.widget.ArrayAdapter;

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
public class DrawerItem {
    public static final int ICON = 1;
    public static final int AVATAR = 2;
    public static final int SINGLE_LINE = 3;
    public static final int TWO_LINE = 4;
    public static final int THREE_LINE = 5;

    private boolean mIsDivider = false;

    private Drawable mImage;
    private int mImageMode = -1;
    private int mId = -1;
    private String mTextPrimary;
    private String mTextSecondary;
    private int mTextMode = -1;

    private OnItemClickListener mOnClickListener;
    private ArrayAdapter<DrawerItem> mAdapter;

    public int getId(){
       return this.mId;
    }
    public DrawerItem setIsDivider(boolean isDivider){
        this.mIsDivider = isDivider;
        notifyDataChanged();
        return this;
    }

    public boolean isDivider(){
        return this.mIsDivider;
    }

    public DrawerItem setImage(Drawable image, int imageMode){
        this.mImage = image;
        setImageMode(imageMode);
        notifyDataChanged();
        return this;
    }

    public DrawerItem setId(int id){
        this.mId = id;
        return this;
    }

    public DrawerItem setImage(Drawable image){
        setImage(image, ICON);
        notifyDataChanged();
        return this;
    }

    public Drawable getImage(){
        return this.mImage;
    }

    public boolean hasImage(){
        return this.mImage != null;
    }

    public DrawerItem removeImage(){
        this.mImage = null;
        notifyDataChanged();
        return this;
    }

    public DrawerItem setImageMode(int imageMode){
        if (imageMode != ICON && imageMode != AVATAR){
            throw new IllegalArgumentException("Image mode must be either ICON or AVATAR");
        }
        this.mImageMode = imageMode;
        notifyDataChanged();
        return this;
    }

    public int getImageMode(){
        return this.mImageMode;
    }

    public boolean hasImageMode(){
        return this.mImageMode > 0;
    }

    public DrawerItem resetImageMode(){
        this.mImageMode = ICON;
        notifyDataChanged();
        return this;
    }

    public DrawerItem setTextPrimary(String textPrimary){
        this.mTextPrimary = textPrimary;
        this.notifyDataChanged();
        return this;
    }

    public String getTextPrimary(){
        return this.mTextPrimary;
    }

    public boolean hasTextPrimary(){
        return mTextPrimary != null && !mTextPrimary.equals("");
    }

    public DrawerItem removeTextPrimary(){
        this.mTextPrimary = null;
        this.notifyDataChanged();
        return this;
    }

    public DrawerItem setTextSecondary(String textSecondary, int textMode){
        this.mTextSecondary = textSecondary;
        this.setTextMode(textMode);
        this.notifyDataChanged();
        return this;
    }

    public DrawerItem setTextSecondary(String textSecondary){
        setTextSecondary(textSecondary, TWO_LINE);
        return this;
    }

    public String getTextSecondary(){
        return this.mTextSecondary;
    }

    public boolean hasTextSecondary(){
        return this.mTextSecondary != null && !mTextSecondary.equals("");
    }

    public DrawerItem removeTextSecondary(){
        this.mTextSecondary = null;
        this.notifyDataChanged();
        return this;
    }

    public DrawerItem setTextMode(int textMode){
        if (textMode != SINGLE_LINE && textMode != TWO_LINE && textMode != THREE_LINE){
            throw new IllegalArgumentException("TextMode must be either SINGLE_LINE, TWO_LINE, THREE_LINE");
        }

        this.mTextMode = textMode;
        this.notifyDataChanged();
        return this;
    }

    public int getTextMode(){
        return this.mTextMode;
    }

    public boolean hasTextMode(){
        return this.mTextMode > 0;
    }

    public boolean hasOnItemClickListener(){
        return this.mOnClickListener != null;
    }

    public DrawerItem setOnItemClickListener(OnItemClickListener listener){
        mOnClickListener = listener;
        this.notifyDataChanged();
        return this;
    }

    public OnItemClickListener getOnItemClickListener(){
        return this.mOnClickListener;
    }

    public DrawerItem attachTo(ArrayAdapter<DrawerItem> adapter){
        this.mAdapter = adapter;
        this.notifyDataChanged();
        return this;
    }

    public DrawerItem detach(){
        this.mAdapter = null;
        return this;
    }

    protected void notifyDataChanged(){
        if (mAdapter != null){
            mAdapter.notifyDataSetChanged();
        }
    }
    public interface OnItemClickListener{
        void onClick(DrawerItem item, int id, int position);
    }
}
