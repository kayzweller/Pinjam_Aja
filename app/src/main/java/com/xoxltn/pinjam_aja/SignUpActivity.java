/*
 * Copyright (c) 2020 Albert Kristaen (DBC 113 008)
 * ONLY USE UNDER PERMISSION -OR- I AM GONNA CHOP YOUR HANDS OFF!
 */

package com.xoxltn.pinjam_aja;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpActivity extends AppCompatActivity {

    // Deklarasi Variabel
    TextInputLayout mFullName, mEmail, mPhone, mPassword;
    RadioGroup mRadioGroup;
    RadioButton mRadioButton;
    ProgressBar mProgressBar;

    FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    String mUserID;

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
        mProgressBar = findViewById(R.id.loading);

        mAuth = FirebaseAuth.getInstance();

    }

    //-------------------------------------------------------------------------------------------//

    @Override
    protected void onStart() {
        super.onStart();

        //[ CALL userType WITHIN THE DOCUMENT, CALLING IT WITH THE SPECIFIC UserID ]
        mUserID = mAuth.getUid();
        String userType = "";

        // what to do when activity started?
        FirebaseUser currentUser = mAuth.getCurrentUser();
        checkUser(currentUser, userType);
    }

    private void checkUser(FirebaseUser user, String type) {

        // TODO : CREATE USER ACCOUNT TYPE CHECKING
        //  >> userType < from onStart() > value called from user database in firestore
        //  1. [ user != null && type.equals("PENDANA") ]
        //  2. [ user != null && type.equals("PEMINJAM") ]

        if (user != null && type.equals("PENDANA")) {
            //user type "PENDANA" successfully login
            Intent pendanaLogin = new Intent(this,
                    PendanaDashboardActivity.class);
            startActivity(pendanaLogin);
            finish();

        } else if (user != null && type.equals("PEMINJAM")) {

            //user type "PEMINJAM" successfully login
            Intent peminjamLogin = new Intent(this,
                    PeminjamDashboardActivity.class);
            startActivity(peminjamLogin);
            finish();
        } else {
            mProgressBar.setMax(100);
            mProgressBar.setAlpha(0f);
            mProgressBar.setProgress(0);
        }

    }

    //-------------------------------------------------------------------------------------------//

    public void onDaftarButtonClick(View view) {

        mProgressBar.setAlpha(1.0f);
        mProgressBar.setProgress(100);

        if (!validateName() | !validateEmail() | !validatePhone() | !validatePassword()
                | !validateUserType()) {
            mProgressBar.setAlpha(0f);
            mProgressBar.setProgress(0);
            return;
        }

        // ekstrak data dari radio button.
        int SelectedId = mRadioGroup.getCheckedRadioButtonId();
        mRadioButton = findViewById(SelectedId);

        String name = mFullName.getEditText().getText().toString().trim();
        String email = mEmail.getEditText().getText().toString().trim();
        String phone = mPhone.getEditText().getText().toString();
        String password = mPassword.getEditText().getText().toString();
        final String userType = mRadioButton.getText().toString();

        // SIGN-UP ACCOUNT
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()) {

                            //get USER ID
                            mUserID = mAuth.getUid();

                            //hide progressbar anim
                            mProgressBar.setAlpha(0f);
                            mProgressBar.setProgress(0);

                            Toast.makeText(SignUpActivity.this, "SUCCESS! "
                                            + userType + " UUID : " + mUserID,
                                    Toast.LENGTH_LONG).show();

                        } else {
                            mProgressBar.setAlpha(0f);
                            mProgressBar.setProgress(0);

                            Toast.makeText(SignUpActivity.this, "FAILED! "
                                    + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }

                    }
                });

    }

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
        String emailPattern = "([a-zA-Z0-9._-]+){3,}@([a-z.-]+){3,}\\.([a-z]+){3,}";

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
        } else if (val.length() > 15 || val.length() < 12) {
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
