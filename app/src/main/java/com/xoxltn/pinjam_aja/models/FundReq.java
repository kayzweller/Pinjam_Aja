/*
 * Created by Albert Kristaen on 30/04/20 17:44
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 27/04/20 23:31
 */

package com.xoxltn.pinjam_aja.models;

import java.util.Date;

public class FundReq {
    private Date pinjaman_tanggal_req;
    private int pinjaman_besar;

    public FundReq() {
        //empty constructor needed
    }

    public FundReq(Date pinjaman_tanggal_req, int pinjaman_besar) {
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
