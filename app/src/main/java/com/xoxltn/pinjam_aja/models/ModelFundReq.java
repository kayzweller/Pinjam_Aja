/*
 * Created by Albert Kristaen (Kayzweller) on 23/06/20 13:06
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 30/04/20 21:16
 */

package com.xoxltn.pinjam_aja.models;

import java.util.Date;

public class ModelFundReq {
    private Date pinjaman_tanggal_req;
    private int pinjaman_besar;

    public ModelFundReq() {
        //empty constructor needed
    }

    public ModelFundReq(Date pinjaman_tanggal_req, int pinjaman_besar) {
        this.pinjaman_tanggal_req = pinjaman_tanggal_req;
        this.pinjaman_besar = pinjaman_besar;
    }

    public Date getPinjaman_tanggal_req() {
        return pinjaman_tanggal_req;
    }

    public int getPinjaman_besar() {
        return pinjaman_besar;
    }
}
