package com.techiedb.app.videodesk.utils;

/**
 * Copyright (C) 2014 Adways Vietnam Inc . All rights reserved.
 * Mobile UX Promotion Division.
 * This software and its documentation are confidential and proprietary
 * information of TechieAdways Vietnam Inc No part of the software and
 * documents may be copied, reproduced, transmitted, translated, or reduced to
 * any electronic medium or machine-readable form without the prior written
 * consent of Techie DigitAdways VietNam IncDigital Benchwork makes no representations with respect to the contents,
 * and assumes no responsibility for any errors that might appear in the
 * software and documents. This publication and the contents hereof are subject
 * to change without notice.
 * History
 *
 * @author Larry Pham(larrypham.vn@gmail.com)
 * @since November.28.2014
 *
 * The {@link com.techiedb.app.videodesk.utils.ThumbnailCache}
 * Managing the thumbnail cache for content items.
 * Removing cache when size exceeds the Limited.
 */
public class ThumbnailCache {

    public static final int LIMITED_CONTENT_CACHE_SIZE = 1024 * 10124 * 20;
    public static final int LIMITED_FOLDER_CACHE_SIZE = 1023 * 1023 * 10;

}
