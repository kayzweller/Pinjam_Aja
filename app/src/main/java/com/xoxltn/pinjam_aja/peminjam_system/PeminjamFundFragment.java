package com.xoxltn.pinjam_aja.peminjam_system;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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

    public static final String EXTRA_ID = "com.xoxltn.pinjam_aja.EXTRA_ID";

    private View mView;

    private DocumentReference mDocRef, mDocRef2;
    private FirebaseFirestore mFire;

    private TextView mTextIDPinjaman, mTextStatusPinjaman, mTextTglDanaCair, mTextTenorPinjaman;
    private TextView mTextPinjaman, mTextTotalBayarPinjaman, mTextTerbayarPinjaman;
    private TextView mTextDendaPinjaman, mTextTglJatuhTempo, mTextStatusPembayaran;
    private TextView mTextTotalBayarCicilan, mTextTahapPinjaman;

    private String mIDPinjaman = "0";
    private String mStatusPinjaman, mTglDanaCair, mTenorPinjaman, mPinjaman, mBayarPinjaman;
    private String mTerbayarPinjaman, mDendaPinjaman,mTglJatuhTempo, mStatusPembayaran;
    private String mTahapPinjaman, mTotalCicilan;

    private Date mCurrentDate;

    private double mNomPinjaman = 0;
    private double mNomKembaliPinjaman = 0;

    private double mNomCicilan = 0;
    private double mNomDenda = 0;
    private double mTotalBayarCicilan = 0;

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
        mTextTglDanaCair = mView.findViewById(R.id.text_TglDanaCair);
        mTextTenorPinjaman = mView.findViewById(R.id.text_TenorPinjaman);
        mTextPinjaman = mView.findViewById(R.id.text_Pinjaman);
        mTextTotalBayarPinjaman = mView.findViewById(R.id.text_TotalBayarPinjaman);
        mTextTerbayarPinjaman = mView.findViewById(R.id.text_TerbayarPinjaman);
        mTextDendaPinjaman = mView.findViewById(R.id.text_DendaPinjaman);
        mTextTglJatuhTempo = mView.findViewById(R.id.text_TglJatuhTempo);
        mTextStatusPembayaran = mView.findViewById(R.id.text_StatusPembayaran);
        mTextTotalBayarCicilan = mView.findViewById(R.id.text_TotalBayarCicilan);
        mTextTahapPinjaman = mView.findViewById(R.id.text_tahapPinjaman);

        formatRupiah(); //FORMATTING TO RUPIAH //

        initPinjamanInfo();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mFire = FirebaseFirestore.getInstance();

        String userID = mAuth.getUid(); assert userID != null;
        mDocRef = mFire.collection("USER").document(userID);
        mDocRef2 = mFire.collection("PEMINJAM").document(mIDPinjaman);

        callIDPinjaman();

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
        CallStatusPembayaran();
    }

    private void initPinjamanInfo() {
        mStatusPinjaman = "--";
        mTglDanaCair = "--";
        mTenorPinjaman = "0 Hari";
        mPinjaman = "Rp0";
        mBayarPinjaman = "Rp0";
        mTerbayarPinjaman = "Rp0";
        mDendaPinjaman = "Rp0";
        mTglJatuhTempo = "--";
        mStatusPembayaran = "--";
        mTotalCicilan = "--";
        mTahapPinjaman = "--";
    }

    //------------------------------------------------------------------------------------------//

    private void loadPinjamanInfo() {

        Handler delay2 = new Handler();
        delay2.postDelayed(new Runnable() {
            @Override
            public void run() {

                CallStatusPinjaman();
                CallTglDanaCair();
                CallTenorPinjaman();
                CallPinjaman();
                CallTotalBayarPinjaman();
                CallTerbayarPinjaman();
                CallTglJatuhTempo();
                CallStatusPembayaran();

            }
        }, 888);
    }

    private void loadPrevData() {
        Handler delay1 = new Handler();
        delay1.postDelayed(new Runnable() {
            @Override
            public void run() {

                mTextIDPinjaman.setText(mIDPinjaman); //id_pinjaman
                mTextStatusPinjaman.setText(mStatusPinjaman); //id_pinjaman
                mTextTglDanaCair.setText(mTglDanaCair); //tanggal pinjaman disetujui admin
                mTextTenorPinjaman.setText(mTenorPinjaman); //sisa waktu pinjaman
                mTextPinjaman.setText(mPinjaman); //opsi pinjaman
                mTextTotalBayarPinjaman.setText(mBayarPinjaman); //total harus bayar
                mTextTerbayarPinjaman.setText(mTerbayarPinjaman); //cicilan terbayar
                mTextDendaPinjaman.setText(mDendaPinjaman); //denda pinjaman
                mTextTglJatuhTempo.setText(mTglJatuhTempo); //tanggal pembayaran cicilan
                mTextStatusPembayaran.setText(mStatusPembayaran); //status pembayaran cicilan
                mTextTotalBayarCicilan.setText(mTotalCicilan); //total bayar cicilan
                mTextTahapPinjaman.setText(mTahapPinjaman); //tahap pinjaman

            }
        }, 7);
    }

    //------------------------------------------------------------------------------------------//

    private void denda_TotalPinjaman() {

        Handler delayIt = new Handler();
        delayIt.postDelayed(new Runnable() {
            @Override
            public void run() {

                mNomKembaliPinjaman = mNomPinjaman/100*20;
                mNomCicilan = (mNomPinjaman + mNomKembaliPinjaman) / 3;

                double denda_harian = (mNomPinjaman + mNomKembaliPinjaman)/1000*8;

                if (mHariTelat > 0) {
                    mNomDenda = mHariTelat * (denda_harian);
                } else {
                    mNomDenda = 0;
                }

                mTotalBayarCicilan = mNomDenda + mNomCicilan;

                if (!mTglDanaCair.equals("--")) {
                    String loadCicilan = formatter.format(mTotalBayarCicilan);
                    mTextTotalBayarCicilan.setText(loadCicilan);
                } else {
                    String loadCicilan = "--";
                    mTextTotalBayarCicilan.setText(loadCicilan);
                }

                String loadDenda = formatter.format(mNomDenda);
                mTextDendaPinjaman.setText(loadDenda);

            }
        }, 1600);

    }

    //------------------------------------------------------------------------------------------//

    private void callIDPinjaman() {
        mDocRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc =  Objects.requireNonNull(task.getResult());
                    String done = doc.getString("pinjaman_aktif");
                    if (done != null) {
                        mIDPinjaman = done;
                        mTextIDPinjaman.setText(mIDPinjaman); //id_pinjaman
                        mDocRef2 = mFire.collection("PEMINJAM").document(mIDPinjaman);
                        mIDPinjamanTransfer = done; // PREPARE ID PINJAMAN
                    }

                    if (done != null && done.equals("0")) {
                        mTextIDPinjaman.setText("--");
                    }
                }
            }
        });
    }

    //------------------------------------------------------------------------------------------//

    private void CallStatusPinjaman() {
        mDocRef2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc =  Objects.requireNonNull(task.getResult());
                    String done = doc.getString("pinjaman_status");
                    if (done != null) {
                        mStatusPinjaman = done;
                        mTextStatusPinjaman.setText(mStatusPinjaman); //id_pinjaman
                    }
                }
            }
        });
    }

    private void CallTglDanaCair() {
        mDocRef2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc =  Objects.requireNonNull(task.getResult());
                    Date done = doc.getDate("pinjaman_tanggal_cair");
                    if (done != null) {
                        mTglDanaCair = DateFormat.getDateInstance(DateFormat.FULL).format(done);
                        mTextTglDanaCair.setText(mTglDanaCair); //tanggal pinjaman disetujui admin
                    } else {
                        mTglDanaCair = "--";
                        mTextTglDanaCair.setText(mTglDanaCair); //tanggal pinjaman disetujui admin
                    }
                }
            }
        });
    }

    private void CallTenorPinjaman() {
        mDocRef2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc =  Objects.requireNonNull(task.getResult());
                    Date done = doc.getDate("pinjaman_tanggal_bayar_3");
                    if (done != null) {
                        long diff = done.getTime() - mCurrentDate.getTime();
                        mMasaTenor = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

                        mTenorPinjaman = mMasaTenor + " Hari";
                        mTextTenorPinjaman.setText(mTenorPinjaman); //sisa waktu pinjaman
                    } else {
                        mTenorPinjaman = " 0 Hari";
                        mTextTenorPinjaman.setText(mTenorPinjaman); //sisa waktu pinjaman
                    }
                }
            }
        });
    }

    private void CallPinjaman() {
        mDocRef2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc =  Objects.requireNonNull(task.getResult());
                    Long done = doc.getLong("pinjaman_besar");
                    if (done != null) {

                        mNomPinjaman = done;

                        mPinjaman = formatter.format(done);
                        mTextPinjaman.setText(mPinjaman); //opsi pinjaman
                    }
                }
            }
        });
    }

    private void CallTotalBayarPinjaman() {
        mDocRef2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc =  Objects.requireNonNull(task.getResult());
                    Long done = doc.getLong("pinjaman_total");
                    if (done != null) {
                        mBayarPinjaman = formatter.format(done);
                        mTextTotalBayarPinjaman.setText(mBayarPinjaman); //total harus bayar
                    }
                }
            }
        });
    }

    private void CallTerbayarPinjaman() {
        mDocRef2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc =  Objects.requireNonNull(task.getResult());
                    Long done = doc.getLong("pinjaman_terbayar");
                    if (done != null) {
                        mTerbayarPinjaman = formatter.format(done);
                        mTextTerbayarPinjaman.setText(mTerbayarPinjaman); //cicilan terbayar
                    }
                }
            }
        });
    }

    private void CallTglJatuhTempo() {
        mDocRef2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc =  Objects.requireNonNull(task.getResult());
                    Long load = doc.getLong("pinjaman_tahap");

                    if (load != null) {
                        if (load == 0) {
                            String date0 = "--";
                            mTextTglJatuhTempo.setText(date0);
                            mTextTahapPinjaman.setText(date0);
                        } else if (load == 1) {
                            String tahap = "Cicilan-1";
                            mTextTahapPinjaman.setText(tahap);

                            Date date1 = doc.getDate("pinjaman_tanggal_bayar_1");
                            if (date1 != null) {
                                mTextTglJatuhTempo.setText(DateFormat.getDateInstance
                                        (DateFormat.FULL).format(date1));
                            }
                        } else if (load == 2) {
                            String tahap = "Cicilan-2";
                            mTextTahapPinjaman.setText(tahap);

                            Date date2 = doc.getDate("pinjaman_tanggal_bayar_2");
                            if (date2 != null) {
                                mTextTglJatuhTempo.setText(DateFormat.getDateInstance
                                        (DateFormat.FULL).format(date2));
                            }
                        } else if (load == 3) {
                            String tahap = "Cicilan-3";
                            mTextTahapPinjaman.setText(tahap);

                            Date date3 = doc.getDate("pinjaman_tanggal_bayar_3");
                            if (date3 != null) {
                                mTextTglJatuhTempo.setText(DateFormat.getDateInstance
                                        (DateFormat.FULL).format(date3));
                            }
                        }
                    } else {
                        String date0 = "--";
                        mTextTglJatuhTempo.setText(date0);
                        mTextTahapPinjaman.setText(date0);
                    }

                }
            }
        });
    }

    private void CallStatusPembayaran() {
        mDocRef2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc =  Objects.requireNonNull(task.getResult());
                    String done = doc.getString("pinjaman_status_pembayaran");
                    if (done != null) {
                        mStatusPembayaran = done;
                        mTextStatusPembayaran.setText(mStatusPembayaran); //status pembayaran cicilan
                    } else {
                        mStatusPembayaran = "--";
                        mTextStatusPembayaran.setText(mStatusPembayaran); //status pembayaran cicilan
                    }
                }
            }
        });
    }

    //------------------------------------------------------------------------------------------//

    private void ButtonBayarCicilanClick() {

        Button buttonBayarCicilan = mView.findViewById(R.id.button_BayarCicilan);
        buttonBayarCicilan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!mIDPinjamanTransfer.equals("0") && !mTglDanaCair.equals("--")) {

                    mDocRef2.update("pinjaman_cicilan", mTotalBayarCicilan);
                    mDocRef2.update("pinjaman_denda", mNomDenda);

                    //SENT DATA THROUGH EXTRA INTENT
                    Intent goToBayar = new Intent(getActivity(), PembayaranPeminjamActivity.class);
                    goToBayar.putExtra(EXTRA_ID, mIDPinjamanTransfer);
                    startActivity(goToBayar);

                } else {
                    Toast.makeText(getActivity(), "ANDA BELUM MEMILIKI PINJAMAN AKTIF!",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

}
