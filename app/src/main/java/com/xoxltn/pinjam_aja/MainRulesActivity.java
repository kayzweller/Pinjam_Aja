/*
 * Created by Albert Kristaen (Kayzweller) on 25/06/20 08.26
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 25/06/20 08.26
 */

package com.xoxltn.pinjam_aja;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.xoxltn.pinjam_aja.registrasi.InfoUserProfileActivity;

public class MainRulesActivity extends AppCompatActivity {

    private DocumentReference mDocRef;
    String mUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_rules);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore mFire = FirebaseFirestore.getInstance();

        mUserID = mAuth.getUid();
        mDocRef = mFire.collection("USER").document(mUserID);
    }

    public void OnClickAgree(View view) {
        mDocRef.update("info_rules", true)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(MainRulesActivity.this,
                            "Silahkan Melanjutkan Registrasi Anda!",
                            Toast.LENGTH_SHORT).show();
                    Intent goToRegistration = new Intent(this,
                            InfoUserProfileActivity.class);
                    startActivity(goToRegistration);
                    finish();
                });
    }

}
