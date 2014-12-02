package com.techiedb.app.videodesk.widgets.drawers;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import com.techiedb.app.videodesk.R;
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
 * An extension of a liner layout that supports the divider API of Android 4.0+. You can populate
 * this layout with data that comes from a {@link android.widget.ListAdapter}
 */
public class LinearListView extends IcsLinearLayout{
    private static final int[] R_styleable_LinearListView = new int[]{
            android.R.attr.entries,
            R.attr.dividerThickness
    };
    private static final int LinearListView_entries = 0;
    private static final int LinearListView_dividerThickness = 1;

    private View mEmptyView;
    private ListAdapter mAdapter;
    private boolean mAreAllItemsSelectable;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener{
        void onItemClick(LinearListView parent, View view, int position, long id);
    }

    private DataSetObserver mDataObserver = new DataSetObserver() {
        @Override
        public void onChanged() {
            setupChildren();
        }

        @Override
        public void onInvalidated() {
            setupChildren();
        }
    };

    @Override
    public void setOrientation(int orientation) {
        if (orientation != getOrientation()){
            int temp = mDividerHeight;
            mDividerHeight = mDividerWidth;
            mDividerWidth = temp;
        }
        super.setOrientation(orientation);
    }

    /**
     * Set the Divider's thickness size in Pixel units. That means setting the divider height if
     * the layout has an HORIZONTAL orientation and setting the divider with otherwise.
     * @param thickness The size of Divider's thickness.
     */
    public void setDividerThickness(int thickness){
        if (getOrientation() == VERTICAL){
            mDividerHeight = thickness;
        }else{
            mDividerHeight = thickness;
        }
        requestLayout();
    }

    public ListAdapter getAdapter(){
        return this.mAdapter;
    }

    /**
     * Sets the data behind this LinearListView.
     * @param adapter The ListAdapter which is responsible for maintaining the data backing this list
     *                and for producing a view to represent an item in that data set.
     * @see #getAdapter()
     */
    public void setAdapter(ListAdapter adapter){
        if (mAdapter != null){
            mAdapter.unregisterDataSetObserver(mDataObserver);
        }
        mAdapter = adapter;
        if (mAdapter != null){
            mAdapter.registerDataSetObserver(mDataObserver);
            mAreAllItemsSelectable = mAdapter.areAllItemsEnabled();
        }
        setupChildren();
    }

    public final void setOnItemClickListener(OnItemClickListener listener){
        this.mOnItemClickListener = listener;
    }

    public final OnItemClickListener getOnItemClickListener(){
        return this.mOnItemClickListener;
    }

    public boolean performItemClick(View view, int position, long id){
        if (mOnItemClickListener != null){
            playSoundEffect(SoundEffectConstants.CLICK);
            mOnItemClickListener.onItemClick(this, view, position, id);
            return true;
        }
        return false;
    }

    public LinearListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R_styleable_LinearListView);
        final int thickness = a.getDimensionPixelSize(LinearListView_dividerThickness, 0);
        if (thickness != 0){
            setDividerThickness(thickness);
        }

        CharSequence[] entries = a.getTextArray(LinearListView_entries);
        if (entries != null){
            setAdapter(new ArrayAdapter<CharSequence>(context, android.R.layout.simple_list_item_1, entries));
        }
        a.recycle();
    }

    public void setEmptyView(View emptyView){
        this.mEmptyView = emptyView;
        final ListAdapter adapter = getAdapter();
        final boolean empty = ((adapter == null) || adapter.isEmpty());
        updateEmptyStatus(empty);
    }

    public View getEmptyView(){
        return this.mEmptyView;
    }

    private void updateEmptyStatus(boolean empty){
        if (empty){
            if (mEmptyView != null){
                mEmptyView.setVisibility(View.VISIBLE);
                setVisibility(View.GONE);
            } else {
                setVisibility(View.VISIBLE);
            }
        }else{
            if (mEmptyView != null){
                mEmptyView.setVisibility(View.GONE);
            }
            setVisibility(View.VISIBLE);
        }
    }

    private void setupChildren(){
        removeAllViews();
        updateEmptyStatus(mAdapter == null || mAdapter.isEmpty());
        if (mAdapter == null) return;
        for (int i = 0; i < mAdapter.getCount(); i++){
            View child = mAdapter.getView(i, null, this);
            if (mAreAllItemsSelectable || mAdapter.isEnabled(i)){
                child.setOnClickListener(new InternalOnClickListener(i));
            }
            addViewInLayout(child, -1, child.getLayoutParams(), true);
        }
    }

    /**
     * Internal OnClickListener that this view associate of each of its children so that they can
     * respond to OnItemClick listener's events. Avoid setting an OnClickListener manually. If you
     * need it you can wrap the child in a simple {@link android.widget.FrameLayout}.
     */
    private class InternalOnClickListener implements OnClickListener{
        int mPosition;

        public InternalOnClickListener(int position){
            this.mPosition = position;
        }

        @Override
        public void onClick(View view) {
            if ((mOnItemClickListener != null) && (mAdapter != null)){
                mOnItemClickListener.onItemClick(LinearListView.this, view,
                        mPosition, mAdapter.getItemId(mPosition));
            }
        }
    }
}
