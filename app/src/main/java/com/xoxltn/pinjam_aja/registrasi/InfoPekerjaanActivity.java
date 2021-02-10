/*
 * Created by Albert Kristaen (s6joxx) on 2/10/21, 9:45 PM
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 10/22/20, 11:34 AM
 */

package com.xoxltn.pinjam_aja.registrasi;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
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
        mJenisPekerjaan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                listPekerjaan();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                listPekerjaan();
            }
        });
    }

    private void listPekerjaan() {
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
        mPenghasilan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                listPenghasilan();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                listPenghasilan();
            }
        });
    }

    private void listPenghasilan() {
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
        mDocRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot doc =  Objects.requireNonNull(task.getResult());
                String load = doc.getString("pekerjaan_jenis");
                if (mJenisPekerjaan.getText() != null) {
                    mJenisPekerjaan.setText(load);
                }
            }
        });
    }

    private void loadNamaInstansi() {
        mDocRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot doc =  Objects.requireNonNull(task.getResult());
                String load = doc.getString("pekerjaan_namainstansi");
                if (mNamaInstansi.getEditText() != null) {
                    mNamaInstansi.getEditText().setText(load);
                }
            }
        });
    }

    private void loadAlamatInstansi() {
        mDocRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot doc =  Objects.requireNonNull(task.getResult());
                String load = doc.getString("pekerjaan_alamatinstansi");
                if (mAlamatInstansi.getEditText() != null) {
                    mAlamatInstansi.getEditText().setText(load);
                }
            }
        });
    }

    private void loadPenghasilan() {
        mDocRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot doc =  Objects.requireNonNull(task.getResult());
                String load = doc.getString("pekerjaan_penghasilan");
                if (mPenghasilan.getText() != null) {
                    mPenghasilan.setText(load);
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
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(InfoPekerjaanActivity.this,
                            "DATA PEKERJAAN DIPERAHARUI!", Toast.LENGTH_SHORT).show();
                    finish();
                });

    }

    //-------------------------------------------------------------------------------------------//

    public void ButtonBackArrow(View view) {
        finish();
    }

}
