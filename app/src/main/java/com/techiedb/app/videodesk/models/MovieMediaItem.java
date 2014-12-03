package com.techiedb.app.videodesk.models;

import android.media.videoeditor.Effect;
import android.media.videoeditor.MediaImageItem;
import android.media.videoeditor.MediaItem;
import android.media.videoeditor.MediaVideoItem;
import android.media.videoeditor.Overlay;
import android.media.videoeditor.WaveformData;

import java.io.IOException;
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
 * @since November.26.2014
 *
 * This class represents a media item
 */
public class MovieMediaItem {
    private final String mUniqueId;
    private final Class<?> mType;

    private final String mFilename;
    private final int mWidth;
    private final int mHeight;
    private final int mAspectRatio;
    private final long mDurationMs;

    private final long mBeginBoundaryTimeMs;
    private final long mEndBoundaryTimeMs;
    private int mRenderingMode;

    private MovieOverlay mOverlay;
    private MovieEffect mEffect;

    private MovieTransition mBeginTransition;
    private MovieTransition mEndTransition;
    // The audio waveform data
    private WaveformData mWaveFromData;
    private int mVolumePercent;
    private boolean mMuted;
    //Application values
    private long mAppBeginBoundaryTimeMs, mAppEndBoundaryTimeMs;
    private int mAppRenderingMode;
    private int mAppVolumePercent;
    private boolean mAppMuted;

    public MovieMediaItem(MediaItem mediaItem){
        this(mediaItem, mediaItem.getBeginTransition() != null
                        ? new MovieTransition( mediaItem.getBeginTransition()) : null);
    }

    public MovieMediaItem(MediaItem mediaItem, MovieTransition beginTransition){
        this.mType = mediaItem.getClass();
        this.mUniqueId = mediaItem.getId();
        this.mFilename = mediaItem.getFilename();
        this.mAppRenderingMode = mRenderingMode = mediaItem.getRenderingMode();
        this.mAspectRatio = mediaItem.getAspectRatio();
        this.mWidth = mediaItem.getWidth();
        this.mHeight = mediaItem.getHeight();

        this.mDurationMs = mediaItem.getDuration();
        if (mediaItem instanceof MediaVideoItem){
            final MediaVideoItem videoItem = ((MediaVideoItem) mediaItem);
            mAppBeginBoundaryTimeMs = mBeginBoundaryTimeMs = videoItem.getBoundaryBeginTime();
            mAppEndBoundaryTimeMs = mEndBoundaryTimeMs = videoItem.getBoundaryEndTime();
            try {
                mWaveFromData = videoItem.getWaveformData();
            } catch (IOException e) {
                e.printStackTrace();
                mWaveFromData = null;
            }
            mAppVolumePercent = mVolumePercent = videoItem.getVolume();
            mAppMuted = mMuted = videoItem.isMuted();
        } else {
            mAppBeginBoundaryTimeMs = mBeginBoundaryTimeMs = 0;
            mAppEndBoundaryTimeMs = mEndBoundaryTimeMs = mediaItem.getTimelineDuration();
            mWaveFromData = null;
            mAppVolumePercent = mVolumePercent = 0;
            mAppMuted = mMuted = false;
        }

        final List<Overlay> overlays = mediaItem.getAllOverlays();
        for (Overlay overlay: overlays){
           // addOverlay(new MovieOverlay(overlay));
        }
        final List<Effect> effects = mediaItem.getAllEffects();
        for (Effect effect: effects){
            //addEffect(new MovieEffect(effect));
        }


    }

    public boolean isImage(){
        return MediaImageItem.class.equals(mType);
    }

    public boolean isVideoClip(){
        return MediaVideoItem.class.equals(mType);
    }

    public String getId(){
        return mUniqueId;
    }

    public String getFilename(){
        return mFilename;
    }

    public void setRenderingMode(int renderingMode){
        mRenderingMode = renderingMode;
    }

    public int getRenderingMode(){
        return mRenderingMode;
    }

    public void setAppRenderingMode(int renderingMode){
        this.mAppRenderingMode = renderingMode;
    }

    public int getAppRenderingMode(){
        return mAppRenderingMode;
    }

    public void setVolume(int volumePercent){
        mVolumePercent = volumePercent;
    }

    public int getVolume(){
        return mVolumePercent;
    }

    public void setAppVolume(int volumePercent){
        mAppVolumePercent = volumePercent;
    }

    public int getAppVolume(){
        return mAppVolumePercent;
    }

    public void setMute(boolean muted){
        mMuted = muted;
    }

    public boolean isMuted(){
        return mMuted;
    }

    public void setAppMuted(boolean muted){
        mAppMuted = muted;
    }

    public boolean isAppMuted(){
        return mAppMuted;
    }

    long getBoundaryBeginTime(){
        return mBeginBoundaryTimeMs;
    }

    long getBoundaryEndTime(){
        return mEndBoundaryTimeMs;
    }

    public long getAppBeginBoundaryTime(){
        return mAppBeginBoundaryTimeMs;
    }

    public long getAppEndBoundaryTime(){
        return mAppEndBoundaryTimeMs;
    }

    public void setAppExtractBoundaries(long beginMs, long endMs){
        mAppBeginBoundaryTimeMs = beginMs;
        mAppEndBoundaryTimeMs = endMs;
    }

    public long getAppTimeLineDuration(){
        return mAppEndBoundaryTimeMs - mAppBeginBoundaryTimeMs;
    }

    public long getDuration(){
        return mDurationMs;
    }

    public int getWidth(){
        return mWidth;
    }

    public int getHeight(){
        return mHeight;
    }

    public int getAspectRatio(){
        return mAspectRatio;
    }

    public void setBeginTransition(MovieTransition beginTransition){
        mBeginTransition = beginTransition;
    }

    public void setEndTransition(MovieTransition endTransition){
        mEndTransition = endTransition;
    }

    public MovieTransition getBeginTransition(){
        return mBeginTransition;
    }

    public MovieTransition getEndTransition(){
        return mEndTransition;
    }

    public MovieOverlay getOverlay(){
        return mOverlay;
    }

    public void addOverlay(MovieOverlay overlay){
        if (mOverlay != null){
            throw new IllegalStateException("Overlay already set to media item: " + mUniqueId);
        }
        mOverlay = overlay;
    }

    public void removeOverlay(String overlayId){
        if (mOverlay != null){
            if (!mOverlay.getId().equals(overlayId)){
                throw new IllegalStateException("Overlay does not match " + mOverlay.getId() + " " + overlayId);
            }
            mOverlay = null;
        }
    }

    public MovieEffect getEffect(){
        return mEffect;
    }

    public void addEffect(MovieEffect effect){
        if (mEffect != null){
            throw new IllegalStateException("Effect already set for media item: " + mUniqueId);
        }
        mEffect = effect;
    }

    public void removeEffect(String effectId){
        if (mEffect != null){
            if (!mEffect.getId().equals(effectId)){
                throw new IllegalStateException("Effect does not match: " + mEffect.getId() + " "
                + effectId);
            }
            mEffect = null;
        }
    }

    public WaveformData getWaveFormData(){
        return mWaveFromData;
    }

    public void setWaveFormData(WaveformData data){
        this.mWaveFromData = data;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MovieMediaItem)){
            return false;
        }
        return mUniqueId.equals(((MovieMediaItem) o).mUniqueId);
    }

    @Override
    public int hashCode() {
        return mUniqueId.hashCode();
    }
}
