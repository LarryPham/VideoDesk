package com.techiedb.app.videodesk.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.techiedb.app.videodesk.AppConstant;

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
 * @since November.23.2014
 */
public class VideoDeskService extends Service {
    public static final String TAG = AppConstant.PREFIX + VideoDeskService.class.getSimpleName();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
