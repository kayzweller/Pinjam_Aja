/*
 * Created by Albert Kristaen (s6joxx) on 2/10/21, 9:45 PM
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 10/22/20, 11:34 AM
 */

package com.xoxltn.pinjam_aja.pendana;

import java.util.Date;

public class ModelFundDetail {
    private String pinjaman_status;
    private Date pinjaman_tanggal_bayar;
    private int pinjaman_besar;

    public ModelFundDetail() {
        //empty constructor needed
    }

    public ModelFundDetail(String pinjaman_status, Date pinjaman_tanggal_bayar, int pinjaman_besar) {
        this.pinjaman_status = pinjaman_status;
        this.pinjaman_tanggal_bayar = pinjaman_tanggal_bayar;
        this.pinjaman_besar = pinjaman_besar;
    }

    public String getPinjaman_status() {
        return pinjaman_status;
    }

    public Date getPinjaman_tanggal_bayar() {
        return pinjaman_tanggal_bayar;
    }

    public int getPinjaman_besar() {
        return pinjaman_besar;
    }
}
