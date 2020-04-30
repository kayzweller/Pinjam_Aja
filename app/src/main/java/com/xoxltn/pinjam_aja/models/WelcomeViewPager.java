/*
 * Created by Albert Kristaen (Kayzweller) on 30/04/20 17:47
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 27/04/20 18:33
 */

package com.xoxltn.pinjam_aja.models;

public class WelcomeViewPager {
    private int mScreenImg;
    private String mTitle, mDescription;

    public WelcomeViewPager(int screenImg, String title, String description) {
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
