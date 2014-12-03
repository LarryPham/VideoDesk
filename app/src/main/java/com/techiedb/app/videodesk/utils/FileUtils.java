package com.techiedb.app.videodesk.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.videoeditor.MediaProperties;
import android.os.Environment;
import android.util.Log;

import com.techiedb.app.videodesk.AppConstant;
import com.techiedb.app.videodesk.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

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
public class FileUtils {
    private static final String TAG = AppConstant.PREFIX + FileUtils.class.getSimpleName();

    /**
     * It is not possible to instantiate this class
     */
    private FileUtils(){

    }

    public static File getProjectsRootDir(Context context) throws FileNotFoundException, IOException{
        final File dir = context.getExternalFilesDir(null);
        if (dir != null && !dir.exists()){
            if (!dir.mkdirs()){
                throw new FileNotFoundException("Cannot create folder: " + dir.getAbsolutePath());
            }else{
                if (!new File(dir, ".nomedia").createNewFile()){
                    throw new FileNotFoundException("Cannot create file .nomedia");
                }
            }
        }
        return dir;
    }

    public static String createNewProjectPath(Context context) throws FileNotFoundException, IOException{
        final File file = new File(getProjectsRootDir(context), StringUtils.randomString(10));
        if (Log.isLoggable(TAG, Log.DEBUG)){
            Log.d(TAG, "NewProject: " + file.getAbsolutePath());
        }
        return file.getAbsolutePath();
    }

    public static int getMaskRawId(String path) {
        final String filename = new File(path).getName();

        if (filename.equals("mask_countour.jpg")) {
            return R.raw.mask_contour;
        } else if (filename.equals("mask_diagonal.jpg")) {
            return R.raw.mask_diagonal;
        } else {
            throw new IllegalArgumentException("Unknown file: " + path);
        }
    }

    public static String getMaskFileName(Context context, int maskRawResourceId) throws FileNotFoundException,IOException{
        final String fileName;
        switch (maskRawResourceId){
            case R.raw.mask_contour:{
                fileName = "mask_contour.jpg";
                break;
            }
            case R.raw.mask_diagonal:{
                fileName = "mask_contour.jpg";
                break;
            }
            default:{
                throw new IllegalArgumentException("Invalid mask raw resource id");
            }
        }
        final File mf = new File(context.getFilesDir(), fileName);
        if (!mf.exists()){
            Bitmap bitmap = null;
            FileOutputStream fos = null;
            InputStream ins = null;
            try {
                ins = context.getResources().openRawResource(maskRawResourceId);
                bitmap = BitmapFactory.decodeStream(ins);
                if (bitmap == null){
                    throw new IllegalArgumentException("Cannot decode raw resource mask");
                }
                fos = context.openFileOutput(fileName, Context.MODE_WORLD_READABLE);
                if (!bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)){
                    throw new IllegalStateException("Cannot compress bitmap");
                }
            }finally {
                if (ins != null){
                    ins.close();
                }
                if (bitmap != null){
                    bitmap.recycle();
                }
                if (fos != null){
                    fos.flush();
                    fos.close();
                }
            }
        }
        return mf.getAbsolutePath();
    }

    public static String createMovieName(int fileType){
        final String fileName;
        switch (fileType){
            case MediaProperties.FILE_MP4:{
                fileName = "movie_" + StringUtils.randomStringOfNumbers(6) + ".mp4";
                break;
            }
            case MediaProperties.FILE_3GP:{
                fileName = "movie_" + StringUtils.randomStringOfNumbers(6) + ".3gp";
                break;
            }
            default:{
                throw new IllegalArgumentException("Unsupported file type: " + fileType);
            }
        }
        final File moviesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
        if (!moviesDir.exists()){
            moviesDir.mkdirs();
        }
        final File f = new File(moviesDir, fileName);
        return f.getAbsolutePath();
    }

    public static boolean deleteDir(File dir){
        if (dir.isDirectory()){
            final String[] children = dir.list();
            for (int i = 0; i < children.length; i++){
                final File f = new File(dir, children[i]);
                if (!deleteDir(f)){
                    Log.e(TAG,"File cannot be deleted: " + f.getAbsolutePath());
                    return false;
                }
            }
        }
        // The directory is now empty so delete it
        return dir.delete();
    }
    public static String getSimpleName(String fileName){
        final int index = fileName.lastIndexOf('/');
        if (index == -1){
            return fileName;
        }else{
            return fileName.substring(index + 1);
        }
    }
}
