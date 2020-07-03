/*
 * Created by Albert Kristaen (Kayzweller) on 25/06/20 02.05
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 23/06/20 13.19
 */

package com.xoxltn.pinjam_aja.peminjam;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.xoxltn.pinjam_aja.R;

import java.text.NumberFormat;
import java.util.*;

public class PeminjamPembayaranActivity extends AppCompatActivity {

    private String passdata, mUserID;

    // set for notification
    private FirebaseFirestore mFire;
    private String mIDNotif;

    private NumberFormat formatter;
    private DocumentReference mDocRef;

    private TextView mIDPinjaman, mBayarPinjaman;
    private String mSetIDPinjaman, mSetBayarPinjaman;
    private Date mTanggalCair;
    private Date mCurrentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peminjam_pembayaran);

        mCurrentDate = Calendar.getInstance().getTime();

        FormatRupiah(); //FORMATTING TO RUPIAH //

        //  OBJECT LINKING ARE HERE!! WATCH IT.
        mIDPinjaman = findViewById(R.id.kode_pinjaman);
        mBayarPinjaman = findViewById(R.id.pinjaman_bayar);

        // GET DATA SENT FROM INTENT
        Bundle extras = this.getIntent().getExtras(); assert extras != null;
        passdata = extras.getString(PeminjamFundFragment.EXTRA_ID);
        mUserID = extras.getString(PeminjamFundFragment.USER_ID);

        // FIREBASE ARE HERE YOU DUMBASS!!!
        mFire = FirebaseFirestore.getInstance();
        mDocRef = mFire.collection("PEMINJAM").document(passdata);

        //firestore for notification
        mIDNotif = mFire.collection("NOTIFIKASI").document().getId();

        callTotalBayar();
    }

    //-------------------------------------------------------------------------------------------//

    private void callTotalBayar() {

        mDocRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot doc =  Objects.requireNonNull(task.getResult());
                mTanggalCair = doc.getDate("pinjaman_tanggal_cair");
            }
        });

        mDocRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot doc =  Objects.requireNonNull(task.getResult());
                Long done = doc.getLong("pinjaman_transfer");

                if (done != null && mTanggalCair != null) {
                    mSetBayarPinjaman = "Nominal Transfer : " + formatter.format(done);
                    mBayarPinjaman.setText(mSetBayarPinjaman);

                    mSetIDPinjaman = "Kode Pinjaman : " + passdata;
                    mIDPinjaman.setText(mSetIDPinjaman);
                }

            }
        });

    }

    //-------------------------------------------------------------------------------------------//

    public void onCLickKonfimasiBayar(View view) {
        if (!passdata.equals("0") && mTanggalCair != null) {
            mDocRef.update("pinjaman_tanggal_transfer", mCurrentDate);
            mDocRef.update("pinjaman_konfirmasi_pembayaran", false);
            mDocRef.update("pinjaman_status_pembayaran", "MENUNGGU KONFIRMASI")
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(PeminjamPembayaranActivity.this,
                                "Permintaan konfirmasi transfer terkirim!",
                                Toast.LENGTH_SHORT).show();

                        // set the notification
                        Map<String, Object> dataNotif = new HashMap<>();
                        dataNotif.put("id_user", mUserID);
                        dataNotif.put("notif_type", true);
                        dataNotif.put("notif_title", "Pembayaran Cicilan Diproses!");
                        dataNotif.put("notif_detail", "Sedang menunggu konfirmasi Admin. " +
                                "Jika dalam 1x24 jam Admin belum menerima transfer, " +
                                "konfirmasi pembayaran akan dibatalkan.");
                        dataNotif.put("notif_date", mCurrentDate);

                        mFire.collection("NOTIFIKASI").document(mIDNotif).set(dataNotif);

                        finish();

                    });
        } else {
            Toast.makeText(PeminjamPembayaranActivity.this,
                    "ERROR!",
                    Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    //-------------------------------------------------------------------------------------------//

    private void FormatRupiah() {
        formatter = NumberFormat.getCurrencyInstance();
        formatter.setMaximumFractionDigits(0);
        formatter.setCurrency(Currency.getInstance("IDR"));
    }

    //-------------------------------------------------------------------------------------------//

    public void ButtonBackArrow(View v) {
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
