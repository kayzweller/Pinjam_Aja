package com.xoxltn.pinjam_aja;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.xoxltn.pinjam_aja.peminjam_system.PeminjamFundFragment;
import com.xoxltn.pinjam_aja.peminjam_system.PeminjamHomeFragment;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Date;
import java.util.Objects;

public class PembayaranPeminjamActivity extends AppCompatActivity {

    private String passdata;

    private NumberFormat formatter;

    private DocumentReference mDocRef;

    private TextView mIDPinjaman;

    private String mSetIDPinjaman;
    private Date mTanggalCair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peminjam_pembayaran);

        FormatRupiah(); //FORMATTING TO RUPIAH //

        // TODO : OBJECT LINKING ARE HERE!! WATCH IT.
        mIDPinjaman = findViewById(R.id.id_pinjaman_bayar);

        // TODO :: GET DATA SENT FROM INTENT
        Bundle extras = this.getIntent().getExtras();
        passdata = Objects.requireNonNull(extras)
                .getString(PeminjamFundFragment.EXTRA_ID);

        // TODO : FIREBASE ARE HERE YOU DUMBASS!!!
        FirebaseFirestore fire = FirebaseFirestore.getInstance();

        mDocRef = fire.collection("PEMINJAM").document(passdata);

        callTotalBayar();

    }

    //-------------------------------------------------------------------------------------------//

    private void callTotalBayar() {

        mDocRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc =  Objects.requireNonNull(task.getResult());
                    mTanggalCair = doc.getDate("pinjaman_tanggal_cair");
                }
            }
        });

        mDocRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc =  Objects.requireNonNull(task.getResult());
                    Long done = doc.getLong("pinjaman_cicilan");

                    if (done != null && mTanggalCair != null) {
                        // TODO : SET VALUE HERE!!
                        mSetIDPinjaman = "Nominal Transfer : " + formatter.format(done);
                        ;
                        mIDPinjaman.setText(mSetIDPinjaman);
                    } else {
                        mSetIDPinjaman = "Nominal Transfer : Rp0";
                        mIDPinjaman.setText(mSetIDPinjaman);
                    }

                }
            }
        });

    }

    //-------------------------------------------------------------------------------------------//

    public void onCLickKonfimasiBayar(View view) {

        if (!passdata.equals("0") && mTanggalCair != null) {
            //TODO :: BUAT KONFIRMASI BAYAR
            mDocRef.update("pinjaman_status_pembayaran", "MENUNGGU KONFIRMASI")
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(PembayaranPeminjamActivity.this,
                                    "Permintaan konfirmasi transfer terkirim!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(PembayaranPeminjamActivity.this,
                    "TIDAK ADA PINJAMAN AKTIF!",
                    Toast.LENGTH_SHORT).show();
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
