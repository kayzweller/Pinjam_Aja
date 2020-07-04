/*
 * Created by Albert Kristaen (Kayzweller) on 25/06/20 02.05
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 24/06/20 18.47
 */

package com.xoxltn.pinjam_aja.peminjam;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.xoxltn.pinjam_aja.R;
import com.xoxltn.pinjam_aja.InfoUserRegisterDialog;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class PeminjamHomeFragment extends Fragment {

    static final String EXTRA_NUMBER = "com.xoxltn.pinjam_aja.EXTRA_NUMBER";

    private DocumentReference mDocRef;
    private View view;

    private String info_id, info_pribadi, info_pekerjaan, info_kontak, info_rekening = "null";

    //-------------------------------------------------------------------------------------------//

    public PeminjamHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_peminjam_home, container, false);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore mFire = FirebaseFirestore.getInstance();
        String userID = mAuth.getUid();
        if (userID != null) {
            mDocRef = mFire.collection("USER").document(userID);
        }

        ButtonOpsi500KClick();
        ButtonOpsi1000KClick();
        ButtonOpsi1500KClick();

        loadRegVerification();

        return view;
    }

    //-------------------------------------------------------------------------------------------//

    private void ButtonOpsi500KClick() {
        Button buttonOpsi500K = view.findViewById(R.id.button_opsi_500K);
        buttonOpsi500K.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (CheckRegStatus()) {
                    // SENT INTENT
                    Intent intentPinjam = new Intent(getActivity(),
                            PeminjamReqPinjamanActivity.class);
                    intentPinjam.putExtra(EXTRA_NUMBER, 500000);
                    startActivity(intentPinjam);
                }

            }
        });
    }

    private void ButtonOpsi1000KClick() {
        Button buttonOpsi1000K = view.findViewById(R.id.button_opsi_1000K);
        buttonOpsi1000K.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (CheckRegStatus()) {
                    // SENT INTENT
                    Intent intentPinjam = new Intent(getActivity(),
                            PeminjamReqPinjamanActivity.class);
                    intentPinjam.putExtra(EXTRA_NUMBER, 1000000);
                    startActivity(intentPinjam);
                }

            }
        });
    }

    private void ButtonOpsi1500KClick() {
        Button buttonOpsi1500K = view.findViewById(R.id.button_opsi_1500K);
        buttonOpsi1500K.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (CheckRegStatus()) {
                    Intent intentPinjam = new Intent(getActivity(),
                            PeminjamReqPinjamanActivity.class);
                    intentPinjam.putExtra(EXTRA_NUMBER, 1500000);
                    startActivity(intentPinjam);
                }

            }
        });
    }

    //-------------------------------------------------------------------------------------------//

    private void loadRegVerification() {
        mDocRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {

                DocumentSnapshot doc = Objects.requireNonNull(task.getResult());

                String done1 = doc.getString("info_id");
                if (done1 != null) {
                    info_id = done1;
                }

                String done2 = doc.getString("info_pribadi");
                if (done2 != null) {
                    info_pribadi = done2;
                }

                String done3 = doc.getString("info_pekerjaan");
                if (done3 != null) {
                    info_pekerjaan = done3;
                }

                String done4 = doc.getString("info_kontak");
                if (done4 != null) {
                    info_kontak = done4;
                }

                String done5 = doc.getString("info_rekening");
                if (done5 != null) {
                    info_rekening = done5;
                }

            }
        });

    }

    private boolean CheckRegStatus() {
        if (!"done".equalsIgnoreCase(info_id) | !"done".equalsIgnoreCase(info_pribadi)
                | !"done".equalsIgnoreCase(info_pekerjaan) | !"done".equalsIgnoreCase(info_kontak)
                | !"done".equalsIgnoreCase(info_rekening)) {

            if (getFragmentManager() != null) {
                InfoUserRegisterDialog peminjamDialog = new InfoUserRegisterDialog();
                peminjamDialog.show(getFragmentManager(), "00");
            }
            return false;

        } else {
            return true;
        }
    }

    //-------------------------------------------------------------------------------------------//


    @Override
    public void onStart() {
        super.onStart();
        loadRegVerification();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadRegVerification();
    }
}
