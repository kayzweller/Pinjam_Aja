/*
 * Copyright (c) 2020 Albert Kristaen (DBC 113 008)
 * ONLY USE UNDER PERMISSION -OR- I AM GONNA CHOP YOUR HANDS OFF!
 */

package com.xoxltn.pinjam_aja;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {

    // Deklarasi Variabel
    TextInputLayout mFullName, mEmail, mPhone, mPassword;
    RadioGroup mRadioGroup;
    RadioButton mRadioButton;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // set object from XML to Variable in java [HOOKS]
        mFullName = findViewById(R.id.signup_nama);
        mEmail = findViewById(R.id.signup_email);
        mPhone = findViewById(R.id.signup_phone);
        mPassword = findViewById(R.id.signup_password);
        mRadioGroup = findViewById(R.id.radio_group_user);

        mAuth = FirebaseAuth.getInstance();

    }

    //-------------------------------------------------------------------------------------------//

    @Override
    protected void onStart() {
        super.onStart();
        // what to do when activity started?
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    //-------------------------------------------------------------------------------------------//

    private Boolean validateName() {
        String val = mFullName.getEditText().getText().toString();
        String namePattern = "[a-zA-Z\\s]+";

        if (val.isEmpty()) {
            mFullName.setError("Nama tidak boleh kosong!");
            return false;
        } else if (!val.matches(namePattern)) {
            mFullName.setError("Nama mengandung karakter yang tidak diijinkan!");
            return false;
        } else {
            mFullName.setError(null);
            return true;
        }

    }

    private Boolean validateEmail() {
        String val = mEmail.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z.-]+\\.[a-z]+";

        if (val.isEmpty()) {
            mEmail.setError("Alamat Email tidak boleh kosong!");
            return false;
        } else if (!val.matches(emailPattern)) {
            mEmail.setError("Alamat email salah!");
            return false;
        } else {
            mEmail.setError(null);
            return true;
        }
    }

    private Boolean validatePhone() {
        String val = mPhone.getEditText().getText().toString();
        String phonePattern = "[+62][0-9]+";

        if (val.isEmpty()) {
            mPhone.setError("Nomor handphone tidak boleh kosong");
            return false;
        } else if (!val.matches(phonePattern)) {
            mPhone.setError("Gunakan format internasional, contoh : +6281234567890");
            return false;
        } else if (val.length() > 14 || val.length() < 10) {
            mPhone.setError("Nomor handphone tidak valid");
            return false;
        } else {
            mPhone.setError(null);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = mPassword.getEditText().getText().toString();

        if (val.isEmpty()) {
            mPassword.setError("Password tidak boleh kosong!");
            return false;
        } else if (val.length() < 6 ) {
            mPassword.setError("Password minimal 6 karakter!");
            return false;
        } else {
            mPassword.setError(null);
            return true;
        }

    }

    private Boolean validateUserType() {

        if (mRadioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Silahkan pilih tipe akun!",
                    Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }

    }

    public void onDaftarButtonClick(View view) {
        if (!validateName() | !validateEmail() | !validatePhone() | !validatePassword()
                | !validateUserType()) {
            return;
        }

        // ekstrak data dari radio button.
        int SelectedId = mRadioGroup.getCheckedRadioButtonId();
        mRadioButton = findViewById(SelectedId);

        String name = mFullName.getEditText().getText().toString();
        String email = mEmail.getEditText().getText().toString();
        String phone = mPhone.getEditText().getText().toString();
        String password = mPassword.getEditText().getText().toString();
        String userType = mRadioButton.getText().toString();

        Toast.makeText(this, userType + " SELECTED", Toast.LENGTH_SHORT).show();

        // TODO : FIREBASE SIGNUP HERE

    }

    //-------------------------------------------------------------------------------------------//

    public void onClicktoLogin(View view) {
        Intent loginActivity = new Intent(SignUpActivity.this,
                LoginActivity.class);
        startActivity(loginActivity);
        finish();
    }

    //-------------------------------------------------------------------------------------------//

    // variabel untuk fungsi tombol BACK
    boolean doubleBackToExitPressedOnce = false;
    int DELAY_PRESS = 2000;

    // method untuk tekan tombol BACK dua kali untuk keluar
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Tekan sekali lagi untuk keluar", Toast.LENGTH_SHORT)
                .show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, DELAY_PRESS);
    }

}
