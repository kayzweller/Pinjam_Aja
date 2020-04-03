package com.xoxltn.pinjam_aja.peminjam_system;

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

public class PeminjamDashboardActivity extends AppCompatActivity {

    BottomNavigationView mPeminjamMainNav;
    FrameLayout mPeminjamFrame;

    private PeminjamHomeFragment mPeminjamHomeFragment;
    private PeminjamFundFragment mPeminjamFundFragment;
    private PeminjamUserFragment mPeminjamUserFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peminjam_dashboard);

        mPeminjamFrame = findViewById(R.id.peminjam_main_frame);
        mPeminjamMainNav = findViewById(R.id.peminjam_main_nav);

        mPeminjamHomeFragment = new PeminjamHomeFragment();
        mPeminjamFundFragment = new PeminjamFundFragment();
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
        mPeminjamMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView
                .OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {

                    case R.id.peminjam_nav_home :
                        setFragment(mPeminjamHomeFragment);
                        return true;

                    case R.id.peminjam_nav_fund :
                        setFragment(mPeminjamFundFragment);
                        return true;

                    case R.id.peminjam_nav_user :
                        setFragment(mPeminjamUserFragment);
                        return true;

                    default:
                        return false;

                }

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

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, DELAY_PRESS);
    }

}
