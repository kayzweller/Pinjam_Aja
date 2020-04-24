package com.xoxltn.pinjam_aja;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class PembayaranActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran);
    }

    //-------------------------------------------------------------------------------------------//

    public void onCLickKonfimasiBayar(View view) {
        //TODO :: BUAT KONFIRMASI BAYAR
        Toast.makeText(this, "Permintaan konfirmasi transfer terkirim!", Toast.LENGTH_SHORT).show();
    }

    //-------------------------------------------------------------------------------------------//

    public void ButtonBackArrow(View v) {
        finish();
    }
    //-------------------------------------------------------------------------------------------//

    @Override
    public void onBackPressed() {
        finish();
    }

}
