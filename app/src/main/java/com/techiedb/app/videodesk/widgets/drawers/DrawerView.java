package com.techiedb.app.videodesk.widgets.drawers;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.techiedb.app.videodesk.AppConstant;
import com.techiedb.app.videodesk.R;
import com.techiedb.app.videodesk.widgets.ScrimInsetsScrollView;

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
 *
 * View to be used with {@link android.support.v4.widget.DrawerLayout} to display a drawer
 * which is fully compliant with the Material Design Specification.
 */
public class DrawerView extends ScrimInsetsScrollView implements ScrimInsetsScrollView.OnInsetsCallback {
    private static final String TAG = AppConstant.PREFIX + DrawerView.class.getSimpleName();

    private DrawerProfile mProfile;
    private DrawerAdapter mAdapter;
    private DrawerItem.OnItemClickListener mOnItemClickListener;
    private LinearLayout mLayout;
    private FrameLayout mFrameLayoutProfile;

    private RelativeLayout mRelativeLayoutProfileContent;
    private ImageView mImageViewBackgroundProfile;
    private ImageView mImageViewAvatarProfile;
    private TextView mTextViewNameProfile;
    private TextView mTextViewDescriptionProfile;

    private LinearListView mLinearListView;
    private int mStatusBarHeight = 0;

    public DrawerView(Context context) {
        this(context, null);
    }

    public DrawerView(Context context, AttributeSet attrs) {
        this (context, attrs, 0);
    }

    public DrawerView(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
        init(context);
    }
    private void init(Context context){
        inflate(context, R.layout.layout_drawer, this);
        findViews();
        setClipToPadding(false);
        setBackgroundColor(getResources().getColor(R.color.drawer_background));
        setInsetForeground(new ColorDrawable(getResources().getColor(R.color.scrim)));
        setInsetsCallback(this);

        mAdapter = new DrawerAdapter(context, new ArrayList<DrawerItem>());
        mLinearListView.setAdapter(mAdapter);
    }

    private void findViews(){
        mLayout = (LinearLayout) findViewById(R.id.mdLayout);
        mFrameLayoutProfile = (FrameLayout) findViewById(R.id.mdLayoutProfile);
        mRelativeLayoutProfileContent = (RelativeLayout) findViewById(R.id.mdLayoutProfileContent);
        mImageViewBackgroundProfile = (ImageView) findViewById(R.id.mdBackgroundProfile);
        mImageViewAvatarProfile = (ImageView) findViewById(R.id.mdAvatarProfile);
        mTextViewNameProfile = (TextView) findViewById(R.id.mdNameProfile);
        mTextViewDescriptionProfile = (TextView) findViewById(R.id.mdDescriptionProfile);

        mLinearListView = (LinearListView) findViewById(R.id.mdLinearListViewPrimary);
    }

    private void updateSpacing(){
        if (mProfile != null && mProfile.getAvatar()!= null && mProfile.getName() != null && !mProfile.getName().isEmpty()){
            mFrameLayoutProfile.getLayoutParams().height = Math.round(getLayoutParams().width / 16 * 9) + mStatusBarHeight;
            mRelativeLayoutProfileContent.getLayoutParams().height = Math.round(getLayoutParams().width / 16 * 9);
            mLayout.setPadding(0,0,0,0);
        }else{
            mLayout.setPadding(0, mStatusBarHeight, 0, 0);
        }
    }

    private void updateProfile(){
        if (mProfile != null && mProfile.getAvatar() != null && mProfile.getName() != null && !mProfile.getName().isEmpty()){
            mImageViewAvatarProfile.setImageDrawable(mProfile.getAvatar());
            mTextViewNameProfile.setText(mProfile.getName());

            if (mProfile.getBackground() != null){
                mImageViewBackgroundProfile.setImageDrawable(mProfile.getBackground());
            }else{
                mImageViewBackgroundProfile.setImageDrawable(new ColorDrawable(getResources().getColor(R.color.primary_material_dark)));
            }

            if (mProfile.getDescription() != null && !mProfile.getDescription().equals("")){
                mTextViewDescriptionProfile.setVisibility(View.GONE);
                mTextViewDescriptionProfile.setText(mProfile.getDescription());
            }else{
                mTextViewDescriptionProfile.setVisibility(View.GONE);
            }
            if (mProfile.hasOnProfileClickListener()){
                mFrameLayoutProfile.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mProfile.getOnProfileClickListener().onClick(mProfile);
                    }
                });
                mFrameLayoutProfile.setEnabled(true);
            }else{
                mFrameLayoutProfile.setEnabled(false);
            }
            mFrameLayoutProfile.setVisibility(View.VISIBLE);
        }else{
            mFrameLayoutProfile.setVisibility(View.GONE);
        }
        updateSpacing();
    }

    private void updateList(){
        if (mAdapter.getCount() == 0){
            mLinearListView.setVisibility(View.GONE);
        }else{
            mLinearListView.setVisibility(View.VISIBLE);
        }
        mLinearListView.setOnItemClickListener(new LinearListView.OnItemClickListener() {
            @Override
            public void onItemClick(LinearListView parent, View view, int position, long id) {
                final DrawerItem item= mAdapter.getItem(position);
                if (!item.isDivider()){
                    if (item.hasOnItemClickListener()){
                        item.getOnItemClickListener().onClick(item, item.getId(), position);
                    }else{
                        if(hasOnItemClickListener()){
                            mOnItemClickListener.onClick(item, item.getId(), position);
                        }
                    }
                }
            }
        });
    }

    /**
     * Sets a profile to the drawer view
      * @param profile Profile to set
     */
    public DrawerView setProfile(DrawerProfile profile){
        this.mProfile = profile;
        updateProfile();
        return this;
    }

    /**Gets the profile of the drawer view
     *
     * @return Profile of the drawer view
     */
    private DrawerProfile getProfile(){
        return this.mProfile;
    }

    public DrawerView addItems(List<DrawerItem> items){
        for (DrawerItem item: items){
            item.attachTo(mAdapter);
        }
        mAdapter.addAll(items);
        updateList();
        return this;
    }

    /**
     * Adds an item to the drawer view
     * @param item Item to add
     * @return DrawerView
     */
    public DrawerView addItem(DrawerItem item){
        item.attachTo(mAdapter);
        updateList();
        return this;
    }

    public List<DrawerItem> getItems(){
        return this.mAdapter.getItems();
    }
    /**
     * Adds a divider to the drawer item
     */
    public DrawerView addDivider(){
        addItem(new DrawerDividerItem());
        return this;
    }

    /**
     * Gets an item from the DrawerItem
     * @param id The item ID
     * @return Item from the drawer view
     */
    public DrawerItem findItemById(int id){
        for (int i = 0; i< mAdapter.getCount(); i++){
            if (mAdapter.getItem(i).getId() == id){
                return mAdapter.getItem(id);
            }
        }
        return null;
    }

    /**
     * Selects an item from the drawer view
     * @return position The item's position.
     */
    public DrawerItem selecteItem(int position){
        mAdapter.select(position);
        return null;
    }

    /**
     * Gets the selected item position of the drawer view
     * @return Position of the selected item
     */
    public int getSelectedPosition(){
        return mAdapter.getSelectedPosition();
    }

    /**
     * Selects an item from the drawer view
     * @return id the item's ID
     */
    public void selectItemId(int id){
        for (int i =0; i< mAdapter.getCount(); i++){
            if (mAdapter.getItem(i).getId() == id){
                mAdapter.select(i);
            }
        }
    }

    public DrawerItem getItem(int position){
        return mAdapter.getItem(position);
    }

    /**
     * Removes an item from the drawer view
     * @param item Item to remove
     */
    public DrawerView removeItem(DrawerItem item){
        item.detach();
        mAdapter.remove(item);
        updateList();
        return this;
    }

    /**
     * Removes an item from the drawer view
     * @param position Position to remove
     */
    public DrawerView removeItem(int position){
        mAdapter.getItem(position).detach();
        mAdapter.remove(mAdapter.getItem(position));
        updateList();
        return this;
    }

    /**
     * Removes all items from the drawer view
     * @return
     */
    public DrawerView clearItems(){
        for (DrawerItem item: mAdapter.getItems()){
            item.detach();
        }
        mAdapter.clear();
        updateList();
        return this;
    }

    /**
     * Gets an item click listener from drawer view
     */
    public DrawerItem.OnItemClickListener getOnItemClickListener(){
        return this.mOnItemClickListener;
    }

    /**
     * Sets an item click listener to the drawer view
     * @param listener Listener to be set
     * @return DrawerView The current instance of 'DrawerView'(Using <code>Builder Pattern</code>
     */
    public DrawerView setOnItemClickListener(DrawerItem.OnItemClickListener listener){
        mOnItemClickListener = listener;
        updateList();
        return this;
    }
    /**
     * Gets whether the drawer view has an item click listener set to it
     *
     * @return True if the drawer view has an item click listener set to it, false otherwise.
     */
    public boolean hasOnItemClickListener(){
        return mOnItemClickListener != null;
    }
    @Override
    public void onInsetsChanged(Rect insets) {
        this.mStatusBarHeight = insets.top;
        updateSpacing();
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldheight) {
        super.onSizeChanged(width, height, oldWidth, oldheight);
        if (width != oldWidth){
            // Window width
            int windowWidth = ((Activity) getContext()).getWindow().getDecorView().getWidth();
            // Minus the width of the vertical nav bar
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                int navigationBarWidthResId = getResources().getIdentifier("navigation_bar_width","dimen","android");
                if (navigationBarWidthResId > 0){
                    windowWidth -= getResources().getDimensionPixelSize(navigationBarWidthResId);
                }
            }

            // App bar size
            TypedValue typedValue = new TypedValue();
            getContext().getTheme().resolveAttribute(R.attr.actionBarSize, typedValue, true);
            int actionBarSize = TypedValue.complexToDimensionPixelSize(typedValue.data, getResources().getDisplayMetrics());
            int fwidth = windowWidth - actionBarSize;
            int maxWidth = getResources().getDimensionPixelSize(R.dimen.drawer_max_width);
            getLayoutParams().width = Math.min(fwidth, maxWidth);
            updateSpacing();
        }
    }
}
