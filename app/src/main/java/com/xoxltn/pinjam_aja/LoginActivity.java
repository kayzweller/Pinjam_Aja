/**
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    TextInputLayout mEmail, mPassword;
    ProgressBar mProgressBar;

    private FirebaseAuth mAuth;

    String mUserID;


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
    }

    //-------------------------------------------------------------------------------------------//

    private Boolean validateEmail() {
        String val = Objects.requireNonNull(mEmail.getEditText()).getText().toString();
        String emailPattern = "([a-zA-Z0-9._-]+){3,}@([a-z.-]+){3,}\\.([a-z]+){2,}";

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
        String val = Objects.requireNonNull(mPassword.getEditText()).getText().toString();

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

        String email = Objects.requireNonNull(mEmail.getEditText()).getText().toString();
        String password = Objects.requireNonNull(mPassword.getEditText()).getText().toString();

        mAuth.signInWithEmailAndPassword(email, password).
                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        // SET STATEMENT TO CHECK WHO'S TRY TO LOG-IN!!

                        if (task.isSuccessful()) {

                            // CHECK USER TYPE (ADMIN or REGULAR USER [PEMINJAM or PENDANA)
                            mUserID = mAuth.getUid(); assert mUserID != null;
                            String keyAdmin = "(vNSDP534cgPHAbqocLjJmgQm68d2)";

                            if (mUserID.matches(keyAdmin)) {
                                mProgressBar.setAlpha(0f);
                                mProgressBar.setProgress(0);
                                Toast.makeText(getApplicationContext(),
                                        "GUNAKAN 'Pinjam Aja! ADMIN' UNTUK LOGIN.",
                                        Toast.LENGTH_SHORT).show();

                            } else if (!mUserID.matches(keyAdmin)) {
                                mProgressBar.setAlpha(0f);
                                mProgressBar.setProgress(0);
                                Toast.makeText(getApplicationContext(), "LOG-IN SUKSES!!",
                                        Toast.LENGTH_SHORT).show();

                                // GO TO ACTIVITY FOR CHECK USER TYPE, IS IT 'PENDANA' OR 'PEMINJAM'
                                Intent checkUser = new Intent(LoginActivity.this,
                                        CheckUserActivity.class);
                                startActivity(checkUser);
                                finish();
                            }

                        } else {
                            mProgressBar.setAlpha(0f);
                            mProgressBar.setProgress(0);
                            Toast.makeText(getApplicationContext(), Objects
                                            .requireNonNull(task.getException()).getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }

                    }

                });
    }

    //-------------------------------------------------------------------------------------------//

    // TODO :: LUPA PASSWORD??
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
