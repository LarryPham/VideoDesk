package com.techiedb.app.videodesk.utils;

import android.net.Uri;
import android.provider.MediaStore;

import com.techiedb.app.videodesk.models.MediaItem;

/**
 * Copyright (C) 2014 Adways Vietnam Inc . All rights reserved.
 * Mobile UX Promotion Division.
 * This software and its documentation are confidential and proprietary
 * information of TechieAdways Vietnam Inc No part of the software and
 * documents may be copied, reproduced, transmitted, translated, or reduced to
 * any electronic medium or machine-readable form without the prior written
 * consent of Techie DigitAdways VietNam IncDigital Benchwork makes no representations with respect to the contents,
 * and assumes no responsibility for any errors that might appear in the
 * software and documents. This publication and the contents hereof are subject
 * to change without notice.
 * History
 *
 * @author Larry Pham(larrypham.vn@gmail.com)
 * @since November.28.2014
 */
public class MediaUtils {

    public static final int COL_ID = 0;
    public static final int COL_DATA = 1;
    public static final int COL_DISPLAY_NAME = 2;
    public static final int COL_DATE_TAKEN = 3;
    public static final int COL_LATITUDE = 4;
    public static final int COL_LONGITUDE = 5;
    public static final int COL_TITLE = 6;
    public static final int COL_MIME_TYPE = 7;
    public static final int COL_ORIENTATION = 8;
    public static final int COL_DURATIon = 8;
    public static final int COL_RESOLUTION = 9;


    public static final String[] BUCKET_IMAGE_COLUMNS = {
            MediaStore.Images.ImageColumns.BUCKET_ID,
            MediaStore.Images.ImageColumns.DATA,
            MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME
    };

    public static final String[] BUCKET_VIDEO_COLUMNS = {
            MediaStore.Video.VideoColumns.BUCKET_ID,
            MediaStore.Video.VideoColumns.DATA,
            MediaStore.Video.VideoColumns.BUCKET_DISPLAY_NAME
    };

    public static final String[] IMAGE_COLUMNS = {
            MediaStore.Images.ImageColumns._ID,
            MediaStore.Images.ImageColumns.DATA,
            MediaStore.Images.ImageColumns.DISPLAY_NAME,
            MediaStore.Images.ImageColumns.DATE_TAKEN,
            MediaStore.Images.ImageColumns.LATITUDE,
            MediaStore.Images.ImageColumns.LONGITUDE,
            MediaStore.Images.ImageColumns.TITLE,
            MediaStore.Images.ImageColumns.MIME_TYPE,
            MediaStore.Images.ImageColumns.ORIENTATION
    };

    public static final String[] VIDEO_COLUMNS = {
            MediaStore.Video.VideoColumns._ID,
            MediaStore.Video.VideoColumns.DATA,
            MediaStore.Video.VideoColumns.DISPLAY_NAME,
            MediaStore.Video.VideoColumns.DATE_TAKEN,
            MediaStore.Video.VideoColumns.LATITUDE,
            MediaStore.Video.VideoColumns.LONGITUDE,
            MediaStore.Video.VideoColumns.TITLE,
            MediaStore.Video.VideoColumns.MIME_TYPE,
            MediaStore.Video.VideoColumns.DURATION,
            MediaStore.Video.VideoColumns.RESOLUTION
    };

    /**
     * <p>Get data columns from <code>MediaStore</code></p>
     * @param mediaType - Image or Video
     * @return data columns
     */
    public static String[] getBucketColumns(int mediaType){
        return (mediaType == MediaItem.IMAGE) ? BUCKET_IMAGE_COLUMNS : BUCKET_VIDEO_COLUMNS;
    }

    /**
     * <p>Get data URI </p>
     * @param mediaType - Image or Video
     * @return data columns
     */
    public static Uri getContentUri(int mediaType){
        return (mediaType == MediaItem.IMAGE) ? MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                                    : MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
    }

    /**
     * <p>Get data bucketId</p>
     * @param mediaType - Image or Video
     * @return data URI
     */
    public static String getBucketId(int mediaType){
        return (mediaType == MediaItem.IMAGE) ? MediaStore.Images.ImageColumns.BUCKET_ID
                                    : MediaStore.Video.VideoColumns.BUCKET_ID;
    }

    /**
     * <p>Get data display name</p>
     * @param mediaType - Image or Video
     * @return data Display Name
     */
    public static String getBucketDisplayName(int mediaType){
        return (mediaType == MediaItem.IMAGE) ? MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME
                        : MediaStore.Video.VideoColumns.BUCKET_DISPLAY_NAME;
    }

    /**
     * <p>Get bucket data</p>
     * @param mediaType - Image or Video
     * @return bucket data
     */
    public static String getBucketData(int mediaType){
        return (mediaType == MediaItem.IMAGE) ? MediaStore.Images.ImageColumns.DATA
                        : MediaStore.Video.VideoColumns.DATA;
    }
}
