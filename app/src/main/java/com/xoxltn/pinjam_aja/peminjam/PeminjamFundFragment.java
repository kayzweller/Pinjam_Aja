/*
 * Created by Albert Kristaen (Kayzweller) on 25/06/20 02.05
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 23/06/20 13.19
 */

package com.xoxltn.pinjam_aja.peminjam;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class PeminjamFundFragment extends Fragment {

    public static final String EXTRA_ID = "com.xoxltn.pinjam_aja.EXTRA_IDS";
    public static final String USER_ID = "com.xoxltn.pinjam_aja.USER_ID";

    private View mView;

    private DocumentReference mDocRef, mDocRef2;
    private FirebaseFirestore mFire;
    private String mUserID;

    private TextView mTextIDPinjaman, mTextStatusPinjaman, mTextStatusPembayaran, mTextTglDanaCair;
    private TextView mTextTenorPinjaman, mTextPinjaman, mTextTotalBayarPinjaman, mTextTerbayarPinjaman;
    private TextView mTextDendaPinjaman, mTextTglJatuhTempo;
    private TextView mTextTotalBayarTransfer, mTextTahapPinjaman;

    private String mIDPinjaman = "0";
    private String mStatusPinjaman, mStatusPembayaran, mTglDanaCair, mTenorPinjaman, mPinjaman, mBayarPinjaman;
    private String mTerbayarPinjaman, mDendaPinjaman,mTglJatuhTempo;
    private String mTahapPinjaman, mTotalTransfer;

    private Date mCurrentDate;
    private Date mTanggalBayar;

    private Long mTahap;

    private double mNomPinjaman = 0;
    private double mNomKembaliPinjaman = 0;
    private double mNomKembaliPendana = 0;

    private double mNomTransfer = 0;
    private double mNomTransferPendana = 0;
    private double mNomDenda = 0;
    private double mNomDendaPendana = 0;
    private double mTotalBayarTransfer = 0;
    private double mTotalBayarPendana = 0;

    private long mHariTelat = 0;
    private long mMasaTenor = 0;

    private String mIDPinjamanTransfer = "0";

    private NumberFormat formatter;

    //------------------------------------------------------------------------------------------//

    public PeminjamFundFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_peminjam_fund, container, false);

        mCurrentDate = Calendar.getInstance().getTime();

        mTextIDPinjaman = mView.findViewById(R.id.text_IDPinjaman);
        mTextStatusPinjaman = mView.findViewById(R.id.text_StatusPinjaman);
        mTextStatusPembayaran = mView.findViewById(R.id.text_StatusPembayaran);
        mTextTglDanaCair = mView.findViewById(R.id.text_TglDanaCair);
        mTextTenorPinjaman = mView.findViewById(R.id.text_TenorPinjaman);
        mTextPinjaman = mView.findViewById(R.id.text_Pinjaman);
        mTextTotalBayarPinjaman = mView.findViewById(R.id.text_TotalBayarPinjaman);
        mTextTerbayarPinjaman = mView.findViewById(R.id.text_TerbayarPinjaman);
        mTextDendaPinjaman = mView.findViewById(R.id.text_DendaPinjaman);
        mTextTglJatuhTempo = mView.findViewById(R.id.text_TglJatuhTempo);
        mTextTotalBayarTransfer = mView.findViewById(R.id.text_TotalBayarTransfer);
        mTextTahapPinjaman = mView.findViewById(R.id.text_tahapPinjaman);

        formatRupiah(); // FORMATTING TO RUPIAH //

        initPinjamanInfo();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mFire = FirebaseFirestore.getInstance();

        mUserID = mAuth.getUid();
        mDocRef = mFire.collection("USER").document(mUserID);
        mDocRef2 = mFire.collection("PEMINJAM").document(mIDPinjaman);

        callIDPinjaman(); // LOAD ID PINJAMAN [THIS ONE MUST LOAD FIRST] //
        loadPinjamanInfo(); // LOAD DATA INFO DARI FIRESTORE //
        denda_TotalPinjaman(); // KALKULASI DENDA + TOTAL BAYAR //

        ButtonBayarCicilanClick();

        return mView;
    }

    //------------------------------------------------------------------------------------------//

    private void formatRupiah() {
        formatter = NumberFormat.getCurrencyInstance();
        formatter.setMaximumFractionDigits(0);
        formatter.setCurrency(Currency.getInstance("IDR"));
    }

    //------------------------------------------------------------------------------------------//

    @Override
    public void onResume() {
        super.onResume();
        loadPrevData();
    }

    private void initPinjamanInfo() {
        mStatusPinjaman = "--";
        mStatusPembayaran = "--";
        mTglDanaCair = "--";
        mTenorPinjaman = "0 Hari";
        mPinjaman = "Rp0";
        mBayarPinjaman = "Rp0";
        mTerbayarPinjaman = "Rp0";
        mDendaPinjaman = "Rp0";
        mTglJatuhTempo = "--";
        mTotalTransfer = "--";
        mTahapPinjaman = "--";
    }

    //------------------------------------------------------------------------------------------//

    private void loadPinjamanInfo() {

        Handler delay2 = new Handler();
        delay2.postDelayed(() -> {

            CallStatusPinjaman();
            CallTglDanaCair();
            CallTenorPinjaman();
            CallPinjaman();
            CallTotalBayarPinjaman();
            CallTerbayarPinjaman();
            CallTglJatuhTempo();

        }, 888);
    }

    private void loadPrevData() {
        Handler delay1 = new Handler();
        delay1.postDelayed(() -> {

            mTextIDPinjaman.setText(mIDPinjaman); //id_pinjaman
            mTextStatusPinjaman.setText(mStatusPinjaman); //id_pinjaman
            mTextStatusPembayaran.setText(mStatusPembayaran); //id_pinjaman
            mTextTglDanaCair.setText(mTglDanaCair); //tanggal pinjaman disetujui admin
            mTextTenorPinjaman.setText(mTenorPinjaman); //sisa waktu pinjaman
            mTextPinjaman.setText(mPinjaman); //opsi pinjaman
            mTextTotalBayarPinjaman.setText(mBayarPinjaman); //total harus bayar
            mTextTerbayarPinjaman.setText(mTerbayarPinjaman); //cicilan terbayar
            mTextDendaPinjaman.setText(mDendaPinjaman); //denda pinjaman
            mTextTglJatuhTempo.setText(mTglJatuhTempo); //tanggal pembayaran cicilan
            mTextTotalBayarTransfer.setText(mTotalTransfer); //total transfer cicilan
            mTextTahapPinjaman.setText(mTahapPinjaman); //tahap pinjaman

        }, 7);
    }

    //------------------------------------------------------------------------------------------//

    private void denda_TotalPinjaman() {

        Handler delayIt = new Handler();
        delayIt.postDelayed(() -> {

            mNomKembaliPinjaman = mNomPinjaman/100*20;
            mNomTransfer = (mNomPinjaman + mNomKembaliPinjaman) / 3;

            mNomKembaliPendana = mNomPinjaman/100*15;
            mNomTransferPendana = Math.round((mNomPinjaman + mNomKembaliPendana) / 3);

            double denda_harian = (mNomPinjaman + mNomKembaliPinjaman)/1000*8;
            double denda_harian_pendana = (mNomPinjaman + mNomKembaliPendana)/1000*8;

            if (mHariTelat > 0) {
                mNomDenda = mHariTelat * (denda_harian);
                mNomDendaPendana = mHariTelat * (denda_harian_pendana);
            } else {
                mNomDenda = 0;
                mNomDendaPendana = 0;
            }

            if (mNomDenda <= mNomTransfer) {
                mTotalBayarTransfer = mNomDenda + mNomTransfer;
                mTotalBayarPendana = mNomDendaPendana + mNomTransferPendana;
            } if (mNomDenda > mNomTransfer) {
                mTotalBayarTransfer = mNomTransfer * 2;
                mNomDenda = mNomTransfer;
                mTotalBayarPendana = mNomTransferPendana * 2;
                mNomDendaPendana = mNomTransferPendana;
            }

            mTotalBayarTransfer = mNomDenda + mNomTransfer;
            mTotalBayarPendana = mNomDendaPendana + mNomTransferPendana;

            Log.e("TRANSFER", String.valueOf(mTotalBayarPendana));

            if (!mTglDanaCair.equals("--")) {
                mTotalTransfer = formatter.format(mTotalBayarTransfer);
            } else {
                mTotalTransfer = "--";
            }
            mTextTotalBayarTransfer.setText(mTotalTransfer);

            String loadDenda = formatter.format(mNomDenda);
            mTextDendaPinjaman.setText(loadDenda);

        }, 1600);

    }

    //------------------------------------------------------------------------------------------//

    private void callIDPinjaman() {
        mDocRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot doc =  Objects.requireNonNull(task.getResult());
                String done = doc.getString("pinjaman_aktif");
                if (done != null) {
                    mIDPinjaman = done;
                    mIDPinjamanTransfer = done; // PREPARE ID PINJAMAN
                    mTextIDPinjaman.setText(mIDPinjaman); //id_pinjaman
                    mDocRef2 = mFire.collection("PEMINJAM").document(mIDPinjaman);
                }

                if (done != null && done.equals("0")) {
                    mTextIDPinjaman.setText("--");
                }
            }
        });
    }

    //------------------------------------------------------------------------------------------//

    private void CallStatusPinjaman() {
        mDocRef2.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot doc =  Objects.requireNonNull(task.getResult());
                String done = doc.getString("pinjaman_status");
                String done2 = doc.getString("pinjaman_status_pembayaran");
                if (done != null) {
                    mStatusPinjaman = done;
                    mTextStatusPinjaman.setText(mStatusPinjaman); //status_pinjaman
                }

                //status_pembayaran
                if (done2 != null) {
                    mStatusPembayaran = done2;
                } else {
                    mStatusPembayaran = "--";
                }
                mTextStatusPembayaran.setText(mStatusPembayaran);
            }
        });
    }

    private void CallTglDanaCair() {
        mDocRef2.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot doc =  Objects.requireNonNull(task.getResult());
                Date done = doc.getDate("pinjaman_tanggal_cair");
                //tanggal pinjaman disetujui admin
                if (done != null) {
                    mTglDanaCair = DateFormat.getDateInstance(DateFormat.FULL).format(done);
                } else {
                    mTglDanaCair = "--";
                }
                mTextTglDanaCair.setText(mTglDanaCair); //tanggal pinjaman disetujui admin
            }
        });
    }

    private void CallTenorPinjaman() {
        mDocRef2.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot doc =  Objects.requireNonNull(task.getResult());
                Date done = doc.getDate("pinjaman_tanggal_bayar_3");
                //sisa waktu pinjaman
                if (done != null) {
                    long diff = done.getTime() - mCurrentDate.getTime();
                    mMasaTenor = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

                    mTenorPinjaman = mMasaTenor + " Hari";
                } else {
                    mTenorPinjaman = " 0 Hari";
                }
                mTextTenorPinjaman.setText(mTenorPinjaman); //sisa waktu pinjaman
            }
        });
    }

    private void CallPinjaman() {
        mDocRef2.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot doc =  Objects.requireNonNull(task.getResult());
                Long done = doc.getLong("pinjaman_besar");
                if (done != null) {

                    mNomPinjaman = done;

                    mPinjaman = formatter.format(done);
                    mTextPinjaman.setText(mPinjaman); //opsi pinjaman
                }
            }
        });
    }

    private void CallTotalBayarPinjaman() {
        mDocRef2.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot doc =  Objects.requireNonNull(task.getResult());
                Long done = doc.getLong("pinjaman_total");
                if (done != null) {
                    mBayarPinjaman = formatter.format(done);
                    mTextTotalBayarPinjaman.setText(mBayarPinjaman); //total harus bayar
                }
            }
        });
    }

    private void CallTerbayarPinjaman() {
        mDocRef2.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot doc =  Objects.requireNonNull(task.getResult());
                Long done = doc.getLong("pinjaman_terbayar");
                if (done != null) {
                    mTerbayarPinjaman = formatter.format(done);
                    mTextTerbayarPinjaman.setText(mTerbayarPinjaman); //cicilan terbayar
                }
            }
        });
    }

    private void CallTglJatuhTempo() {
        mDocRef2.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot doc =  Objects.requireNonNull(task.getResult());
                Long load = doc.getLong("pinjaman_tahap");

                mTahap = doc.getLong("pinjaman_tahap");

                if (load != null) {
                    if (load == 0) {
                        mTglJatuhTempo = "--";
                        mTextTglJatuhTempo.setText(mTglJatuhTempo);
                        mTextTahapPinjaman.setText(mTglJatuhTempo);
                    } else if (load == 1) {
                        mTahapPinjaman = "Cicilan-1";
                        mTextTahapPinjaman.setText(mTahapPinjaman);

                        Date date1 = doc.getDate("pinjaman_tanggal_bayar_1");
                        mTanggalBayar = date1;
                        if (date1 != null) {
                            mTglJatuhTempo = DateFormat.getDateInstance
                                    (DateFormat.FULL).format(date1);
                            mTextTglJatuhTempo.setText(mTglJatuhTempo);
                        }
                    } else if (load == 2) {
                        mTahapPinjaman = "Cicilan-2";
                        mTextTahapPinjaman.setText(mTahapPinjaman);

                        Date date2 = doc.getDate("pinjaman_tanggal_bayar_2");
                        mTanggalBayar = date2;
                        if (date2 != null) {
                            mTglJatuhTempo = DateFormat.getDateInstance
                                    (DateFormat.FULL).format(date2);
                            mTextTglJatuhTempo.setText(mTglJatuhTempo);
                        }
                    } else if (load == 3) {
                        mTahapPinjaman = "Cicilan-3";
                        mTextTahapPinjaman.setText(mTahapPinjaman);

                        Date date3 = doc.getDate("pinjaman_tanggal_bayar_3");
                        mTanggalBayar = date3;
                        if (date3 != null) {
                            mTglJatuhTempo = DateFormat.getDateInstance
                                    (DateFormat.FULL).format(date3);
                            mTextTglJatuhTempo.setText(mTglJatuhTempo);
                        }
                    }
                } else {
                    mTglJatuhTempo = "--";
                    mTextTglJatuhTempo.setText(mTglJatuhTempo);
                    mTextTahapPinjaman.setText(mTglJatuhTempo);
                }

                // kalkulasi hari telat bayar
                if (mTanggalBayar != null) {
                    long differ = mCurrentDate.getTime() - mTanggalBayar.getTime();
                    if (differ > 0) {
                        mHariTelat = TimeUnit.DAYS.convert(differ, TimeUnit.MILLISECONDS);
                    }
                    else {
                        mHariTelat = 0;
                    }
                }

                Log.e("Telat Pembayaran : ", mHariTelat + " Hari");

                denda_TotalPinjaman();

            }
        });
    }

    //------------------------------------------------------------------------------------------//

    private void ButtonBayarCicilanClick() {

        Button buttonBayarCicilan = mView.findViewById(R.id.button_BayarCicilan);
        buttonBayarCicilan.setOnClickListener(view -> {

            if (!mIDPinjamanTransfer.equals("0") && !mTglDanaCair.equals("--")) {

                if (mTahap == 1 || mTahap == 2 || mTahap == 3) {
                    mDocRef2.update("pinjaman_transfer", mTotalBayarTransfer);
                    mDocRef2.update("pinjaman_transfer_pendana", mTotalBayarPendana);
                    mDocRef2.update("pinjaman_denda", mNomDenda);

                    Log.e("Nominal Denda : ", "Rp. " + mNomDenda);

                    //SENT DATA THROUGH EXTRA INTENT
                    Intent goToBayar = new Intent(getActivity(), PeminjamPembayaranActivity.class);
                    goToBayar.putExtra(EXTRA_ID, mIDPinjamanTransfer);
                    goToBayar.putExtra(USER_ID, mUserID);
                    startActivity(goToBayar);

                }

            } else {
                Toast.makeText(getActivity(), "ANDA BELUM MEMILIKI PINJAMAN AKTIF!",
                        Toast.LENGTH_SHORT).show();
            }

        });

    }

}
