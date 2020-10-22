/*
 * Created by Albert Kristaen (s6joxx) on 10/22/20, 11:34 AM
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 7/5/20, 2:33 AM
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

public class InfoRekeningActivity extends AppCompatActivity {

    private DocumentReference mDocRef;
    String mUserID, mUserType;

    private AutoCompleteTextView mNamaBank;
    private TextInputLayout mRekeningBank, mNamaRekening;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_rekening);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore mFire = FirebaseFirestore.getInstance();

        mUserID = mAuth.getUid();
        mDocRef = mFire.collection("USER").document(mUserID);

        mRekeningBank = findViewById(R.id.rekening_nomor);
        mNamaBank = findViewById(R.id.rekening_bank);
        mNamaRekening = findViewById(R.id.rekening_nama);

        getListBank();
        getUserType();
        loadPreviousData();
    }

    //-------------------------------------------------------------------------------------------//

    private void getListBank() {
        mNamaBank.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                listBank();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                listBank();
            }
        });
    }

    private void listBank() {
        String[] items = new String[] {
                "Bank Central Asia (BCA)",
                "Bank Negara Indonesia (BNI)",
                "Bank Rakyat Indonesia (BRI)",
                "Bank Mandiri",
                "Bank Pembangunan Daerah Kalteng",
                "Bank Tabungan Negara (BTN)",
                "Bank Permata",
                "Bank Danamon",
                "Bank CIMB Niaga"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                InfoRekeningActivity.this,
                R.layout.dropdown_item,
                items
        );

        mNamaBank.setAdapter(adapter);
    }

    //-------------------------------------------------------------------------------------------//

    private void loadPreviousData() {
        loadRekeningBank();
        loadNamaBank();
        loadNamaRekening();
    }

    private void loadRekeningBank() {
        mDocRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot doc =  Objects.requireNonNull(task.getResult());
                String load = doc.getString("rekening_rekbank");
                if (mRekeningBank.getEditText() != null) {
                    mRekeningBank.getEditText().setText(load);
                }
            }
        });
    }

    private void loadNamaBank() {
        mDocRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot doc =  Objects.requireNonNull(task.getResult());
                String load = doc.getString("rekening_namabank");
                if (mNamaBank.getText() != null) {
                    mNamaBank.setText(load);
                }
            }
        });
    }

    private void loadNamaRekening() {
        mDocRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot doc =  Objects.requireNonNull(task.getResult());
                String load = doc.getString("rekening_namaholder");
                if (mNamaRekening.getEditText() != null) {
                    mNamaRekening.getEditText().setText(load);
                }
            }
        });
    }

    private void getUserType() {
        mDocRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot doc =  Objects.requireNonNull(task.getResult());
                mUserType = doc.getString("userType");
            }
        });
    }

    //-------------------------------------------------------------------------------------------//

    private boolean validasiRekeningBank() {
        String val = Objects.requireNonNull(mRekeningBank.getEditText()).getText().toString();

        if (val.isEmpty()) {
            mRekeningBank.setError("Form tidak boleh kosong!");
            return false;
        } else {
            mRekeningBank.setError(null);
            return true;
        }
    }

    private boolean validasiNamaBank() {
        String val = mNamaBank.getText().toString();

        if (val.isEmpty()) {
            mNamaBank.setError("Form tidak boleh kosong!");
            return false;
        } else {
            mNamaBank.setError(null);
            return true;
        }
    }

    private boolean validasiNamaRekening() {
        String val = Objects.requireNonNull(mNamaRekening.getEditText()).getText().toString();

        if (val.isEmpty()) {
            mNamaRekening.setError("Form tidak boleh kosong!");
            return false;
        } else {
            mNamaRekening.setError(null);
            return true;
        }
    }

    public void OnClickButtonUploadRekening(View view) {
        if (!validasiRekeningBank() | !validasiNamaBank() | !validasiNamaRekening()) {
            return;
        }

        String rekening_rekbank = Objects.requireNonNull(mRekeningBank.getEditText())
                .getText().toString();
        String rekening_namabank = mNamaBank.getText().toString();
        String rekening_namaholder = Objects.requireNonNull(mNamaRekening.getEditText())
                .getText().toString();

        mDocRef.update("rekening_rekbank", rekening_rekbank);
        mDocRef.update("rekening_namabank", rekening_namabank);
        mDocRef.update("rekening_namaholder", rekening_namaholder);

        if (mUserType.equals("PEMINJAM")) {
            mDocRef.update("pinjaman_status", false);
            mDocRef.update("pinjaman_aktif", "0");
        }

        mDocRef.update("info_rekening", "done")
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(InfoRekeningActivity.this,
                            "DATA REKENING DIPERAHARUI!", Toast.LENGTH_SHORT).show();
                    finish();
                });
    }
    //-------------------------------------------------------------------------------------------//

    public void ButtonBackArrow(View view) {
        finish();
    }
}
