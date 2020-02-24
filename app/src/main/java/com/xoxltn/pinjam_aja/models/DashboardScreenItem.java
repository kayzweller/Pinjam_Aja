/*
 * Copyright (c) 2020 Albert Kristaen (DBC 113 008)
 * ONLY USE UNDER PERMISSION -OR- I AM GONNA CHOP YOUR HANDS OFF!
 */

package com.xoxltn.pinjam_aja.models;

public class DashboardScreenItem {
    private int ScreenImg;
    private String Title, Description;

    public
    DashboardScreenItem(int screenImg, String title, String description) {
        ScreenImg = screenImg;
        Title = title;
        Description = description;
    }

    public
    String getTitle() {
        return Title;
    }

    public
    String getDescription() {
        return Description;
    }

    public
    int getScreenImg() {
        return ScreenImg;
    }


}
