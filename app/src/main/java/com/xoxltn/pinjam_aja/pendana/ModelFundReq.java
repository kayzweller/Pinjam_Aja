/*
 * Created by Albert Kristaen (s6joxx) on 2/10/21, 9:45 PM
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 10/22/20, 11:34 AM
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
