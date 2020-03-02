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
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    //deklarasi variabel
    TextInputLayout mEmail, mPassword;

    ProgressBar mProgressBar;

    //deklarasi firebase
    private FirebaseAuth mAuth;

    //-------------------------------------------------------------------------------------------//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // set object from XML to Variable in java [HOOKS]
        mEmail = findViewById(R.id.login_email);
        mPassword = findViewById(R.id.login_password);
        mProgressBar = findViewById(R.id.loginprogressBar);

        mAuth = FirebaseAuth.getInstance();

    }

    //-------------------------------------------------------------------------------------------//

    @Override
    protected void onStart() {
        super.onStart();

        mProgressBar.setMax(100);
        mProgressBar.setAlpha(0f);
        mProgressBar.setProgress(0);

        // is user signed?
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    //-------------------------------------------------------------------------------------------//

    private Boolean validateEmail() {
        String val = mEmail.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z.-]+\\.[a-z]+";

        if (val.isEmpty()) {
            mEmail.setError("Masukan alamat email Anda!");
            return false;
        } else if (!val.matches(emailPattern)) {
            mEmail.setError("Alamat email salah!");
            return false;
        } else {
            mEmail.setError(null);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = mPassword.getEditText().getText().toString();

        if (val.isEmpty()) {
            mPassword.setError("Masukan password Anda!");
            return false;
        } else {
            mPassword.setError(null);
            return true;
        }
    }

    public void onMasukButtonClick(View view) {
        mProgressBar.setAlpha(1.0f);
        mProgressBar.setProgress(100);

        if (!validateEmail() | !validatePassword()) {
            mProgressBar.setAlpha(0f);
            mProgressBar.setProgress(0);
            return;
        }

        String email = mEmail.getEditText().getText().toString();
        String password = mPassword.getEditText().getText().toString();

        mAuth.signInWithEmailAndPassword(email, password).
                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        // TODO : CREATE USER ACCOUNT LOGIN TYPE CHECKING
                        //  >> mUserType value called from user database in firestore
                        //  1. [task.isSucessful() && mUserType == "PENDANA"]
                        //  2. [task.isSucessful() && mUserType == "PEMINJAM"]

                        if (task.isSuccessful()) {
                            //user successfully login
                            Intent pendanaLogin = new Intent(LoginActivity.this,
                                    PendanaDashboardActivity.class);
                            startActivity(pendanaLogin);
                            finish();
                        } else {
                            mProgressBar.setAlpha(0f);
                            mProgressBar.setProgress(0);
                            Toast.makeText(getApplicationContext(), task.getException().
                                    getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                });
    }

    //-------------------------------------------------------------------------------------------//

    public void onClickLupaButton (View v) {
        Intent lupaPassword = new Intent(this, LupaPasswordActivity.class);
        startActivity(lupaPassword);
    }

    //-------------------------------------------------------------------------------------------//

    public void onClicktoSignUp (View view) {
        Intent signUpActivity = new Intent(LoginActivity.this,
                SignUpActivity.class);
        startActivity(signUpActivity);
        finish();
    }

    //-------------------------------------------------------------------------------------------//

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
