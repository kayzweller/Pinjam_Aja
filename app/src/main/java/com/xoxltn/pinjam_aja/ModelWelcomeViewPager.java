/*
 * Created by Albert Kristaen (s6joxx) on 2/10/21, 9:45 PM
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 10/22/20, 11:34 AM
 */

package com.xoxltn.pinjam_aja;

public class ModelWelcomeViewPager {
    private int mScreenImg;
    private String mTitle, mDescription;

    public ModelWelcomeViewPager(int screenImg, String title, String description) {
        mScreenImg = screenImg;
        mTitle = title;
        mDescription = description;
    }

    public
    String getTitle() {
        return mTitle;
    }

    public
    String getDescription() {
        return mDescription;
    }

    public
    int getScreenImg() {
        return mScreenImg;
    }


}
