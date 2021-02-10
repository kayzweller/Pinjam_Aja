/*
 * Created by Albert Kristaen (s6joxx) on 2/10/21, 9:45 PM
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 10/22/20, 11:34 AM
 */

package com.xoxltn.pinjam_aja.registrasi;

import android.text.Editable;
import android.text.TextWatcher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.xoxltn.pinjam_aja.R;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class InfoIdentitasActivity extends AppCompatActivity {

    public static final int CAMERA_PERMISSION_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;

    private ImageView mImageViewKTP;
    private ProgressBar mProgressBarUpload;

    private TextInputLayout mNamaLengkap, mNoKTP, mTempatLahir, mTanggalLahir;
    private AutoCompleteTextView mJenisKelamin;

    private StorageReference mStorage;
    private DocumentReference mDocRef;

    String mUserID;

    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_identitas);

        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore mFire = FirebaseFirestore.getInstance();

        mUserID = mAuth.getUid();
        mStorage = storageRef.child("KTP").child(mUserID).child("foto_ktp");
        mDocRef = mFire.collection("USER").document(mUserID);

        mImageViewKTP = findViewById(R.id.ImageViewKTP);
        mProgressBarUpload = findViewById(R.id.progressBarUpload);
        mNamaLengkap = findViewById(R.id.identitas_NamaLengkap);
        mNoKTP = findViewById(R.id.identitas_NoKTP);
        mJenisKelamin = findViewById(R.id.identitas_JenisKelamin);
        mTempatLahir = findViewById(R.id.identitas_TempatLahir);
        mTanggalLahir = findViewById(R.id.identitas_TanggalLahir);

        loadDatePicker();
        loadJenisKelaminDropdown();
        loadPreviousData();


        mProgressBarUpload.setAlpha(0);

    }

    //-------------------------------------------------------------------------------------------//

    public void loadDatePicker() {
        final DatePickerDialog.OnDateSetListener date = (datePicker, i, i1, i2) -> {
            myCalendar.set(Calendar.YEAR, i);
            myCalendar.set(Calendar.MONTH, i1);
            myCalendar.set(Calendar.DAY_OF_MONTH, i2);
            updateLabel();
        };

        if (mTanggalLahir.getEditText() != null) {
            mTanggalLahir.getEditText().setOnClickListener(view -> new DatePickerDialog(
                    InfoIdentitasActivity.this, date, myCalendar.get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show());
        }
    }

    private void loadJenisKelaminDropdown() {
            mJenisKelamin.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    listJenisKelamin();
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    listJenisKelamin();
                }
            });
    }

    private void listJenisKelamin() {
        String[] items = new String[] {
                "Laki-laki",
                "Perempuan"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                InfoIdentitasActivity.this,
                R.layout.dropdown_item,
                items
        );

        mJenisKelamin.setAdapter(adapter);
    }

    private void updateLabel() {
        String myFormat = "dd-MMMM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);
        if (mTanggalLahir.getEditText() != null) {
            mTanggalLahir.getEditText().setText(sdf.format(myCalendar.getTime()));
        }
    }

    //-------------------------------------------------------------------------------------------//

    private void loadPreviousData() {
        loadImage();
        loadName();
        loadKTP();
        loadJenisKelamin();
        loadTempatLahir();
        loadTanggalLahir();
    }

    private void loadImage() {
        mStorage.getDownloadUrl().addOnSuccessListener(uri -> Picasso.get().load(uri).fit()
                .centerCrop().into(mImageViewKTP));
    }

    private void loadName() {
        mDocRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot doc =  Objects.requireNonNull(task.getResult());
                String nama = doc.getString("name");
                if (mNamaLengkap.getEditText() != null) {
                    mNamaLengkap.getEditText().setText(nama);
                }
            }
        });
    }

    private void loadKTP() {
        mDocRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot doc =  Objects.requireNonNull(task.getResult());
                String noKTP = doc.getString("noKTP");
                if (mNoKTP.getEditText() != null) {
                    mNoKTP.getEditText().setText(noKTP);
                }
            }
        });
    }

    private void loadJenisKelamin() {
        mDocRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot doc =  Objects.requireNonNull(task.getResult());
                String jenisKelamin = doc.getString("jenisKelamin");
                mJenisKelamin.setText(jenisKelamin);
            }
        });
    }

    private void loadTempatLahir() {
        mDocRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot doc =  Objects.requireNonNull(task.getResult());
                String tempatLahir = doc.getString("tempatLahir");
                if (mTempatLahir.getEditText() != null) {
                    mTempatLahir.getEditText().setText(tempatLahir);
                }
            }
        });
    }

    private void loadTanggalLahir() {
        mDocRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot doc =  Objects.requireNonNull(task.getResult());
                String tanggalLahir = doc.getString("tanggalLahir");
                if (mTanggalLahir.getEditText() != null) {
                    mTanggalLahir.getEditText().setText(tanggalLahir);
                }
            }
        });
    }

    //-------------------------------------------------------------------------------------------//

    private boolean validateName() {
        String val = Objects.requireNonNull(mNamaLengkap.getEditText()).getText().toString();
        String namePattern = "[a-zA-Z\\s]+";

        if (val.isEmpty()) {
            mNamaLengkap.setError("Nama tidak boleh kosong!");
            return false;
        } else if (!val.matches(namePattern)) {
            mNamaLengkap.setError("Nama mengandung karakter yang tidak diijinkan!");
            return false;
        } else {
            mNamaLengkap.setError(null);
            return true;
        }
    }

    private boolean validateNoKTP() {
        String val = Objects.requireNonNull(mNoKTP.getEditText()).getText().toString();

        if (val.isEmpty()) {
            mNoKTP.setError("No. KTP tidak boleh kosong!");
            return false;
        } else {
            mNoKTP.setError(null);
            return true;
        }
    }

    private boolean validateTempatLahir() {
        String val = Objects.requireNonNull(mTempatLahir.getEditText()).getText().toString();

        if (val.isEmpty()) {
            mTempatLahir.setError("Tempat Lahir tidak boleh kosong!");
            return false;
        } else {
            mTempatLahir.setError(null);
            return true;
        }
    }

    private boolean validateTanggalLahir(){
        String val = Objects.requireNonNull(mTanggalLahir.getEditText()).getText().toString();

        if (val.isEmpty()) {
            mTanggalLahir.setError("Tempat Lahir tidak boleh kosong!");
            return false;
        } else {
            mTanggalLahir.setError(null);
            return true;
        }
    }

    public void OnClickButtonUploadIdentitas(View view) {

        if (!validateName() | !validateNoKTP() | !validateTempatLahir()
                | !validateTanggalLahir()) {
            return;
        }

        String name = Objects.requireNonNull(mNamaLengkap.getEditText()).getText().toString();
        String noKTP = Objects.requireNonNull(mNoKTP.getEditText()).getText().toString();
        String jenisKelamin = mJenisKelamin.getText().toString();
        String tempatLahir = Objects.requireNonNull(mTempatLahir.getEditText()).getText().toString();
        String tanggalLahir = Objects.requireNonNull(mTanggalLahir.getEditText()).getText().toString();

        mDocRef.update("name", name);
        mDocRef.update("noKTP", noKTP);
        mDocRef.update("jenisKelamin", jenisKelamin);
        mDocRef.update("tempatLahir", tempatLahir);
        mDocRef.update("tanggalLahir", tanggalLahir);

        mDocRef.update("info_id", "done")
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(InfoIdentitasActivity.this,
                            "DATA IDENTITAS DIPERAHARUI!", Toast.LENGTH_SHORT).show();
                    finish();
                });

    }

    //-------------------------------------------------------------------------------------------//

    public void OnClickButtonCameraKTP(View view) {
        askCameraPermission();
    }

    private void askCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
        } else {
            openCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            }
        } else {
            Toast.makeText(this, "CAMERA PERMISSION REQUIRED!",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void openCamera() {
        Intent openCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (openCamera.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(openCamera, CAMERA_REQUEST_CODE);
        }
    }

    //-------------------------------------------------------------------------------------------//

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE) {

            if (data != null && data.getExtras() != null) {
                Bitmap imageKtp = (Bitmap) data.getExtras().get("data");

                if (imageKtp != null) {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    imageKtp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] dataImg = baos.toByteArray();

                    UploadTask uploadTask = mStorage.putBytes(dataImg);
                    uploadTask.addOnSuccessListener(taskSnapshot -> {
                        loadImage();
                        mProgressBarUpload.setProgress(100);
                        mProgressBarUpload.setAlpha(1);
                        Toast.makeText(InfoIdentitasActivity.this,
                            "UNGGAH BERHASIL " + taskSnapshot.getMetadata(),
                                Toast.LENGTH_LONG).show();
                    }).addOnProgressListener(taskSnapshot -> {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred())
                            / taskSnapshot.getTotalByteCount();
                        int loadProgress = (int)Math.round(progress);
                        mProgressBarUpload.setProgress(loadProgress);
                        mProgressBarUpload.setAlpha(1);
                    });
                }

            }

        }
    }

    //-------------------------------------------------------------------------------------------//

    public void ButtonBackArrow(View view) {
        finish();
    }

}
