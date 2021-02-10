/*
 * Created by Albert Kristaen (s6joxx) on 2/10/21, 9:45 PM
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 10/22/20, 11:34 AM
 */

package com.xoxltn.pinjam_aja.registrasi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.xoxltn.pinjam_aja.R;

public class InfoUserProfileActivity extends AppCompatActivity {

    Button mButtonDataIdentitas, mButtonDataPribadi, mButtonDataPekerjaan, mButtonDataKontak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_user_profile);

        mButtonDataIdentitas = findViewById(R.id.button_lengkapi_identitas);
        mButtonDataPribadi = findViewById(R.id.button_lengkapi_pribadi);
        mButtonDataPekerjaan = findViewById(R.id.button_lengkapi_pekerjaan);
        mButtonDataKontak = findViewById(R.id.button_lengkapi_kontak);

    }

    //-------------------------------------------------------------------------------------------//

    public void ButtonLengkapiIdentitas(View view) {
        Intent GoToIdentitas = new Intent(this, InfoIdentitasActivity.class);
        startActivity(GoToIdentitas);
    }

    public void ButtonLengkapiPribadi(View view) {
        Intent GoToPribadi = new Intent(this, InfoPribadiActivity.class);
        startActivity(GoToPribadi);
    }

    public void ButtonLengkapiPekerjaan(View view) {
        Intent GoToPekerjaan = new Intent(this, InfoPekerjaanActivity.class);
        startActivity(GoToPekerjaan);
    }

    public void ButtonLengkapiKontak(View view) {
        Intent GoToKontak = new Intent(this, InfoKontakActivity.class);
        startActivity(GoToKontak);
    }

    public void ButtonLengkapiRekening(View view) {
        Intent GoToRekening = new Intent(this, InfoRekeningActivity.class);
        startActivity(GoToRekening);
    }

    //-------------------------------------------------------------------------------------------//

    public void ButtonBackArrow(View view) {
        finish();
    }
}
