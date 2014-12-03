package com.techiedb.app.videodesk.models;

import android.media.videoeditor.VideoEditor;
import android.net.Uri;

import com.techiedb.app.videodesk.AppConstant;

import java.util.ArrayList;
import java.util.List;

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
 */
public class DeskProject {
    public static final String TAG = AppConstant.PREFIX + DeskProject.class.getSimpleName();

    private final static String PROJECT_METADATA_FILENAME = "metadata.xml";
    public static final int DEFAULT_ZOOM_LEVEL = 20;

    // XML Nodes
    private static final String TAG_PROJECT = "project";
    private static final String TAG_MOVIE = "movie";
    private static final String TAG_DOWNLOAD = "download";
    private static final String ATTR_NAME = "name";
    private static final String ATTR_URI = "uri";
    private static final String ATTR_SAVED = "saved";
    private static final String ATTR_THEME = "theme";
    private static final String ATTR_PLAYHEAD_POSITION = "playhead";
    private static final String ATTR_DURATION = "duration";
    private static final String ATTR_ZOOM_LEVEL = "zoom_level";
    private static final String ATTR_MIME = "mime";
    private static final String ATTR_FILENAME = "filename";
    private static final String ATTR_TIME = "time";

    private final VideoEditor mVideoEditor;
    private final String mProjectPath;
    private final long mProjectDurationMs;
    private final List<Download> mDownloads;
    private String mProjectName;

    private long mLastSaved;
    private Uri mExportedMovieUri;
    private int mAspectRatio;
    private String mTheme;
    private long mPlayheadPosMs;
    private int mZoomLevel;
    private List<MovieMediaItem> mMediaItems = new ArrayList<MovieMediaItem>();
    private boolean mClean;

    public static class Download{
        private final String mMediaUri;
        private final String mMimeType;
        private final String mFileName;
        private final long mTime;

        private Download(String mediaUri, String mimeType, String fileName, long time){
            this.mMediaUri = mediaUri;
            this.mMimeType = mimeType;
            this.mFileName = fileName;
            this.mTime = time;
        }

        public String getMediaUri(){
            return this.mMediaUri;
        }

        public String getMimeType(){
            return this.mMimeType;
        }

        public String getFileName(){
            return this.mFileName;
        }

        public long getTime(){
            return this.mTime;
        }
    }

    public DeskProject(VideoEditor editor, String projectPath, String projectName, long lastSaved,
                       long playHeadPosMs, long durationMs, int zoomLevel, Uri exportedMovieUri,
                       String theme, List<Download> downloads){
        this.mVideoEditor = editor;
        if (editor != null){
            mAspectRatio = editor.getAspectRatio();
        }
        if (downloads != null){
            this.mDownloads = downloads;
        }else{
            this.mDownloads = new ArrayList<Download>();
        }
        this.mProjectPath = projectPath;
        this.mProjectName = projectName;
        this.mLastSaved = lastSaved;
        this.mPlayheadPosMs = playHeadPosMs;
        this.mProjectDurationMs = durationMs;
        this.mZoomLevel = zoomLevel;
        this.mExportedMovieUri = exportedMovieUri;
        this.mTheme  = theme;
        this.mClean = true;
    }

    public void setClean(boolean clean){
        this.mClean = clean;
    }

    public boolean isClean(){
        return this.mClean;
    }

    public String getPath(){
        return mProjectPath;
    }

    public void setProjectName(String projectName){
        this.mProjectName = projectName;
        this.mClean = false;
    }

    public String getName(){
        return this.mProjectName;
    }

    public long getLastSaved(){
        return this.mLastSaved;
    }

    public long getProjectDuration(){
        return this.mProjectDurationMs;
    }

    public int getZoomLevel(){
        return this.mZoomLevel;
    }

    public void setZoomLevel(int zoomLevel){
        this.mZoomLevel = zoomLevel;
    }

    public int getAspectRatio(){
        return this.mAspectRatio;
    }

    public long getPlayHeadPost(){
        return this.mPlayheadPosMs;
    }

    public void setPlayHeadPos(long playHeadPos){
        this.mPlayheadPosMs = playHeadPos;
    }

    public void setAspectRatio(int aspectRatio){
        mAspectRatio = aspectRatio;
        mClean = false;
    }

    public void addExportedMovieUri(Uri uri){
        mExportedMovieUri = uri;
        mClean = false;
    }

    public Uri getExportedMovieUri(){
        return mExportedMovieUri;
    }

    public void setTheme(String theme){
        this.mTheme = theme;
        this.mClean = false;
    }

    public String getTheme(){
        return this.mTheme;
    }

    public void setMediaItem(List<MovieMediaItem> mediaItems){
        this.mMediaItems = mediaItems;
        this.mClean = false;
    }

    public void insertMediaItem(MovieMediaItem mediaItem, String afterMediaItemId){
        if (afterMediaItemId == null){
            if (mMediaItems.size() > 0){
                final MovieMediaItem firstMediaItem = mMediaItems.get(0);
            }
        }
    }
}

