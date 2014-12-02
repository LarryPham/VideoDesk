package com.techiedb.app.videodesk.services.tasks;

import android.graphics.Bitmap;

import com.techiedb.app.videodesk.services.loaders.ThreadPool;
import com.techiedb.app.videodesk.models.MediaItem;

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
public class LoadThumbTask implements ThreadPool.Job<Bitmap> {
    public static final int FOLDER = 0;
    public static final int CONTENTS = 1;

    public MediaItem mItem;
    public int mStore;

    public LoadThumbTask(int store, MediaItem item){
        this.mItem = item;
        this.mStore = store;
    }

    @Override
    public Bitmap run(ThreadPool.JobContext context) {
        if (mItem == null) return null;
        return mItem.requestImage(mStore).run(context);
    }
}
