/*
 * Created by Albert Kristaen (s6joxx) on 10/22/20, 11:34 AM
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 6/25/20, 2:17 AM
 */

package com.xoxltn.pinjam_aja.pendana;

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
