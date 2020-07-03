/*
 * Created by Albert Kristaen (Kayzweller) on 25/06/20 02.05
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 23/06/20 13.19
 */

package com.xoxltn.pinjam_aja.peminjam;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.xoxltn.pinjam_aja.MainAboutActivity;
import com.xoxltn.pinjam_aja.MainLoginActivity;
import com.xoxltn.pinjam_aja.MainRulesReadActivity;
import com.xoxltn.pinjam_aja.R;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class PeminjamUserFragment extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseFirestore mFire;
    private FirebaseUser mFireUser;
    private View mView;

    private TextView mUserEmailPeminjam, mUserNamePeminjam;

    private String mUserID, mNama, mEmail;

    public PeminjamUserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_peminjam_user, container, false);

        mAuth = FirebaseAuth.getInstance();
        mFire = FirebaseFirestore.getInstance();
        mFireUser = mAuth.getCurrentUser();

        mUserEmailPeminjam = mView.findViewById(R.id.email_user_peminjam);
        mUserNamePeminjam = mView.findViewById(R.id.nama_user_peminjam);

        mUserID = mFireUser.getUid();

        CallUserName();
        CallUserEmail();

        ButtonLogoutClick();
        ButtonTentangClick();
        buttonRulesClick();

        return mView;
    }

    //-------------------------------------------------------------------------------------------//

    @Override
    public void onResume() {
        super.onResume();

        mUserNamePeminjam.setText(mNama);
        mUserEmailPeminjam.setText(mEmail);
    }

    //-------------------------------------------------------------------------------------------//

    private void CallUserName() {
        DocumentReference docRefLog = mFire.collection("USER").document(mUserID);

        docRefLog.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot docLog = Objects.requireNonNull(task.getResult());
                mNama = docLog.getString("name");
                mUserNamePeminjam.setText(mNama);
            }
        });

    }

    private void CallUserEmail() {
        mEmail = mFireUser.getEmail();
        mUserEmailPeminjam.setText(mEmail);
    }

    //-------------------------------------------------------------------------------------------//

    private void ButtonLogoutClick() {

        Button buttonLogoutPeminjam = mView.findViewById(R.id.button_PeminjamLogout);
        buttonLogoutPeminjam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mAuth.getCurrentUser() != null) {
                    mAuth.signOut();

                    Toast.makeText(getActivity(), "LOGOUT SUCCESS",
                            Toast.LENGTH_SHORT).show();

                    Intent backToLogin = new Intent(getActivity(), MainLoginActivity.class);
                    backToLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    backToLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(backToLogin);
                    Objects.requireNonNull(getActivity()).finish();
                }

            }
        });

    }

    private void ButtonTentangClick() {
        Button buttonTentangPeminjam = mView.findViewById(R.id.button_TentangApps);
        buttonTentangPeminjam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToAbout = new Intent(getActivity(), MainAboutActivity.class);
                startActivity(goToAbout);
            }
        });
    }

    private void buttonRulesClick() {
        Button buttonTentangPeminjam = mView.findViewById(R.id.button_Rules);
        buttonTentangPeminjam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToRules = new Intent(getActivity(), MainRulesReadActivity.class);
                startActivity(goToRules);
            }
        });
    }

}
