package com.techiedb.app.videodesk.services.tasks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

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
 * @since November.28.2014
 *
 * To load the image, we should build the {@link com.techiedb.app.videodesk.services.tasks.ImageLoadTask}
 * for loading the image from bitmap resources, and synchronizing with application
 */
public class ImageLoadTask implements ThreadPool.Job<Bitmap> {
    public MediaItem mMediaItem;
    public BitmapFactory.Options mOptions = new BitmapFactory.Options();
    private int mWidth;
    private int mHeight;
    private String mPath;

    public ImageLoadTask(MediaItem item, int decodeWidth, int decodeHeight){
        this.mMediaItem = item;
        this.mOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;
        this.mWidth = decodeWidth;
        this.mHeight = decodeHeight;
    }

    public ImageLoadTask(String path, int decodeWidth, int decodeHeight){
        this.mPath = path;
        this.mOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;
        this.mWidth = decodeWidth;
        this.mHeight = decodeHeight;
    }

    @Override
    public Bitmap run(ThreadPool.JobContext context) {
        if (context.isCancelled()) return null;
        String path = this.mPath;
        //if (mMediaItem != null) path = mMediaItem.getPath();
        return null;
    }
}
