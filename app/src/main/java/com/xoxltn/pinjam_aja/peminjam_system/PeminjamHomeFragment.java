package com.xoxltn.pinjam_aja.peminjam_system;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.xoxltn.pinjam_aja.R;
import com.xoxltn.pinjam_aja.register.UserRegisterDialog;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class PeminjamHomeFragment extends Fragment {

    static final String EXTRA_NUMBER = "com.xoxltn.pinjam_aja.EXTRA_NUMBER";

    private View mView;

    private DocumentReference mDocRef;

    private String info_id, info_pribadi, info_pekerjaan, info_kontak;
    private String info_rekening;

    public PeminjamHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_peminjam_home, container, false);

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

        return mView;
    }

    //-------------------------------------------------------------------------------------------//

    private void ButtonOpsi500KClick() {
        Button buttonOpsi500K = mView.findViewById(R.id.button_opsi_500K);
        buttonOpsi500K.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (CheckRegStatus()) {

                    //TODO : SENT INTENT
                    Intent intentPinjam = new Intent(getActivity(),
                            PermintaanPinjamanActivity.class);
                    intentPinjam.putExtra(EXTRA_NUMBER, 500000);
                    startActivity(intentPinjam);
                }

            }
        });
    }

    private void ButtonOpsi1000KClick() {
        Button buttonOpsi1000K = mView.findViewById(R.id.button_opsi_1000K);
        buttonOpsi1000K.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (CheckRegStatus()) {

                    //TODO : SENT INTENT
                    Intent intentPinjam = new Intent(getActivity(),
                            PermintaanPinjamanActivity.class);
                    intentPinjam.putExtra(EXTRA_NUMBER, 1000000);
                    startActivity(intentPinjam);
                }

            }
        });
    }

    private void ButtonOpsi1500KClick() {
        Button buttonOpsi1500K = mView.findViewById(R.id.button_opsi_1500K);
        buttonOpsi1500K.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (CheckRegStatus()) {

                    //TODO : SENT INTENT
                    Intent intentPinjam = new Intent(getActivity(),
                            PermintaanPinjamanActivity.class);
                    intentPinjam.putExtra(EXTRA_NUMBER, 1500000);
                    startActivity(intentPinjam);
                }

            }
        });
    }

    //-------------------------------------------------------------------------------------------//

    private void loadRegVerification() {

        mDocRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc =  Objects.requireNonNull(task.getResult());
                    info_id = doc.getString("info_id");
                }
            }
        });

        mDocRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc =  Objects.requireNonNull(task.getResult());
                    info_pribadi = doc.getString("info_pribadi");
                }
            }
        });

        mDocRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc =  Objects.requireNonNull(task.getResult());
                    info_pekerjaan = doc.getString("info_pekerjaan");
                }
            }
        });

        mDocRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc =  Objects.requireNonNull(task.getResult());
                    info_kontak = doc.getString("info_kontak");
                }
            }
        });

        mDocRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc =  Objects.requireNonNull(task.getResult());
                    info_rekening = doc.getString("info_rekening");
                }
            }
        });
    }

    private boolean CheckRegStatus() {

        assert info_id != null;
        assert info_pribadi != null;
        assert info_pekerjaan != null;
        assert info_kontak != null;
        assert info_rekening != null;

        if (!info_id.equals("done") | !info_pribadi.equals("done") | !info_pekerjaan.equals("done")
        | !info_kontak.equals("done") | !info_rekening.equals("done")) {

            if (getFragmentManager() != null) {
                UserRegisterDialog peminjamDialog = new UserRegisterDialog();
                peminjamDialog.show(getFragmentManager(), "Dialog Peminjam");
            } return false;

        } else {
            return true;
        }
    }

}
