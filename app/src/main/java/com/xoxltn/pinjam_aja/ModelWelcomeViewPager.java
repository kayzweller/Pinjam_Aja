/*
 * Created by Albert Kristaen (s6joxx) on 10/22/20, 11:34 AM
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 6/23/20, 1:19 PM
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
