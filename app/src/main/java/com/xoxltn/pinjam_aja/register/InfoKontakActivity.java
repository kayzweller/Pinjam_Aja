package com.xoxltn.pinjam_aja.register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.xoxltn.pinjam_aja.R;

import java.util.Objects;

public class InfoKontakActivity extends AppCompatActivity {

    private TextInputLayout mKontakHub1, mKontakHub2, mKontakNama1, mKontakNama2,
            mKontakNoTelp1, mKontakNoTelp2;

    private DocumentReference mDocRef;
    private String mUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_kontak);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore mFire = FirebaseFirestore.getInstance();

        mUserID = mAuth.getUid();
        mDocRef = mFire.collection("USER").document(mUserID);

        mKontakHub1 = findViewById(R.id.kontak_hubungan1);
        mKontakHub2 = findViewById(R.id.kontak_hubungan2);
        mKontakNama1 = findViewById(R.id.kontak_nama1);
        mKontakNama2 = findViewById(R.id.kontak_nama2);
        mKontakNoTelp1 = findViewById(R.id.kontak_notelp1);
        mKontakNoTelp2 = findViewById(R.id.kontak_notelp2);

        loadPreviousData();
    }

    //-------------------------------------------------------------------------------------------//

    private void loadPreviousData() {
        loadKontakHub1();
        loadKontakHub2();
        loadKontakNama1();
        loadKontakNama2();
        loadKontakNoTelp1();
        loadKontakNoTelp2();
    }

    private void loadKontakHub1() {
        mDocRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc =  Objects.requireNonNull(task.getResult());
                    String nama = doc.getString("kontak_Hub1");
                    if (mKontakHub1.getEditText() != null) {
                        mKontakHub1.getEditText().setText(nama);
                    }
                }
            }
        });
    }

    private void loadKontakHub2() {
        mDocRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc =  Objects.requireNonNull(task.getResult());
                    String nama = doc.getString("kontak_Hub2");
                    if (mKontakHub2.getEditText() != null) {
                        mKontakHub2.getEditText().setText(nama);
                    }
                }
            }
        });
    }

    private void loadKontakNama1() {
        mDocRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc =  Objects.requireNonNull(task.getResult());
                    String nama = doc.getString("kontak_Nama1");
                    if (mKontakNama1.getEditText() != null) {
                        mKontakNama1.getEditText().setText(nama);
                    }
                }
            }
        });
    }

    private void loadKontakNama2() {
        mDocRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc =  Objects.requireNonNull(task.getResult());
                    String nama = doc.getString("kontak_Nama2");
                    if (mKontakNama2.getEditText() != null) {
                        mKontakNama2.getEditText().setText(nama);
                    }
                }
            }
        });
    }

    private void loadKontakNoTelp1() {
        mDocRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc =  Objects.requireNonNull(task.getResult());
                    String nama = doc.getString("kontak_NoTelp1");
                    if (mKontakNoTelp1.getEditText() != null) {
                        mKontakNoTelp1.getEditText().setText(nama);
                    }
                }
            }
        });
    }

    private void loadKontakNoTelp2() {
        mDocRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc =  Objects.requireNonNull(task.getResult());
                    String nama = doc.getString("kontak_NoTelp2");
                    if (mKontakNoTelp2.getEditText() != null) {
                        mKontakNoTelp2.getEditText().setText(nama);
                    }
                }
            }
        });
    }

    //-------------------------------------------------------------------------------------------//

    private boolean validateKontakHub1() {
        String val = Objects.requireNonNull(mKontakHub1.getEditText()).getText().toString();

        if (val.isEmpty()) {
            mKontakHub1.setError("Tidak boleh kosong!");
            return false;
        } else {
            mKontakHub1.setError(null);
            return true;
        }
    }

    private boolean validateKontakHub2() {
        String val = Objects.requireNonNull(mKontakHub2.getEditText()).getText().toString();

        if (val.isEmpty()) {
            mKontakHub2.setError("Tidak boleh kosong!");
            return false;
        } else {
            mKontakHub2.setError(null);
            return true;
        }
    }

    private boolean validateKontakNama1() {
        String val = Objects.requireNonNull(mKontakNama1.getEditText()).getText().toString();

        if (val.isEmpty()) {
            mKontakNama1.setError("Tidak boleh kosong!");
            return false;
        } else {
            mKontakNama1.setError(null);
            return true;
        }
    }

    private boolean validateKontakNama2() {
        String val = Objects.requireNonNull(mKontakNama2.getEditText()).getText().toString();

        if (val.isEmpty()) {
            mKontakNama2.setError("Tidak boleh kosong!");
            return false;
        } else {
            mKontakNama2.setError(null);
            return true;
        }
    }

    private boolean validateKontakNoTelp1() {
        String val = Objects.requireNonNull(mKontakNoTelp1.getEditText()).getText().toString();

        if (val.isEmpty()) {
            mKontakNoTelp1.setError("Tidak boleh kosong!");
            return false;
        } else {
            mKontakNoTelp1.setError(null);
            return true;
        }
    }

    private boolean validateKontakNoTelp2() {
        String val = Objects.requireNonNull(mKontakNoTelp2.getEditText()).getText().toString();

        if (val.isEmpty()) {
            mKontakNoTelp2.setError("Tidak boleh kosong!");
            return false;
        } else {
            mKontakNoTelp2.setError(null);
            return true;
        }
    }

    public void OnClickButtonUploadKontak(View view) {

        if (!validateKontakHub1() | !validateKontakHub2() | !validateKontakNama1() |
                !validateKontakNama2() | !validateKontakNoTelp1() | !validateKontakNoTelp2()) {
            return;
        }

        String kontak_Hub1 = Objects.requireNonNull(mKontakHub1.getEditText())
                .getText().toString();
        String kontak_Hub2 = Objects.requireNonNull(mKontakHub2.getEditText())
                .getText().toString();
        String kontak_Nama1 = Objects.requireNonNull(mKontakNama1.getEditText())
                .getText().toString();
        String kontak_Nama2 = Objects.requireNonNull(mKontakNama2.getEditText())
                .getText().toString();
        String kontak_NoTelp1 = Objects.requireNonNull(mKontakNoTelp1.getEditText())
                .getText().toString();
        String kontak_NoTelp2 = Objects.requireNonNull(mKontakNoTelp2.getEditText())
                .getText().toString();

        mDocRef.update("kontak_Hub1", kontak_Hub1);
        mDocRef.update("kontak_Hub2", kontak_Hub2);
        mDocRef.update("kontak_Nama1", kontak_Nama1);
        mDocRef.update("kontak_Nama2", kontak_Nama2);
        mDocRef.update("kontak_NoTelp1", kontak_NoTelp1);
        mDocRef.update("kontak_NoTelp2", kontak_NoTelp2);

        mDocRef.update("info_kontak", "done")
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(InfoKontakActivity.this,
                                "DATA TELAH DIPERAHARUI!", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    //-------------------------------------------------------------------------------------------//

    public void ButtonBackArrow(View view) {
        finish();
    }

}
