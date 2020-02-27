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
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class SignUpActivity extends AppCompatActivity {

    // Deklarasi Variabel
    Button goToLoginButton, signupButton;
    TextInputLayout fullName, email, phone, password;
    RadioButton pendanaRadio, peminjamRadio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // set object from XML to Variable in java [HOOKS]
        goToLoginButton = findViewById(R.id.signup_to_login_button);
        fullName = findViewById(R.id.signup_nama);
        email = findViewById(R.id.signup_email);
        phone = findViewById(R.id.signup_phone);
        password = findViewById(R.id.signup_password);
        pendanaRadio = findViewById(R.id.signup_radio_pendana);
        peminjamRadio = findViewById(R.id.signup_radio_peminjam);
        signupButton = findViewById(R.id.signup_button);

    }

    //-------------------------------------------------------------------------------------------//

    public void onClicktoLogin(View view) {
        Intent loginActivity = new Intent(SignUpActivity.this,
                LoginActivity.class);
        startActivity(loginActivity);
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
