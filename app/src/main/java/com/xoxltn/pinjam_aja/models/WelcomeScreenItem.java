/*
 * Copyright (c) 2020 Albert Kristaen (DBC 113 008)
 * ONLY USE UNDER PERMISSION -OR- I AM GONNA CHOP YOUR HANDS OFF!
 */

package com.xoxltn.pinjam_aja.models;

public class WelcomeScreenItem {
    private int mScreenImg;
    private String mTitle, mDescription;

    public WelcomeScreenItem(int screenImg, String title, String description) {
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
