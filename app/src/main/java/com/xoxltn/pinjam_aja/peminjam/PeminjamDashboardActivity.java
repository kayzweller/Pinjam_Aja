/*
 * Created by Albert Kristaen (s6joxx) on 2/10/21, 9:45 PM
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 10/22/20, 11:34 AM
 */

package com.xoxltn.pinjam_aja.peminjam;

import android.os.Bundle;
import android.os.Handler;
import android.widget.FrameLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.xoxltn.pinjam_aja.R;

public class PeminjamDashboardActivity extends AppCompatActivity {

    BottomNavigationView mPeminjamMainNav;
    FrameLayout mPeminjamFrame;

    private PeminjamHomeFragment mPeminjamHomeFragment;
    private PeminjamFundFragment mPeminjamFundFragment;
    private PeminjamNotifFragment mPeminjamNotifFragment;
    private PeminjamUserFragment mPeminjamUserFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peminjam_dashboard);

        mPeminjamFrame = findViewById(R.id.peminjam_main_frame);
        mPeminjamMainNav = findViewById(R.id.peminjam_main_nav);

        mPeminjamHomeFragment = new PeminjamHomeFragment();
        mPeminjamFundFragment = new PeminjamFundFragment();
        mPeminjamNotifFragment = new PeminjamNotifFragment();
        mPeminjamUserFragment = new PeminjamUserFragment();

        setFragment(mPeminjamHomeFragment);
        loadFragment();

    }

    //--------------------------------------------------------------------------------------------//

    private void setFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.peminjam_main_frame, fragment);
        fragmentTransaction.commit();
    }

    private void loadFragment() {
        mPeminjamMainNav.setOnNavigationItemSelectedListener(menuItem -> {

            switch (menuItem.getItemId()) {

                case R.id.peminjam_nav_home :
                    setFragment(mPeminjamHomeFragment);
                    return true;

                case R.id.peminjam_nav_fund :
                    setFragment(mPeminjamFundFragment);
                    return true;

                case R.id.peminjam_nav_notif :
                    setFragment(mPeminjamNotifFragment);
                    return true;

                case R.id.peminjam_nav_user :
                    setFragment(mPeminjamUserFragment);
                    return true;

                default:
                    return false;

            }

        });
    }

    //-------------------------------------------------------------------------------------------//

    // variabel untuk fungsi tombol BACK
    boolean doubleBackToExitPressedOnce = false;
    int DELAY_PRESS = 2000;

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

        new Handler().postDelayed(() -> doubleBackToExitPressedOnce=false, DELAY_PRESS);
    }

}
