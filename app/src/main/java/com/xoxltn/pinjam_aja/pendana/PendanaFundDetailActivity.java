/*
 * Created by Albert Kristaen (Kayzweller) on 25/06/20 02.04
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 24/06/20 22.34
 */

package com.xoxltn.pinjam_aja.pendana;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.xoxltn.pinjam_aja.R;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class PendanaFundDetailActivity extends AppCompatActivity {

    private String mDocPath;
    private String id_pinjaman;

    private TextView mTextIDPinjaman, mTextTglPendanaan, mTextFundStatus, mTahapPinjam,
            mTextJatuhTempo, mTextTenor, mTextBesarPinjam, mTextTotalKembali, mTextKembali;

    private Date mCurrentDate;
    private NumberFormat formatter;

    //-------------------------------------------------------------------------------------------//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pendana_fund_detail);

        mCurrentDate = Calendar.getInstance().getTime();

        formatRupiah(); //FORMATTING TO RUPIAH //

        // call data sent trought intent~
        Bundle extras = this.getIntent().getExtras(); assert extras != null;
        id_pinjaman = extras.getString(PendanaFundFragment.SEND_DOC);
        mDocPath = extras.getString(PendanaFundFragment.SEND_PATH);

        // CALL THE HOOKS
        mTextIDPinjaman = findViewById(R.id.fund_id_pinjaman);
        mTextTglPendanaan = findViewById(R.id.fund_tgl_pendanaan);
        mTextFundStatus = findViewById(R.id.fund_status);
        mTahapPinjam = findViewById(R.id.fund_tahap);
        mTextJatuhTempo = findViewById(R.id.fund_tgl_tempo);
        mTextTenor = findViewById(R.id.fund_sisa_tenor);
        mTextBesarPinjam = findViewById(R.id.fund_besar_pinjaman);
        mTextTotalKembali = findViewById(R.id.fund_total_kembali);
        mTextKembali = findViewById(R.id.fund_dana_kembali);

        loadSetsDataPinjaman();
    }

    //-------------------------------------------------------------------------------------------//

    private void loadSetsDataPinjaman() {
        FirebaseFirestore mFire = FirebaseFirestore.getInstance();
        mFire.document(mDocPath).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc =  Objects.requireNonNull(task.getResult());

                    // PULL DATA FROM FIRESTORE HERE!!
                    Date date_cair = doc.getDate("pinjaman_tanggal_cair");
                    String status = doc.getString("pinjaman_status");
                    Date tenor = doc.getDate("pinjaman_tanggal_bayar_3");
                    Long tahap = doc.getLong("pinjaman_tahap");
                    int pinjam = Math.toIntExact(doc.getLong("pinjaman_besar"));
                    int kembali = Math.toIntExact(doc.getLong("pinjaman_terbayar_pendana"));

                    // PROCESS DATA + SET TEXT VIEW BELOW!!
                    // LOOK THE SPAGETTI CODE HERE BELOW POINT [1] to [8]

                    //[1]
                    mTextIDPinjaman.setText(id_pinjaman);

                    //[2]
                    if (date_cair == null) {
                        String sets = "--";
                        mTextTglPendanaan.setText(sets);
                    } else {
                        String sets = DateFormat.getDateInstance(DateFormat.FULL).format(date_cair);
                        mTextTglPendanaan.setText(sets);
                    }

                    //[3]
                    mTextFundStatus.setText(status);

                    //[4]
                    if (tenor != null) {
                        long diff = tenor.getTime() - mCurrentDate.getTime();
                        long day_count = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

                        String tenor_set = day_count + " Hari";
                        mTextTenor.setText(tenor_set); //sisa waktu pinjaman
                    } else {
                        String tenor_set = "0 Hari";
                        mTextTenor.setText(tenor_set); //tanggal pembayaran cicilan
                    }

                    //[5] -- pinjaman lunas? sets it to "0"
                    if (tahap != null) {
                        if (tahap == 0) {
                            String sets0 = "--";
                            mTahapPinjam.setText(sets0);
                            mTextJatuhTempo.setText(sets0);
                        } else if (tahap == 1) {
                            String sets1 = "Cicilan Pertama";
                            mTahapPinjam.setText(sets1);
                            Date date1 = doc.getDate("pinjaman_tanggal_bayar_1");
                            if (date1 != null) {
                                mTextJatuhTempo.setText(DateFormat.getDateInstance(DateFormat.FULL)
                                        .format(date1));
                            }
                        } else if (tahap == 2) {
                            String sets2 = "Cicilan Kedua";
                            mTahapPinjam.setText(sets2);
                            Date date2 = doc.getDate("pinjaman_tanggal_bayar_2");
                            if (date2 != null) {
                                mTextJatuhTempo.setText(DateFormat.getDateInstance(DateFormat.FULL)
                                        .format(date2));
                            }
                        } else if (tahap == 3) {
                            String sets3 = "Cicilan Terakhir";
                            mTahapPinjam.setText(sets3);
                            Date date3 = doc.getDate("pinjaman_tanggal_bayar_3");
                            if (date3 != null) {
                                mTextJatuhTempo.setText(DateFormat.getDateInstance(DateFormat.FULL)
                                        .format(date3));
                            }
                        }
                    } else {
                        String sets0 = "--";
                        mTahapPinjam.setText(sets0);
                        mTextJatuhTempo.setText(sets0);
                    }

                    //[6]
                    String pinjam_form =  formatter.format(pinjam);
                    mTextBesarPinjam.setText(pinjam_form);

                    //[7]
                    int total_kembali = pinjam + (pinjam /100*15);
                    String pinjam_kembali =  formatter.format(total_kembali);
                    mTextTotalKembali.setText(pinjam_kembali);

                    //[8]
                    String kembali_form = formatter.format(kembali);
                    mTextKembali.setText(kembali_form);
                }
            }
        });
    }

    //-------------------------------------------------------------------------------------------//

    private void formatRupiah() {
        formatter = NumberFormat.getCurrencyInstance();
        formatter.setMaximumFractionDigits(0);
        formatter.setCurrency(Currency.getInstance("IDR"));
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