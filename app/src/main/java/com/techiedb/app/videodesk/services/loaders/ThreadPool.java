package com.techiedb.app.videodesk.services.loaders;

import android.util.Log;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
public class ThreadPool {

    private static final int CORE_POOL_SIZE = 4; // For Default CPU Cores
    private static final int MAX_POOL_SIZE = 8;  // Maximum Pool Storage's Size.
    private static final int KEEP_ALIVE_TIME = 10; // ten seconds

    // Resource Type
    public static final int MODE_NONE = 0;
    public static final int MODE_CPU = 1;
    public static final int MODE_NETWORK = 2;

    ResourceCounter mCpuCounter = new ResourceCounter(2);
    ResourceCounter mNetworkCounter = new ResourceCounter(2);

    public interface Job<T>{
        public T run(JobContext context);
    }

    private static class JobContextStub implements JobContext{

        @Override
        public boolean isCancelled() {
            return false;
        }

        @Override
        public void setCancelListener(CancelListener listener) {

        }

        @Override
        public boolean setMode(int mode) {
            return true;
        }
    }

    public interface JobContext{
        boolean isCancelled();
        void setCancelListener(CancelListener listener);
        boolean setMode(int mode);
    }

    public interface CancelListener{
        public void onCancel();
    }

    public static class ResourceCounter{
        public int mValue;
        public ResourceCounter(int value){
            this.mValue = value;
        }
    }

    private final Executor mExecutor;

    public ThreadPool(){
        mExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<Runnable>(),
                new PriorityThreadFactory("thread-pool",android.os.Process.THREAD_PRIORITY_BACKGROUND));

    }

    public <T> Future<T> submit(Job<T> job, FutureListener<T> listener){
        Worker<T> worker = new Worker<T>(job, listener);
        mExecutor.execute(worker);
        return worker;
    }

    public <T> Future<T> submit(Job<T> job){
        return submit(job, null);
    }

    private class Worker<T> implements Runnable, Future<T>, JobContext{
        private static final String TAG = "Worker";
        private Job<T> mJob;
        private FutureListener<T> mListener;
        private CancelListener mCancelListener;
        private ResourceCounter mWaitOnResource;
        private volatile boolean mIsCancelled;
        private boolean mIsDone;
        private T mResult;
        private int mMode;

        public Worker(Job<T> job, FutureListener<T> listener){
            this.mJob = job;
            this.mListener = listener;
        }

        @Override
        public synchronized void setCancelListener(CancelListener listener) {
            mCancelListener = listener;
            if (mIsCancelled && mCancelListener != null){
                mCancelListener.onCancel();
            }
        }

        @Override
        public boolean setMode(int mode) {
            ResourceCounter resCounter = modeToCounter(mMode);
            if (resCounter != null){
                releaseResource(resCounter);
            }
            mMode = MODE_NONE;
            // Acquire new resource
            resCounter = modeToCounter(mode);
            if (resCounter != null){
                if (!accquireResource(resCounter)){
                    return false;
                }
                mMode = mode;
            }
            return true;
        }

        @Override
        public boolean isDone() {
            return mIsDone;
        }

        @Override
        public T get() {
            while (!mIsDone){
                try{
                    wait();
                }catch (Exception ex){
                    Log.w(TAG, String.format("Ignore Exception: - %s", ex.getMessage()));
                }
            }
            return mResult;
        }

        @Override
        public void waitDone() {
            get();
        }

        @Override
        public void run() {
            T result = null;
            if (setMode(MODE_CPU)){
                try{
                    result = mJob.run(this);
                }catch (Throwable ex){
                    Log.w(TAG, String.format("Exception in running a job - %s", ex.getMessage()));
                }
            }

            synchronized (this){
                setMode(MODE_NONE);
                mResult = result;
                notifyAll();
            }
            if (mListener != null) mListener.onFutureDone(this);
        }

        @Override
        public synchronized void cancel(){

            if (mIsCancelled) return;
            mIsCancelled = true;
            if (mWaitOnResource != null){
                synchronized (mWaitOnResource){
                    mWaitOnResource.notifyAll();
                }
            }

            if (mCancelListener != null){
                mCancelListener.onCancel();
            }
        }

        private ResourceCounter modeToCounter(int mode){
            if (mode == MODE_CPU){
                return mCpuCounter;
            }else if (mode == MODE_NETWORK){
                return mNetworkCounter;
            }else{
                return null;
            }
        }

        private boolean accquireResource(ResourceCounter counter){
            while (true){
                synchronized (this){
                    if (mIsCancelled){
                        mWaitOnResource = null;
                        return false;
                    }
                    mWaitOnResource = counter;
                }

                synchronized (counter){
                    if (counter.mValue > 0){
                        counter.mValue--;
                        break;
                    }else{
                        try{
                            counter.wait();
                        }catch (InterruptedException ex){

                        }
                    }
                }
            }
            synchronized (this){
                mWaitOnResource = null;
            }
            return true;
        }

        private void releaseResource(ResourceCounter counter){
            synchronized (counter){
                counter.mValue++;
                counter.notifyAll();
            }
        }
        @Override
        public boolean isCancelled(){
            return mIsCancelled;
        }

    }
}
