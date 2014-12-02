package com.techiedb.app.videodesk.services.loaders;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

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
 */
public class PriorityThreadFactory implements ThreadFactory{
    private final int mPriority;
    private final AtomicInteger mNumber = new AtomicInteger();
    private final String mName;

    public PriorityThreadFactory(String name, int priority){
        this.mName = name;
        this.mPriority = priority;
    }

    public Thread newThread(Runnable runnable){
        return new Thread(runnable, mName + "-" + mNumber.getAndIncrement()){
            @Override
            public void run() {
                android.os.Process.setThreadPriority(mPriority);
                super.run();
            }
        };
    }
}
