/*
 * Created by Albert Kristaen (s6joxx) on 2/10/21, 9:45 PM
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 10/22/20, 11:34 AM
 */

package com.xoxltn.pinjam_aja;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.xoxltn.pinjam_aja.peminjam.PeminjamDashboardActivity;
import com.xoxltn.pinjam_aja.pendana.PendanaDashboardActivity;
import java.util.Objects;

public class MainLoginActivity extends AppCompatActivity {

    TextInputLayout mEmail, mPassword;
    ProgressBar mProgressBar;
    Button mMasukButton;

    private FirebaseAuth mAuth;
    private FirebaseUser mFireUser;
    private FirebaseFirestore mFire;

    String mUserID, mUserType;

    private final String PENDANA = "PENDANA";
    private final String PEMINJAM = "PEMINJAM";
    private final String mKeyAdmin = "(vNSDP534cgPHAbqocLjJmgQm68d2)";

    //-------------------------------------------------------------------------------------------//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);

        mAuth = FirebaseAuth.getInstance();
        mFireUser = mAuth.getCurrentUser();

        // set object from XML to Variable in java [HOOKS]
        mEmail = findViewById(R.id.login_email);
        mPassword = findViewById(R.id.login_password);
        mProgressBar = findViewById(R.id.login_progress_bar);
        mMasukButton = findViewById(R.id.login_button);

    }

    //-------------------------------------------------------------------------------------------//

    @Override
    protected void onStart() {
        super.onStart();

        mProgressBar.setMax(100);
        mProgressBar.setAlpha(0f);
        mProgressBar.setProgress(0);

        mAuth = FirebaseAuth.getInstance();
        mFire = FirebaseFirestore.getInstance();

        executeAutoLogin();
    }

    public void executeAutoLogin() {

        if (mFireUser != null) {

            progressBarLoad();

            mUserID = mFireUser.getUid();
            DocumentReference docRefLog = mFire.collection("USER").document(mUserID);

            docRefLog.get().addOnCompleteListener(task -> {

                if (task.isSuccessful()) {
                    DocumentSnapshot docLog = Objects.requireNonNull(task.getResult());
                    mUserType = docLog.getString("userType");
                } else {
                    mUserType = null;
                }

                if (Objects.equals(mUserType, PENDANA)) {
                    //user successfully login
                    toastLoginSuccess();
                    Intent pendanaLogin = new Intent(MainLoginActivity.this,
                            PendanaDashboardActivity.class);
                    startActivity(pendanaLogin);
                    finish();

                } else if (Objects.equals(mUserType, PEMINJAM)) {
                    //user successfully login
                    toastLoginSuccess();
                    Intent peminjamLogin = new Intent(MainLoginActivity.this,
                            PeminjamDashboardActivity.class);
                    startActivity(peminjamLogin);
                    finish();
                }

            });

        }
    }

    //-------------------------------------------------------------------------------------------//

    public void progressBarLoad() {
        mProgressBar.setAlpha(1.0f);
        mProgressBar.setProgress(100);

        mMasukButton.setEnabled(false);
    }

    public void progressBarUnload() {
        mProgressBar.setAlpha(0f);
        mProgressBar.setProgress(0);

        mMasukButton.setEnabled(true);
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

    private void toastLoginSuccess() {
        Toast.makeText(getApplicationContext(), "Selamat Datang!",
                Toast.LENGTH_SHORT).show();
    }

    public void onMasukButtonClick(View v) {

        progressBarLoad();

        if (!validateEmail() | !validatePassword()) {
            progressBarUnload();
            return;
        }

        String email = Objects.requireNonNull(mEmail.getEditText()).getText().toString();
        String password = Objects.requireNonNull(mPassword.getEditText()).getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    // SET STATEMENT TO CHECK WHO'S TRY TO LOG-IN!!
                    if (task.isSuccessful()) {

                        // CHECK USER TYPE (ADMIN or REGULAR USER [PEMINJAM or PENDANA)
                        mUserID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

                        if (mUserID.matches(mKeyAdmin)) {
                            progressBarUnload();
                            Toast.makeText(getApplicationContext(),
                                    "GUNAKAN 'Pinjam Aja! ADMIN' UNTUK LOGIN.",
                                    Toast.LENGTH_SHORT).show();

                        } else if (!mUserID.matches(mKeyAdmin)) {

                            // CALL THE USER TYPE (using document reference)
                            DocumentReference docRef = mFire.collection("USER")
                                    .document(mUserID);
                            docRef.get().addOnCompleteListener(task1 -> {

                                if (task1.isSuccessful()) {
                                    DocumentSnapshot doc = Objects.requireNonNull(task1.getResult());
                                    mUserType = doc.getString("userType");
                                } else {
                                    mUserType = null;
                                }

                                // AUTO JUMP TO DASHBOARD ACCORDING TO USER TYPE!
                                if (Objects.equals(mUserType, PENDANA)) {
                                    //user successfully login
                                    toastLoginSuccess();
                                    Intent pendanaLogin = new Intent(MainLoginActivity.this,
                                            PendanaDashboardActivity.class);
                                    startActivity(pendanaLogin);
                                    finish();

                                } else if (Objects.equals(mUserType, PEMINJAM)) {
                                    //user successfully login
                                    toastLoginSuccess();
                                    Intent peminjamLogin = new Intent(MainLoginActivity.this,
                                            PeminjamDashboardActivity.class);
                                    startActivity(peminjamLogin);
                                    finish();
                                }

                            })
                                    .addOnFailureListener(e -> {
                                        progressBarUnload();
                                        Toast.makeText(getApplicationContext(), "DATA MISSING!! "
                                                        + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        }

                    } else {
                        progressBarUnload();
                        Toast.makeText(getApplicationContext(), Objects
                                        .requireNonNull(task.getException()).getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }

                });
    }

    //-------------------------------------------------------------------------------------------//

    public void onClickLupaButton (View v) {
        Intent lupaPassword = new Intent(this, MainLupaPasswordActivity.class);
        startActivity(lupaPassword);
        finish();
    }

    //-------------------------------------------------------------------------------------------//

    public void onClicktoSignUp (View v) {
        Intent signUpActivity = new Intent(MainLoginActivity.this,
                MainSignUpActivity.class);
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

        new Handler().postDelayed(() -> doubleBackToExitPressedOnce=false, DELAY_PRESS);
    }

}
