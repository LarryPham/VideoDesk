package com.techiedb.app.videodesk.services;

import android.media.videoeditor.AudioTrack;
import android.media.videoeditor.MediaItem;

import com.techiedb.app.videodesk.models.DeskProject;

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
public class VideoDeskServiceListener {

    /**
     * List of projects was loaded
     * @param projects The array of projects
     * @param exception The exception
     */
    public void onProjectsLoaded(List<DeskProject> projects, Exception exception){

    }

    /**
     * A new project was created
     * @param projectPath The project path
     * @param projectEdited true if the project is edited
     */
    public void onProjectEditState(String projectPath, boolean projectEdited){

    }

    /**
     * A new project was created
     * @param projectPath The project path
     * @param project The VideoDesk project
     * @param mediaItems The list of media items
     * @param audioTracks The list of audio tracks
     * @param exception The exception that occurred
     */
    public void onVideoEditorCreated(String projectPath, DeskProject project, List<MediaItem> mediaItems,
                                     List<AudioTrack> audioTracks, Exception exception){

    }

    /**
     * The aspect ratio was set
     * @param projectPath The project path
     * @param aspectRatio The aspect ratio
     * @param exception The exception that occurred
     */
    public void onVideoEditorAspectRatioSet(String projectPath, int aspectRatio, Exception exception){

    }

    /**
     * The specified theme was applied
     * @param projectPath The project path
     * @param theme The theme
     * @param ex The exception that occurred
     */
    public void onVideoEditorThemeApplied(String projectPath, String theme, Exception ex){

    }

    public void onVideoEditorExportCanceled(String projectPath, String fileName){

    }
}