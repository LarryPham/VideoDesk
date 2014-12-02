package com.techiedb.app.videodesk.widgets.drawers;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Build;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.techiedb.app.videodesk.R;
import com.techiedb.app.videodesk.widgets.drawers.DrawerItem;

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
 * @since December.02.2014
 * Adapter to be used with {@link DrawerView} to display a list of drawer item.
 */
public class DrawerAdapter extends ArrayAdapter<DrawerItem> {
    private int mSelectedPosition = -1; // Default position

    public DrawerAdapter(Context context, List<DrawerItem> dataSet){
        super(context, R.layout.drawer_item, dataSet);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final DrawerItem drawerItem = getItem(position);
        if (drawerItem.isDivider()){
            if (convertView == null || convertView instanceof RelativeLayout){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.drawer_divider_item, parent, false);
            }
        }else{
            if (convertView == null || !(convertView instanceof RelativeLayout)){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.drawer_item, parent, false);
            }

            final ViewHolder viewHolder =  new ViewHolder(convertView);
            int colorPrimary = -1;
            if (position == mSelectedPosition){
                TypedValue a = new TypedValue();
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP){
                    getContext().getTheme().resolveAttribute(android.R.attr.colorPrimary, a, true);
                    if (a.type >= TypedValue.TYPE_FIRST_COLOR_INT && a.type <= TypedValue.TYPE_LAST_COLOR_INT){
                        colorPrimary = a.data;
                    }
                }
                if (colorPrimary == -1){
                    getContext().getTheme().resolveAttribute(R.attr.colorPrimary, a, true);
                    if (a.type >= TypedValue.TYPE_FIRST_COLOR_INT && a.type <= TypedValue.TYPE_LAST_COLOR_INT){
                        colorPrimary = a.data;
                    }
                }
            }

            if (drawerItem.hasImage()){
                viewHolder.getImageView().setImageDrawable(drawerItem.getImage());
                int imageSize;
                int imageMarginEnd;

                if (drawerItem.getImageMode() == DrawerItem.AVATAR){
                    imageSize = getContext().getResources().getDimensionPixelSize(R.dimen.avatar_size);
                }else{
                    imageSize = getContext().getResources().getDimensionPixelSize(R.dimen.icon_size);
                    if (colorPrimary > -1){
                        // Setting the ImageView with PorterDuff's Mode: SRC_INSET
                        viewHolder.getImageView().setColorFilter(colorPrimary, PorterDuff.Mode.SRC_IN);
                    }
                }

                imageMarginEnd = getContext().getResources().getDimensionPixelSize(R.dimen.baseline_content) -
                                    getContext().getResources().getDimensionPixelSize(R.dimen.baseline_start) - imageSize;
                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) viewHolder.getImageView().getLayoutParams();
                layoutParams.width = layoutParams.height = imageSize;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
                    layoutParams.setMarginEnd(imageMarginEnd);
                }else{
                    layoutParams.rightMargin = imageMarginEnd;
                }
            }

            if (drawerItem.hasTextPrimary()){
                viewHolder.getTextViewPrimary().setText(drawerItem.getTextPrimary());
                if (colorPrimary > -1){
                    viewHolder.getTextViewPrimary().setTextColor(colorPrimary);
                }
                if (drawerItem.hasTextPrimary() && (drawerItem.getTextMode() == DrawerItem.TWO_LINE
                                            || drawerItem.getTextMode() == DrawerItem.THREE_LINE)){
                    viewHolder.getTextViewSecondary().setText(drawerItem.getTextSecondary());
                    viewHolder.getTextViewSecondary().setVisibility(View.VISIBLE);

                    if (drawerItem.getTextMode() == DrawerItem.THREE_LINE){
                        viewHolder.getTextViewSecondary().setMaxLines(23);
                    }else{
                        viewHolder.getTextViewSecondary().setMaxLines(1);
                    }
                }else{
                    viewHolder.getTextViewSecondary().setVisibility(View.GONE);
                }
            }else if (drawerItem.hasTextSecondary()){
                viewHolder.getTextViewPrimary().setText(drawerItem.getTextPrimary());
                if (colorPrimary > -1){
                    viewHolder.getTextViewPrimary().setTextColor(colorPrimary);
                }
                viewHolder.getTextViewSecondary().setVisibility(View.GONE);
            }
        }
        return convertView;
    }

    public boolean isEnabled(int pos){
        return getItem(pos).hasOnItemClickListener();
    }

    public void select(int position){
        if (position >= 0 && position <= getCount()){
            this.mSelectedPosition = position;
            notifyDataSetChanged();
        }
    }

    public List<DrawerItem> getItems(){
        List<DrawerItem> items = new ArrayList<DrawerItem>();
        for (int i = 0; i < getCount(); i++){
            items.add(getItem(i));
        }
        return items;
    }

    public int getSelectedPosition(){
        return mSelectedPosition;
    }
    public class ViewHolder{
        LinearLayout mViewRoot;
        ImageView mImageView;
        TextView mTextViewPrimary;
        TextView mTextViewSecondary;

        public ViewHolder(View root){
            mViewRoot = (LinearLayout) root;
            mImageView = (ImageView) root.findViewById(R.id.mdImage);
            mTextViewPrimary = (TextView) root.findViewById(R.id.mdTextPrimary);
            mTextViewSecondary = (TextView) root.findViewById(R.id.mdTextSecondary);
        }

        public LinearLayout getRoot(){
            return this.mViewRoot;
        }

        public TextView getTextViewPrimary(){
            return this.mTextViewPrimary;
        }

        public TextView getTextViewSecondary(){
            return this.mTextViewSecondary;
        }

        public ImageView getImageView(){
            return this.mImageView;
        }
    }
}
