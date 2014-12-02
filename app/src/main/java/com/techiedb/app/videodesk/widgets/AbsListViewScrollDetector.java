package com.techiedb.app.videodesk.widgets;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AbsListView;

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
abstract class AbsListViewScrollDetector implements AbsListView.OnScrollListener {
    private int mLastScrollY;
    private int mPreviousFirstVisibleItem;
    private AbsListView mListView;
    private int mScrollThreshold;

    abstract void onScrollUp();
    abstract void onScrollDown();

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (isSameRow(firstVisibleItem)){
            int newScrollY = getTopItemScrollY();
            boolean isSignificantDelta = Math.abs(mLastScrollY - newScrollY) > mScrollThreshold;
            if (isSignificantDelta){
                if (mLastScrollY > newScrollY){
                    onScrollUp();
                } else {
                    onScrollDown();
                }
            }
            mLastScrollY = newScrollY;
        } else {
            if (firstVisibleItem > mPreviousFirstVisibleItem){
                onScrollUp();
            } else {
                onScrollDown();
            }
            mLastScrollY = getTopItemScrollY();
            mPreviousFirstVisibleItem = firstVisibleItem;
        }
    }

    public void setScrollThreshold(int scrollThreshold){
        this.mScrollThreshold = scrollThreshold;
    }

    public void setListView(@NonNull AbsListView listView){
        this.mListView = listView;
    }

    private boolean isSameRow(int firstVisibleItem){
        return firstVisibleItem == mPreviousFirstVisibleItem;
    }

    private int getTopItemScrollY(){
        if (mListView == null || mListView.getChildAt(0) == null) return 0;
        View topChild = mListView.getChildAt(0);
        return topChild.getTop();
    }
}
