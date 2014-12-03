package com.techiedb.app.videodesk.models;

import android.media.videoeditor.AudioTrack;
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
    private final int mAutioSamplingFrequency;

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

        mAudioChannels = audioTrack.getAudioChannels();
        mAppMuted = mMuted = audioTrack.isMuted();
        mAppLoop = mLoop = audioTrack.isLooping();
        mAppIsDuckingEnabled = audioTrack.isDuckingEnabled();
        mAppMuted = mMuted = audioTrack.isMuted();
        mAppLoop = mLoop = audioTrack.isLooping();

    }
}
