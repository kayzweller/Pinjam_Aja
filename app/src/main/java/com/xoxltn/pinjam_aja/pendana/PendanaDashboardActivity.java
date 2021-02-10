/*
 * Created by Albert Kristaen (s6joxx) on 2/10/21, 9:45 PM
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 10/22/20, 11:34 AM
 */

package com.xoxltn.pinjam_aja.pendana;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.xoxltn.pinjam_aja.R;
import com.xoxltn.pinjam_aja.peminjam.PeminjamNotifFragment;

public class PendanaDashboardActivity extends AppCompatActivity {

    BottomNavigationView mPendanaMainNav;
    FrameLayout mPendanaFrame;

    private PendanaHomeFragment mPendanaHomeFragment;
    private PendanaFundFragment mPendanaFundFragment;
    private PendanaNotifFragment mPendanaNotifFragment;
    private PendanaUserFragment mPendanaUserFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pendana_dashboard);

        mPendanaFrame = findViewById(R.id.pendana_main_frame);
        mPendanaMainNav = findViewById(R.id.pendana_main_nav);

        mPendanaHomeFragment = new PendanaHomeFragment();
        mPendanaFundFragment = new PendanaFundFragment();
        mPendanaNotifFragment = new PendanaNotifFragment();
        mPendanaUserFragment = new PendanaUserFragment();

        setFragment(mPendanaHomeFragment);
        loadFragment();

    }

    //-------------------------------------------------------------------------------------------//

    private void setFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.pendana_main_frame, fragment);
        fragmentTransaction.commit();
    }

    private void loadFragment() {
        mPendanaMainNav.setOnNavigationItemSelectedListener(menuItem -> {

            switch (menuItem.getItemId()) {
                case R.id.pendana_nav_home :
                    setFragment(mPendanaHomeFragment);
                    return true;

                case R.id.pendana_nav_fund :
                    setFragment(mPendanaFundFragment);
                    return true;

                case R.id.pendana_nav_notif :
                    setFragment(mPendanaNotifFragment);
                    return true;

                case R.id.pendana_nav_user :
                    setFragment(mPendanaUserFragment);
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
