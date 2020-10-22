/*
 * Created by Albert Kristaen (s6joxx) on 10/22/20, 11:34 AM
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 6/25/20, 5:04 AM
 */

package com.xoxltn.pinjam_aja.pendana;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.xoxltn.pinjam_aja.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PendanaFundFragment extends Fragment {

    static final String SEND_PATH = "com.xoxltn.pinjam_aja.SEND_PATH";
    static final String SEND_DOC = "com.xoxltn.pinjam_aja.SEND_DOC";

    private View mView;
    private CollectionReference mColRef;
    private AdapterFundDetail adapter;
    private String mUserID;

    //-------------------------------------------------------------------------------------------//

    public PendanaFundFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView =  inflater.inflate(R.layout.fragment_pendana_fund, container, false);

        mUserID = FirebaseAuth.getInstance().getUid();

        mColRef = FirebaseFirestore.getInstance().collection("PEMINJAM");

        setUpRecycleView();

        return mView;
    }

    //-------------------------------------------------------------------------------------------//

    private void setUpRecycleView() {
        Query query = mColRef.whereEqualTo("pendanaan_status", true)
                .whereEqualTo("id_pendana", mUserID)
                .orderBy("pinjaman_tanggal_req", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<ModelFundDetail> options = new FirestoreRecyclerOptions.Builder<ModelFundDetail>()
                .setQuery(query, ModelFundDetail.class)
                .build();

        adapter = new AdapterFundDetail(options);

        RecyclerView recyclerView = mView.findViewById(R.id.recycler_view_fund_detail);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        // ON CLICK RECYCLEVIEW ITEM HERE!!
        adapter.setOnItemClickListener((documentSnapshot, position) -> {
            String doc = documentSnapshot.getId();
            String path = documentSnapshot.getReference().getPath();

            // TO DETAIL FUNDING
            Intent toFundDetail = new Intent(getActivity(), PendanaFundDetailActivity.class);
            toFundDetail.putExtra(SEND_PATH, path);
            toFundDetail.putExtra(SEND_DOC, doc);
            startActivity(toFundDetail);

        });
    }

    //-------------------------------------------------------------------------------------------//

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
