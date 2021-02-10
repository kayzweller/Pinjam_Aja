/*
 * Created by Albert Kristaen (s6joxx) on 2/10/21, 9:45 PM
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 10/22/20, 11:34 AM
 */

package com.xoxltn.pinjam_aja.peminjam;

import android.os.Bundle;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.xoxltn.pinjam_aja.AdapterNotif;
import com.xoxltn.pinjam_aja.ModelNotif;
import com.xoxltn.pinjam_aja.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PeminjamNotifFragment extends Fragment {

    private View mView;
    private CollectionReference mColRef;
    private AdapterNotif adapter;
    private String mUserID;

    //-------------------------------------------------------------------------------------------//

    public PeminjamNotifFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView =  inflater.inflate(R.layout.fragment_peminjam_notif, container, false);

        mUserID = FirebaseAuth.getInstance().getUid();

        mColRef = FirebaseFirestore.getInstance().collection("NOTIFIKASI");

        setUpRecycleView();

        return mView;
    }

    //-------------------------------------------------------------------------------------------//

    private void setUpRecycleView() {
        Query query = mColRef.whereEqualTo("id_user", mUserID)
                .orderBy("notif_date", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<ModelNotif> options = new FirestoreRecyclerOptions.Builder<ModelNotif>()
                .setQuery(query, ModelNotif.class)
                .build();

        adapter = new AdapterNotif(options);

        RecyclerView recyclerView = mView.findViewById(R.id.recycler_view_notif_peminjam);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener((documentSnapshot, position) -> {
            String doc = documentSnapshot.getId();

            Toast.makeText(getActivity(), doc, Toast.LENGTH_SHORT).show();
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
