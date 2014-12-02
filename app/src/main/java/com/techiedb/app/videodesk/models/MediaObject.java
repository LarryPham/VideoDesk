package com.techiedb.app.videodesk.models;

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
 */
abstract public class MediaObject {
    public static final int IMAGE = 0;
    public static final int VIDEO = 1;
    public static final int FOLDER = 2;

    /**
     * Get the path
     * @return the path
     */
    abstract public String getPath();

    /**
     * Get the type
     * @return type -IMAGE, VIDEO, FOLDER
     */
    abstract public int getType();
    /**
     * Check the state is selected.
     * @return selected is true Otherwise false
     */

    abstract public boolean isSelected();

    /**
     * Set the selected
     * @param selected
     */
    abstract public void setSelected(boolean selected);
    /**
     * Get Bucket Id
     * @return the bucket's id
     */
    abstract public long getId();
    /**
     * Get the name showing.
     * @return display name
     */
    abstract public String getDisplayName();
    /**
     * Protection Settings.
     * @return protect Protection whether
     */
    abstract public void setProtected(boolean protect);
    /**
     * Change protected status
     */
    abstract public void changeProtectedStatus();
    /**
     * Check the state is protected.
     * @return protected is true Otherwise false
     */
    abstract public boolean isProtected();
}
