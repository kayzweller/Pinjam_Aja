/*
 * Created by Albert Kristaen (Kayzweller) on 23/06/20 13:06
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 30/04/20 17:47
 */

package com.xoxltn.pinjam_aja.models;

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
