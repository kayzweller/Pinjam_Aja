/*
 * Created by Albert Kristaen (s6joxx) on 2/10/21, 9:45 PM
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 10/22/20, 11:34 AM
 */

package com.xoxltn.pinjam_aja.peminjam;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.xoxltn.pinjam_aja.R;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PeminjamReqPinjamanActivity extends AppCompatActivity {

    TextView mTextBesarPinjaman, mTextTotalBesarPinjaman, mNotifLoad;
    Button mButtonAjukanPinjaman;
    ProgressBar mLoading;

    int passdata;

    private FirebaseFirestore mFire;
    private DocumentReference mDocRef;
    private String mUserID;
    private String mIDPinjaman, mIDNotif;
    private Boolean mPinjamanStatus;

    private Date mCurrentDate;

    //-------------------------------------------------------------------------------------------//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peminjam_req_pinjaman);

        mCurrentDate = Calendar.getInstance().getTime();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mFire = FirebaseFirestore.getInstance();

        mUserID = mAuth.getUid();
        mIDPinjaman = mFire.collection("PEMINJAM").document().getId();
        mIDNotif = mFire.collection("NOTIFIKASI").document().getId();
        mDocRef = mFire.collection("USER").document(mUserID);

        mTextBesarPinjaman = findViewById(R.id.text_Pinjaman);
        mTextTotalBesarPinjaman = findViewById(R.id.text_TotalBayarPinjaman);
        mNotifLoad = findViewById(R.id.notif_load);
        mButtonAjukanPinjaman = findViewById(R.id.button_load_req);
        mLoading = findViewById(R.id.loading);
        mLoading.setAlpha(0);
        mNotifLoad.setVisibility(View.GONE);

        // :: GET DATA SENT FROM INTENT
        Bundle extras = this.getIntent().getExtras();
        passdata = Objects.requireNonNull(extras)
                .getInt(PeminjamHomeFragment.EXTRA_NUMBER, 0);

        setValueTextView();
        cekStatusPinjaman();

    }

    //-------------------------------------------------------------------------------------------//

    private void cekStatusPinjaman() {
        mDocRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot doc =  Objects.requireNonNull(task.getResult());
                mPinjamanStatus = doc.getBoolean("pinjaman_status");
            }
        });
    }

    //-------------------------------------------------------------------------------------------//

    private void setValueTextView() {

        switch (passdata) {
            case 500000: {
                String setvalue = "Rp. 500.000,-";
                String setvalue2 = "Rp. 600.000,-";

                mTextBesarPinjaman.setText(setvalue);
                mTextTotalBesarPinjaman.setText(setvalue2);
                break;
            }
            case 1000000: {
                String setvalue = "Rp. 1.000.000,-";
                String setvalue2 = "Rp. 1.200.000,-";

                mTextBesarPinjaman.setText(setvalue);
                mTextTotalBesarPinjaman.setText(setvalue2);
                break;
            }
            case 1500000: {
                String setvalue = "Rp. 1.500.000,-";
                String setvalue2 = "Rp. 1.800.000,-";
                mTextBesarPinjaman.setText(setvalue);
                mTextTotalBesarPinjaman.setText(setvalue2);
                break;
            }
        }
    }

    //-------------------------------------------------------------------------------------------//

    public void OnClickAjukanPinjaman(View view) {

        mButtonAjukanPinjaman.setEnabled(false);
        mLoading.setAlpha(1);


        Handler handler = new Handler();
        handler.postDelayed(() -> {

            int total_kembali_pinjaman = passdata + (passdata*20/100);

            assert mPinjamanStatus != null;
            if(!mPinjamanStatus) {

                mButtonAjukanPinjaman.setEnabled(true);
                mLoading.setAlpha(0);
                mNotifLoad.setVisibility(View.VISIBLE);

                Map<String, Object> data = new HashMap<>();
                data.put("id_pinjaman", mIDPinjaman);
                data.put("id_peminjam", mUserID);
                data.put("pendanaan_status", false);
                data.put("pendanaan_req", false);
                data.put("pendanaan_submit", true);

                data.put("pinjaman_status", "MENUNGGU VERIFIKASI");

                data.put("pinjaman_tanggal_req", mCurrentDate);

                data.put("pinjaman_tanggal_cair", null); //NULL [mCurrentDate]
                data.put("pinjaman_tanggal_transfer", null);
                data.put("pinjaman_tahap", 0);

                data.put("pinjaman_tanggal_bayar_1", null);
                data.put("pinjaman_tanggal_bayar_2", null);
                data.put("pinjaman_tanggal_bayar_3", null);

                data.put("pinjaman_besar", passdata);
                data.put("pinjaman_total", total_kembali_pinjaman);

                data.put("pinjaman_terbayar", 0); // akumulasi transfer
                data.put("pinjaman_terbayar_pendana", 0); // akumulasi transfer ke pendana
                data.put("pinjaman_denda_total", 0); // akumulasi denda

                data.put("pinjaman_denda", 0); // denda
                data.put("pinjaman_transfer", 0); // denda + cicilan
                data.put("pinjaman_transfer_pendana", 0); // denda + cicilan ke pendana
                data.put("pinjaman_lunas", false);

                mFire.collection("PEMINJAM").document(mIDPinjaman).set(data);

                mDocRef.update("pinjaman_status", true);
                mDocRef.update("pinjaman_aktif", mIDPinjaman);

                //-------------------------------------------------------------------------------------------//

                // set notif
                Map<String, Object> dataNotif = new HashMap<>();
                dataNotif.put("id_user", mUserID);
                dataNotif.put("notif_type", true);
                dataNotif.put("notif_title", "Pengajuan Pinjaman Diproses!");
                dataNotif.put("notif_detail", "Sedang menunggu verifikasi Admin.");
                dataNotif.put("notif_date", mCurrentDate);
                mFire.collection("NOTIFIKASI").document(mIDNotif).set(dataNotif);

            } else {
                Toast.makeText(PeminjamReqPinjamanActivity.this,
                        "ANDA MEMILIKI PINJAMAN AKTIF!", Toast.LENGTH_SHORT).show();
                mButtonAjukanPinjaman.setEnabled(true);
                mLoading.setAlpha(0);
            }

        }, 1000);

    }

    //-------------------------------------------------------------------------------------------//

    public void ButtonBackArrow(View view) {
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
