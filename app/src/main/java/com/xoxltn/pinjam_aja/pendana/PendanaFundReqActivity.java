/*
 * Created by Albert Kristaen (s6joxx) on 10/22/20, 11:34 AM
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 6/25/20, 4:11 AM
 */

package com.xoxltn.pinjam_aja.pendana;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.xoxltn.pinjam_aja.R;

import java.text.DateFormat;
import java.util.Date;
import java.util.Objects;

public class PendanaFundReqActivity extends AppCompatActivity {

    public static final String FUND_ID_PATH = "com.xoxltn.pinjam_aja.FUND_ID_PATH";
    public static final String FUND_ID_DOC = "com.xoxltn.pinjam_aja.FUND_ID_DOC";

    private DocumentReference mDocRef;

    private TextView mIDPinjaman, mTanggalPinjaman;
    private TextView mBesarPinjaman, mBesarKembali;
    private String mDocPath, id_pinjaman, id_peminjam;

    private Date mTanggalPengajuan;
    private int mNominal_pinjam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pendana_fund_req);

        // call data sent trought intent~
        Bundle extras = this.getIntent().getExtras(); assert extras != null;
        id_pinjaman = extras.getString(PendanaHomeFragment.SEND_DOCUMENT_DOC);
        mDocPath = extras.getString(PendanaHomeFragment.SEND_DOCUMENT_PATH);

        // FIRESTORE SETS!
        FirebaseFirestore mFire = FirebaseFirestore.getInstance();
        mDocRef = mFire.document(mDocPath);

        // sets hooks!
        mIDPinjaman = findViewById(R.id.id_pengajuan_pinjaman);
        mTanggalPinjaman = findViewById(R.id.tanggal_pengajuan_pinjaman);
        mBesarPinjaman = findViewById(R.id.text_Pinjaman);
        mBesarKembali = findViewById(R.id.text_besar_Kembali);

        calledFromDocument();
    }

    //-------------------------------------------------------------------------------------------//

    private void calledFromDocument() {
        mDocRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot doc =  Objects.requireNonNull(task.getResult());

                mTanggalPengajuan = doc.getDate("pinjaman_tanggal_req");

                id_peminjam = doc.getString("id_peminjam");

                Long call = doc.getLong("pinjaman_besar"); assert call != null;
                mNominal_pinjam = Math.toIntExact(call);

                setTextView();
            }
        });
    }

    private void setTextView() {
        mIDPinjaman.setText(id_pinjaman);
        mTanggalPinjaman.setText(DateFormat.getDateInstance(DateFormat.FULL)
                .format(mTanggalPengajuan));

        switch (mNominal_pinjam) {
            case 500000:
                mBesarPinjaman.setText(R.string.pinjam_500);
                String set1 = "Rp. 575.000,-";
                mBesarKembali.setText(set1);
                break;
            case 1000000:
                mBesarPinjaman.setText(R.string.pinjam_1000);
                String set2 = "Rp. 1.150.000,-";
                mBesarKembali.setText(set2);
                break;
            case 1500000:
                mBesarPinjaman.setText(R.string.pinjam_1500);
                String set3 = "Rp. 1.725.000,-";
                mBesarKembali.setText(set3);
                break;
        }
    }

    //-------------------------------------------------------------------------------------------//

    public void onClickDanaiPinjaman(View view) {
        Intent goToPendanaan = new Intent(this, PendanaPendanaanActivity.class);
        goToPendanaan.putExtra(FUND_ID_PATH, mDocPath);
        goToPendanaan.putExtra(FUND_ID_DOC, id_pinjaman);
        startActivity(goToPendanaan);
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
