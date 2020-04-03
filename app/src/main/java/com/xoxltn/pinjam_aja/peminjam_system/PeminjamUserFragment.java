package com.xoxltn.pinjam_aja.peminjam_system;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.xoxltn.pinjam_aja.LoginActivity;
import com.xoxltn.pinjam_aja.R;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class PeminjamUserFragment extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseUser mFireUser;
    private View mView;

    TextView mUserEmailPeminjam;

    public PeminjamUserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_peminjam_user, container, false);

        mAuth = FirebaseAuth.getInstance();
        mFireUser = mAuth.getCurrentUser();

        mUserEmailPeminjam = mView.findViewById(R.id.email_user_peminjam);
        mUserEmailPeminjam.setText(mFireUser.getEmail());

        ButtonLogoutClick();

        return mView;
    }

    //-------------------------------------------------------------------------------------------//

    private void ButtonLogoutClick() {

        Button buttonLogoutPeminjam = mView.findViewById(R.id.peminjam_logout);
        buttonLogoutPeminjam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mAuth.getCurrentUser() != null) {
                    mAuth.signOut();

                    Toast.makeText(getActivity(),
                            "LOGOUT SUCCESS",
                            Toast.LENGTH_SHORT).show();

                    Intent backToLogin = new Intent(getActivity(), LoginActivity.class);
                    backToLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    backToLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(backToLogin);
                    //Objects.requireNonNull(getActivity()).finish();
                }

            }
        });

    }

}
