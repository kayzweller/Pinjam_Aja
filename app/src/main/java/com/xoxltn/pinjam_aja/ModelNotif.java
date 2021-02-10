/*
 * Created by Albert Kristaen (s6joxx) on 2/10/21, 9:45 PM
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 10/22/20, 11:34 AM
 */

package com.xoxltn.pinjam_aja;

import java.util.Date;

public class ModelNotif {
    private Boolean notif_type;
    private String notif_title;
    private Date notif_date;
    private String notif_detail;

    public ModelNotif() {
        //empty constructor needed
    }

    public ModelNotif(Boolean notif_type, String notif_title, Date notif_date, String notif_detail) {
        this.notif_type = notif_type;
        this.notif_title = notif_title;
        this.notif_date = notif_date;
        this.notif_detail = notif_detail;
    }

    public Boolean getNotif_type() {
        return notif_type;
    }

    public String getNotif_title() {
        return notif_title;
    }

    public Date getNotif_date() {
        return notif_date;
    }

    public String getNotif_detail() {
        return notif_detail;
    }
}
