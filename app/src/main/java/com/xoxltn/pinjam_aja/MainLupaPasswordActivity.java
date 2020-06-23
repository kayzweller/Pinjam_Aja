/*
 * Copyright (c) 2020 Albert Kristaen (DBC 113 008)
 * ONLY USE UNDER PERMISSION -OR- I AM GONNA CHOP YOUR HANDS OFF!
 */

package com.xoxltn.pinjam_aja;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class MainLupaPasswordActivity extends AppCompatActivity {

    TextInputLayout mResetEmail;
    ProgressBar mProgressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_lupa_password);

        mResetEmail = findViewById(R.id.reset_email);
        mProgressBar = findViewById(R.id.reset_progress_bar);

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

    public void progressBarLoad() {
        mProgressBar.setAlpha(1.0f);
        mProgressBar.setProgress(100);
    }

    public void progressBarUnload() {
        mProgressBar.setAlpha(0f);
        mProgressBar.setProgress(0);
    }

    //-------------------------------------------------------------------------------------------//

    private Boolean validateEmail() {
        String val = Objects.requireNonNull(mResetEmail.getEditText()).getText().toString();
        String emailPattern = "([a-zA-Z0-9._-]+){3,}@([a-z.-]+){3,}\\.([a-z]+){2,}";

        if (val.isEmpty()) {
            mResetEmail.setError("Masukan alamat email Anda!");
            return false;
        } else if (!val.matches(emailPattern)) {
            mResetEmail.setError("Alamat email salah!");
            return false;
        } else {
            mResetEmail.setError(null);
            return true;
        }
    }

    public void onResetButtonClick (View view) {

        progressBarLoad();

        if (!validateEmail()) {
            progressBarUnload();
            return;
        }

        String resetEmail = Objects.requireNonNull(mResetEmail.getEditText()).getText().toString();

        //RESET VIA EMAIL FOR ACCOUNT!
        mAuth.sendPasswordResetEmail(resetEmail).addOnSuccessListener(new OnSuccessListener<Void>() {

            @Override
            public void onSuccess(Void aVoid) {

                progressBarUnload();
                Toast.makeText(getApplicationContext(), "Reset link telah dikirim ke email anda!!",
                        Toast.LENGTH_LONG).show();

                Intent loginActivity = new Intent(MainLupaPasswordActivity.this,
                        MainLoginActivity.class);
                startActivity(loginActivity);
                finish();

            }
        })
                .addOnFailureListener(new OnFailureListener() {

                    @Override
                    public void onFailure(@NonNull Exception e) {

                        progressBarUnload();
                        Toast.makeText(getApplicationContext(), e.getMessage(),
                                Toast.LENGTH_LONG).show();

                    }
                });
    }

    //--------------------------------------------------------------------------------------------//

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent loginActivity = new Intent(MainLupaPasswordActivity.this,
                MainLoginActivity.class);
        startActivity(loginActivity);
        finish();
    }

}
