/*
 * Created by Albert Kristaen (s6joxx) on 10/22/20, 11:34 AM
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 6/25/20, 4:32 AM
 */

package com.xoxltn.pinjam_aja.pendana;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.xoxltn.pinjam_aja.R;

import java.text.NumberFormat;
import java.util.*;

public class PendanaPendanaanActivity extends AppCompatActivity {

    private NumberFormat formatter;

    private FirebaseFirestore mFire;
    private DocumentReference mDocRef;

    private String passdata, passdata2, mUserID;
    private TextView mIDPinjaman, mPinjamanDana;
    private String mSetIDPinjaman, mSetPendanaan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pendana_pendanaan);

        FormatRupiah(); //FORMATTING INT TO RUPIAH //

        // OBJECT LINKING ARE HERE!! WATCH IT.
        mIDPinjaman = findViewById(R.id.kode_pinjaman);
        mPinjamanDana = findViewById(R.id.pinjaman_dana);

        // GET DATA SENT FROM INTENT
        Bundle extras = this.getIntent().getExtras();
        passdata = Objects.requireNonNull(extras).getString(PendanaFundReqActivity.FUND_ID_PATH);
        passdata2 = Objects.requireNonNull(extras).getString(PendanaFundReqActivity.FUND_ID_DOC);

        // FIREBASE ARE HERE YOU DUMBASS!!!
        mUserID = FirebaseAuth.getInstance().getUid();
        mFire = FirebaseFirestore.getInstance();

        callTotalPendanaan();
    }

    //-------------------------------------------------------------------------------------------//

    private void callTotalPendanaan() {
        mDocRef = mFire.document(passdata);
        mDocRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot doc =  Objects.requireNonNull(task.getResult());
                Long done = doc.getLong("pinjaman_besar");

                if (done != null) {
                    mSetPendanaan = "Nominal Transfer : " + formatter.format(done);
                    mPinjamanDana.setText(mSetPendanaan);

                    mSetIDPinjaman = "Kode Pinjaman : " + passdata2;
                    mIDPinjaman.setText(mSetIDPinjaman);
                }

            }
        });
    }

    //-------------------------------------------------------------------------------------------//

    public void onCLickKonfimasiDanai(View view) {
        mDocRef.update("id_pendana", mUserID);
        mDocRef.update("pendanaan_req", false);
        mDocRef.update("pendanaan_status", true);
        mDocRef.update("pinjaman_aktif", false);
        mDocRef.update("pinjaman_status", "KONFIRMASI PENDANAAN")
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(PendanaPendanaanActivity.this,
                            "Konfirmasi transfer pendanaan terkirim!",
                            Toast.LENGTH_SHORT).show();
                    finish();
                });
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
    //-------------------------------------------------------------------------------------------//

    @Override
    public void onBackPressed() {
        finish();
    }

}
