/**
 * Copyright (c) 2020 Albert Kristaen (DBC 113 008)
 * ONLY USE UNDER PERMISSION -OR- I AM GONNA CHOP YOUR HANDS OFF!
 */

package com.xoxltn.pinjam_aja;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class CheckUserActivity extends AppCompatActivity {

    TextView mTextUserID, mTextUserType;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore mFire = FirebaseFirestore.getInstance();

    String mUserID, mUserType;
    String PENDANA = "PENDANA";
    String PEMINJAM = "PEMINJAM";

    //-------------------------------------------------------------------------------------------//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_user);

        mTextUserID = findViewById(R.id.text_user_id);
        mTextUserType = findViewById(R.id.text_user_type);

        mUserID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        DocumentReference docRef = mFire.collection("USER").document(mUserID);

        // CALL THE USER TYPE (using document reference)
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    assert doc != null;
                    mUserType = doc.getString("userType");
                } else {
                    mUserType = null;
                }

                mTextUserID.setText(mUserID);
                mTextUserType.setText(mUserType);

            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "DATA MISSING!!",
                                Toast.LENGTH_SHORT).show();
                    }
                });

        // AUTO JUMP TO DASHBOARD ACCORDING TO USER TYPE!
        if (Objects.equals(mUserType, PENDANA)) {
            //user successfully login
            Intent pendanaLogin = new Intent(CheckUserActivity.this,
                    PendanaDashboardActivity.class);
            startActivity(pendanaLogin);
            finish();

        } else if (Objects.equals(mUserType, PEMINJAM)) {
            //user successfully login
            Intent peminjamLogin = new Intent(CheckUserActivity.this,
                    PeminjamDashboardActivity.class);
            startActivity(peminjamLogin);
            finish();
        }

    }

    //-------------------------------------------------------------------------------------------//

    public void onLogoutButtonClick(View view) {
        mAuth.signOut();
        Intent loginActivity = new Intent(this, LoginActivity.class);
        startActivity(loginActivity);
        finish();
    }

    //-------------------------------------------------------------------------------------------//

    @Override
    public void onBackPressed() {
        // DISABLE BACK BUTTON
    }

}
