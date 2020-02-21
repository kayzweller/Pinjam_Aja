package com.xoxltn.pinjam_aja;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.xoxltn.pinjam_aja.R;

public class LoginActivity extends AppCompatActivity {

    // variabel untuk fungsi tombol BACK
    boolean doubleBackToExitPressedOnce = false;
    int DELAY_PRESS = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    // method untuk tekan tombol BACK dua kali untuk keluar
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Tekan sekali lagi untuk keluar", Toast.LENGTH_SHORT)
                .show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, DELAY_PRESS);
    }
}
