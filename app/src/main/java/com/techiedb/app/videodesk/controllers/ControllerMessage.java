package com.techiedb.app.videodesk.controllers;

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
 * The ControllerMessage represents the message's content for sending the message from controller to service,
 * or feeding back from an <code>Application</code> object to <code>Controller</code> objects.
 *
 * By Defining the controller-message, It's eager to handle the application's workflow. The set of controller's
 * messages which included more than 2 type of messages "REQUEST, OR RESPONSE, ERROR"
 */
public class ControllerMessage {
    // Message for controllers which will send the action for service's components that will be handled the request signals
    public static final int BASE_CONTROLLER_MESSAGGE = 100;
    public static final int REQUEST_OWNER_MESSAGE = BASE_CONTROLLER_MESSAGGE + 1;

    public static final int REQUEST_TO_HANDLE_BASE = 400;

    // Signals for processing the video
    public static final int REQUEST_TO_LOADING_VIDEO = REQUEST_TO_HANDLE_BASE + 1;
    public static final int REQUEST_TO_HANDLE_VIDEO_OVERLAY_IMAGE = REQUEST_TO_HANDLE_BASE + 2;
    public static final int REQUEST_TO_RECORD_VIDEO_OVERLAY_IMAGE = REQUEST_TO_HANDLE_BASE + 3;
    public static final int REQUEST_TO_HANDLE_VIDEO_TRANSITION_IMAGE = REQUEST_TO_HANDLE_BASE + 6;
    public static final int REQUEST_TO_HANDLE_VIDEO_EFFECTS = REQUEST_TO_HANDLE_BASE + 7;

    public static final int REQUEST_TO_EXPORT_VIDEO =  REQUEST_TO_HANDLE_BASE + 4;
    public static final int REQUEST_TO_IMPORT_VIDEO = REQUEST_TO_HANDLE_BASE + 5;

    // Signals for processing the images which will be attached over video
    public static final int REQUEST_TO_IMPORT_IMAGE = REQUEST_TO_HANDLE_BASE + 10;
    public static final int REQUEST_TO_DRAG_IMAGE = REQUEST_TO_HANDLE_BASE + 11;
    public static final int REQUEST_TO_DETECT_FACE_IMAGE = REQUEST_TO_HANDLE_BASE + 12;
    public static final int REQUEST_TO_HANDLE_IMAGE_EFFECTS = REQUEST_TO_HANDLE_BASE + 13;
    public static final int REQUEST_TO_ATTACH_IMAGE = REQUEST_TO_HANDLE_BASE + 14;
    public static final int REQUEST_TO_REMOVE_IMAGE = REQUEST_TO_HANDLE_BASE + 15;
    public static final int REQUEST_TO_ALTER_IMAGE = REQUEST_TO_HANDLE_BASE + 16;

    /** Messages for controllers, application, service will handle the background's action **/
    public static final int ACTION_WAITING_START= 500;

    public static final int RESUME_BACKGROUND_TASK = 351;
    public static final int PAUSE_BACKGROUND_TASK = 350;
    public static final int DECODE_IMAGE_BITMAP = 352;

    public static final int REQUEST_DEVICE_UUID_SETTINGS = 900;
    public static final int REQUEST_DEVICE_INFO_SETTINGS = 901;
}
