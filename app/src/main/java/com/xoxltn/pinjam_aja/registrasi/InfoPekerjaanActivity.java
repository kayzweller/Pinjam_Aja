/*
 * Created by Albert Kristaen (Kayzweller) on 25/06/20 02.03
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 23/06/20 13.19
 */

package com.xoxltn.pinjam_aja.registrasi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.xoxltn.pinjam_aja.R;

import java.util.Objects;

public class InfoPekerjaanActivity extends AppCompatActivity {

    private DocumentReference mDocRef;
    String mUserID;

    private AutoCompleteTextView mJenisPekerjaan, mPenghasilan;
    private TextInputLayout mNamaInstansi, mAlamatInstansi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_pekerjaan);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore mFire = FirebaseFirestore.getInstance();

        mUserID = mAuth.getUid();
        mDocRef = mFire.collection("USER").document(mUserID);

        mJenisPekerjaan = findViewById(R.id.pekerjaan_jenis);
        mNamaInstansi = findViewById(R.id.pekerjaan_namainstansi);
        mAlamatInstansi = findViewById(R.id.pekerjaan_alamatinstansi);
        mPenghasilan = findViewById(R.id.pekerjaan_penghasilan);


        getListJenisPekerjaan();
        getListPenghasilan();

        loadPreviousData();
    }

    //-------------------------------------------------------------------------------------------//

    private void getListJenisPekerjaan() {
        String[] items = new String[] {
                "Karyawan Perusahaan",
                "Wirawastawan",
                "Mahasiswa"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                InfoPekerjaanActivity.this,
                R.layout.dropdown_item,
                items
        );

        mJenisPekerjaan.setAdapter(adapter);
    }

    private void getListPenghasilan() {
        String[] items = new String[] {
                "Kurang dari Rp. 2.000.000,-",
                "Rp. 2.000.000,- s/d Rp. 3.000.000,-",
                "Rp. 3.000.000,- s/d Rp. 5.000.000,-",
                "Lebih dari Rp. 5.000.000,-"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                InfoPekerjaanActivity.this,
                R.layout.dropdown_item,
                items
        );

        mPenghasilan.setAdapter(adapter);
    }

    //-------------------------------------------------------------------------------------------//

    private void loadPreviousData() {
        loadJenisPekerjaan();
        loadNamaInstansi();
        loadAlamatInstansi();
        loadPenghasilan();
    }

    private void loadJenisPekerjaan() {
        mDocRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc =  Objects.requireNonNull(task.getResult());
                    String load = doc.getString("pekerjaan_jenis");
                    if (mJenisPekerjaan.getText() != null) {
                        mJenisPekerjaan.setText(load);
                    }
                }
            }
        });
    }

    private void loadNamaInstansi() {
        mDocRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc =  Objects.requireNonNull(task.getResult());
                    String load = doc.getString("pekerjaan_namainstansi");
                    if (mNamaInstansi.getEditText() != null) {
                        mNamaInstansi.getEditText().setText(load);
                    }
                }
            }
        });
    }

    private void loadAlamatInstansi() {
        mDocRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc =  Objects.requireNonNull(task.getResult());
                    String load = doc.getString("pekerjaan_alamatinstansi");
                    if (mAlamatInstansi.getEditText() != null) {
                        mAlamatInstansi.getEditText().setText(load);
                    }
                }
            }
        });
    }

    private void loadPenghasilan() {
        mDocRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc =  Objects.requireNonNull(task.getResult());
                    String load = doc.getString("pekerjaan_penghasilan");
                    if (mPenghasilan.getText() != null) {
                        mPenghasilan.setText(load);
                    }
                }
            }
        });
    }

    //-------------------------------------------------------------------------------------------//

    private boolean validateJenisPekerjaan() {
        String val = mJenisPekerjaan.getText().toString();

        if (val.isEmpty()) {
            mJenisPekerjaan.setError("Form tidak boleh kosong!");
            return false;
        } else {
            mJenisPekerjaan.setError(null);
            return true;
        }
    }

    private boolean validateNamaInstansi() {
        String val = Objects.requireNonNull(mNamaInstansi.getEditText()).getText().toString();

        if (val.isEmpty()) {
            mNamaInstansi.setError("Form tidak boleh kosong!");
            return false;
        } else {
            mNamaInstansi.setError(null);
            return true;
        }
    }

    private boolean validateAlamatInstansi() {
        String val = Objects.requireNonNull(mAlamatInstansi.getEditText()).getText().toString();

        if (val.isEmpty()) {
            mAlamatInstansi.setError("Form tidak boleh kosong!");
            return false;
        } else {
            mAlamatInstansi.setError(null);
            return true;
        }
    }

    private boolean validatePenghasilan() {
        String val = mPenghasilan.getText().toString();

        if (val.isEmpty()) {
            mPenghasilan.setError("Form tidak boleh kosong!");
            return false;
        } else {
            mPenghasilan.setError(null);
            return true;
        }
    }

    public void OnClickButtonUploadPekerjaan(View view) {
        if (!validateJenisPekerjaan() | !validateNamaInstansi() | !validateAlamatInstansi()
                | !validatePenghasilan()) {
            return;
        }

        String pekerjaan_jenis = mJenisPekerjaan.getText().toString();
        String pekerjaan_namainstansi = Objects.requireNonNull(mNamaInstansi.getEditText())
                .getText().toString();
        String pekerjaan_alamatinstansi = Objects.requireNonNull(mAlamatInstansi.getEditText())
                .getText().toString();
        String pekerjaan_penghasilan = mPenghasilan.getText().toString();

        mDocRef.update("pekerjaan_jenis", pekerjaan_jenis);
        mDocRef.update("pekerjaan_namainstansi", pekerjaan_namainstansi);
        mDocRef.update("pekerjaan_alamatinstansi", pekerjaan_alamatinstansi);
        mDocRef.update("pekerjaan_penghasilan", pekerjaan_penghasilan);

        mDocRef.update("info_pekerjaan", "done")
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(InfoPekerjaanActivity.this,
                                "DATA PEKERJAAN DIPERAHARUI!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });

    }

    //-------------------------------------------------------------------------------------------//

    public void ButtonBackArrow(View view) {
        finish();
    }

}
