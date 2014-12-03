package com.techiedb.app.videodesk.models;

import android.media.videoeditor.AudioTrack;
import android.media.videoeditor.MediaProperties;
import android.media.videoeditor.VideoEditor;
import android.media.videoeditor.WaveformData;

import java.io.IOException;

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
public class MovieAudioTrack {
    private final String mUniqueId;
    private final String mFilename;
    private final int mRawResourceId;
    private final long mDurationMs;
    private long mStartTimeMs;
    private long mTimeLineDurationMs;
    private int mVolumPercent;
    private boolean mMuted;
    private long mBeginBoundaryTimeMs;
    private long mEndBoundaryTimeMs;
    private boolean mLoop;

    private final int mAudioChannels;
    private final int mAudioType;
    private final int mAudioBitrate;
    private final int mAudioSamplingFrequency;

    private boolean isDuckingEnabled;
    private WaveformData mWaveFormData;
    private long mAppStartTimeMs;
    private int mAppVolumePercent;

    private boolean mAppMuted;
    private boolean mAppIsDuckingEnabled;
    private boolean mAppLoop;

    private MovieAudioTrack() throws IOException{
        this((AudioTrack) null);
    }

    public MovieAudioTrack(AudioTrack audioTrack){
        mUniqueId = audioTrack.getId();
        mFilename = audioTrack.getFilename();
        mRawResourceId = 0;

        mAppStartTimeMs = mStartTimeMs = audioTrack.getStartTime();
        mDurationMs = audioTrack.getDuration();

        mBeginBoundaryTimeMs = audioTrack.getBoundaryBeginTime();
        mEndBoundaryTimeMs = audioTrack.getBoundaryEndTime();

        mAudioType = audioTrack.getAudioType();
        mAudioBitrate = audioTrack.getAudioBitrate();
        mAudioSamplingFrequency = audioTrack.getAudioSamplingFrequency();
        mAudioChannels = audioTrack.getAudioChannels();

        mAppMuted = mMuted = audioTrack.isMuted();
        mAppLoop = mLoop = audioTrack.isLooping();
        mAppIsDuckingEnabled = audioTrack.isDuckingEnabled();
        mAppMuted = mMuted = audioTrack.isMuted();
        mAppLoop = mLoop = audioTrack.isLooping();

        mAppIsDuckingEnabled = isDuckingEnabled = audioTrack.isDuckingEnabled();
        try{
            mWaveFormData = audioTrack.getWaveformData();
        }catch (Exception ex){
            mWaveFormData = null;
        }
        mTimeLineDurationMs = mEndBoundaryTimeMs - mBeginBoundaryTimeMs;
    }

    public MovieAudioTrack(int resId){
        mUniqueId = null;
        mFilename = null;
        mRawResourceId = resId;
        mAppStartTimeMs = mStartTimeMs = 0;
        mDurationMs = VideoEditor.DURATION_OF_STORYBOARD;
        mBeginBoundaryTimeMs = mStartTimeMs;
        mEndBoundaryTimeMs = mDurationMs;

        mAudioChannels = 0;
        mAudioType = MediaProperties.ACODEC_AAC_LC;
        mAudioBitrate = 0;
        mAudioSamplingFrequency = 0;

        mAppVolumePercent = mVolumPercent = 100;
        mAppMuted = mMuted = false;
        mAppLoop = mLoop = true;
        mAppIsDuckingEnabled = isDuckingEnabled = true;
        mWaveFormData = null;

        mTimeLineDurationMs = mEndBoundaryTimeMs - mBeginBoundaryTimeMs;
    }

    public String getId(){
        return mUniqueId;
    }

    public int getRawResourceId(){
        return mRawResourceId;
    }

    public String getFilename(){
        return mFilename;
    }

    public int getAudioChannels(){
        return mAudioChannels;
    }

    public int getAudioType(){
        return mAudioType;
    }

    public int getAudioSamplingFrequency(){
        return mAudioSamplingFrequency;
    }

    public int getAudioBitrate(){
        return mAudioBitrate;
    }

    public void setVolume(int volumePercent){
        mVolumPercent = volumePercent;
    }

    public int getVolume(){
        return mVolumPercent;
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

    public void setAppMute(boolean muted){
        mAppMuted = muted;
    }

    public boolean isAppMuted(){
        return mAppMuted;
    }

    public void setStartTime(long startTimeMs){
        mStartTimeMs = startTimeMs;
    }

    public long getStartTime(){
        return mStartTimeMs;
    }

    public void setAppStartTime(long startTimeMs){
        mAppStartTimeMs = startTimeMs;
    }

    public long getAppStartTime(){
        return mAppStartTimeMs;
    }

    public long getDuration(){
        return mDurationMs;
    }

    public long getTimeLineDuration(){
        return mTimeLineDurationMs;
    }

    public void setExtractBoundaries(long beginMs, long endMs){
        mBeginBoundaryTimeMs = beginMs;
        mEndBoundaryTimeMs = endMs;
        mTimeLineDurationMs = mEndBoundaryTimeMs - mBeginBoundaryTimeMs;
    }

    public long getBoundaryBeginTime(){
        return mBeginBoundaryTimeMs;
    }

    public long getBoundaryEndTime(){
        return mEndBoundaryTimeMs;
    }

    public void enableLoop(boolean loop){
        mLoop = loop;
    }

    public boolean isLooping(){
        return mLoop;
    }

    public void enableAppLoop(boolean loop){
        mAppLoop = loop;
    }

    public boolean isAppLooping(){
        return mAppLoop;
    }

    public boolean isDuckingEnabled(){
        return isDuckingEnabled;
    }

    public void enableAppDucking(boolean enabled){
        mAppIsDuckingEnabled = enabled;
    }

    public boolean isAppDuckingEnabled(){
        return mAppIsDuckingEnabled;
    }

    public WaveformData getWaveFormData(){
        return mWaveFormData;
    }

    public void setWaveFormData(WaveformData waveFormData){
        mWaveFormData = waveFormData;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MovieAudioTrack)){
            return false;
        }
        return mUniqueId.equals(((MovieAudioTrack)obj).mUniqueId);
    }

    @Override
    public int hashCode() {
        return mUniqueId.hashCode();
    }
}
