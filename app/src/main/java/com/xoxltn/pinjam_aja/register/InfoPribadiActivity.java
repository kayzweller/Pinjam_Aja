package com.xoxltn.pinjam_aja.register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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

public class InfoPribadiActivity extends AppCompatActivity {

    private DocumentReference mDocRef;
    String mUserID;

    private AutoCompleteTextView mPendidikan, mPernikahan, mKendaraan;
    private TextInputLayout mAlamat, mNamaIbu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_pribadi);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore mFire = FirebaseFirestore.getInstance();

        mUserID = mAuth.getUid();
        mDocRef = mFire.collection("USER").document(mUserID);

        mPendidikan = findViewById(R.id.pribadi_pendidikan);
        mPernikahan = findViewById(R.id.pribadi_pernikahan);
        mAlamat = findViewById(R.id.pribadi_alamat);
        mNamaIbu = findViewById(R.id.pribadi_ibu);
        mKendaraan = findViewById(R.id.pribadi_kendaraan);

        getListPendidikan();
        getListPernikahan();
        getListKendaraan();

        loadPreviousData();

    }

    //-------------------------------------------------------------------------------------------//

    private void getListPendidikan() {
        String[] items = new String[] {
                "Sekolah Dasar (SD)",
                "Sekolah Menengah Pertama (SMP)",
                "Sekolah Menengah Atas (SMA)",
                "D3/Akademi",
                "Sarjana",
                "Magister"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                InfoPribadiActivity.this,
                R.layout.dropdown_item,
                items
        );

        mPendidikan.setAdapter(adapter);
    }

    private void getListPernikahan() {
        String[] items = new String[] {
                "Belum Menikah",
                "Nikah - Punya Anak",
                "Nikah - Tanpa Anak",
                "Cerai"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                InfoPribadiActivity.this,
                R.layout.dropdown_item,
                items
        );

        mPernikahan.setAdapter(adapter);
    }

    private void getListKendaraan() {
        String[] items = new String[] {
                "Tidak Memiliki Kendaraan",
                "Sepeda Motor",
                "Mobil"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                InfoPribadiActivity.this,
                R.layout.dropdown_item,
                items
        );

        mKendaraan.setAdapter(adapter);
    }

    //-------------------------------------------------------------------------------------------//

    private void loadPreviousData() {
        loadPendidikan();
        loadPernikahan();
        loadAlamat();
        loadNamaIbu();
        loadKendaraan();
    }

    private void loadPendidikan() {
        mDocRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc =  Objects.requireNonNull(task.getResult());
                    String load = doc.getString("pribadi_pendidikan");
                    if (mPendidikan.getText() != null) {
                        mPendidikan.setText(load);
                    }
                }
            }
        });
    }

    private void loadPernikahan() {
        mDocRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc =  Objects.requireNonNull(task.getResult());
                    String load = doc.getString("pribadi_pernikahan");
                    if (mPernikahan.getText() != null) {
                        mPernikahan.setText(load);
                    }
                }
            }
        });
    }

    private void loadAlamat() {
        mDocRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc =  Objects.requireNonNull(task.getResult());
                    String load = doc.getString("pribadi_alamat");
                    if (mAlamat.getEditText() != null) {
                        mAlamat.getEditText().setText(load);
                    }
                }
            }
        });
    }

    private void loadNamaIbu() {
        mDocRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc =  Objects.requireNonNull(task.getResult());
                    String load = doc.getString("pribadi_namaibu");
                    if (mNamaIbu.getEditText() != null) {
                        mNamaIbu.getEditText().setText(load);
                    }
                }
            }
        });
    }

    private void loadKendaraan() {
        mDocRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc =  Objects.requireNonNull(task.getResult());
                    String load = doc.getString("pribadi_kendaraan");
                    if (mKendaraan.getText() != null) {
                        mKendaraan.setText(load);
                    }
                }
            }
        });
    }

    //-------------------------------------------------------------------------------------------//

    private boolean validatePendidikan() {
        String val = mPendidikan.getText().toString();

        if (val.isEmpty()) {
            mPendidikan.setError("Form tidak boleh kosong!");
            return false;
        } else {
            mPendidikan.setError(null);
            return true;
        }
    }

    private boolean validatePernikahan() {
        String val = mPernikahan.getText().toString();

        if (val.isEmpty()) {
            mPernikahan.setError("Form tidak boleh kosong!");
            return false;
        } else {
            mPernikahan.setError(null);
            return true;
        }
    }

    private boolean validateAlamat() {
        String val = Objects.requireNonNull(mAlamat.getEditText()).getText().toString();

        if (val.isEmpty()) {
            mAlamat.setError("Form tidak boleh kosong!");
            return false;
        } else {
            mAlamat.setError(null);
            return true;
        }
    }

    private boolean validateNamaIbu() {
        String val = Objects.requireNonNull(mNamaIbu.getEditText()).getText().toString();

        if (val.isEmpty()) {
            mNamaIbu.setError("Form tidak boleh kosong!");
            return false;
        } else {
            mNamaIbu.setError(null);
            return true;
        }
    }

    private boolean validateKendaraan() {
        String val = mKendaraan.getText().toString();

        if (val.isEmpty()) {
            mKendaraan.setError("Form tidak boleh kosong!");
            return false;
        } else {
            mKendaraan.setError(null);
            return true;
        }
    }


    public void OnClickButtonUploadKontak(View view) {

        if (!validatePendidikan() | !validatePernikahan() | !validateAlamat()
                | !validateNamaIbu() | !validateKendaraan()) {
            return;
        }

        String pribadi_pendidikan = mPendidikan.getText().toString();
        String pribadi_pernikahan = mPernikahan.getText().toString();
        String pribadi_alamat = Objects.requireNonNull(mAlamat.getEditText())
                .getText().toString();
        String pribadi_namaibu = Objects.requireNonNull(mNamaIbu.getEditText())
                .getText().toString();
        String pribadi_kendaraan = mKendaraan.getText().toString();

        mDocRef.update("pribadi_pendidikan", pribadi_pendidikan);
        mDocRef.update("pribadi_pernikahan", pribadi_pernikahan);
        mDocRef.update("pribadi_alamat", pribadi_alamat);
        mDocRef.update("pribadi_namaibu", pribadi_namaibu);
        mDocRef.update("pribadi_kendaraan", pribadi_kendaraan);

        mDocRef.update("info_pribadi", "done")
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(InfoPribadiActivity.this,
                                "DATA TELAH DIPERAHARUI!", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    //-------------------------------------------------------------------------------------------//

    public void ButtonBackArrow(View view) {
        finish();
    }
}
