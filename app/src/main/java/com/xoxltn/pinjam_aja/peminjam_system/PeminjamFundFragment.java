package com.xoxltn.pinjam_aja.peminjam_system;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xoxltn.pinjam_aja.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class PeminjamFundFragment extends Fragment {

    public PeminjamFundFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_peminjam_fund, container, false);
    }
}
