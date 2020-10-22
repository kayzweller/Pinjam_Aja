/*
 * Created by Albert Kristaen (s6joxx) on 10/22/20, 11:34 AM
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 7/3/20, 10:33 PM
 */

package com.xoxltn.pinjam_aja.pendana;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.xoxltn.pinjam_aja.R;
import com.xoxltn.pinjam_aja.InfoUserRegisterDialog;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class PendanaHomeFragment extends Fragment {

    static final String SEND_DOCUMENT_PATH = "com.xoxltn.pinjam_aja.SEND_DOCUMENT_PATH";
    static final String SEND_DOCUMENT_DOC = "com.xoxltn.pinjam_aja.SEND_DOCUMENT_DOC";

    private View mView;
    private CollectionReference mColRef;
    private DocumentReference mDocRef;
    private AdapterFundReq adapter;

    private String info_id, info_pribadi, info_pekerjaan, info_kontak, info_rekening;

    //-------------------------------------------------------------------------------------------//

    public PendanaHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_pendana_home, container, false);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String userID = mAuth.getUid(); assert userID != null;

        mColRef = FirebaseFirestore.getInstance()
                .collection("PEMINJAM");
        mDocRef = FirebaseFirestore.getInstance()
                .collection("USER").document(userID);

        loadRegVerification();
        setUpRecycleView();

        return mView;
    }

    //-------------------------------------------------------------------------------------------//

    private void loadRegVerification() {
        mDocRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
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
            }
        });

    }

    private boolean checkRegStatus() {
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

    private void setUpRecycleView() {

        Query query = mColRef.whereEqualTo("pendanaan_req", true)
                .orderBy("pinjaman_tanggal_req", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<ModelFundReq> options = new FirestoreRecyclerOptions.Builder<ModelFundReq>()
                .setQuery(query, ModelFundReq.class)
                .build();

        adapter = new AdapterFundReq(options);

        RecyclerView recyclerView = mView.findViewById(R.id.recycler_view_fund_req);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        // ON CLICK RECYCLEVIEW ITEM HERE!!
        adapter.setOnItemClickListener(new AdapterFundReq.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                String doc = documentSnapshot.getId();
                String path = documentSnapshot.getReference().getPath();

                // CHECK LENDER REGISTRATION STATUSES.
                if (checkRegStatus()) {
                    Intent toFundReqDetail = new Intent(getActivity(), PendanaFundReqActivity.class);
                    toFundReqDetail.putExtra(SEND_DOCUMENT_PATH, path);
                    toFundReqDetail.putExtra(SEND_DOCUMENT_DOC, doc);
                    startActivity(toFundReqDetail);
                }

            }
        });
    }

    //-------------------------------------------------------------------------------------------//

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
        loadRegVerification();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadRegVerification();
    }
}
