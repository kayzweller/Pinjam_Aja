/*
 * Copyright (c) 2020 Albert Kristaen (DBC 113 008)
 * ONLY USE UNDER PERMISSION -OR- I AM GONNA CHOP YOUR HANDS OFF!
 */

package com.xoxltn.pinjam_aja;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;
import com.xoxltn.pinjam_aja.models.DashboardScreenItem;
import com.xoxltn.pinjam_aja.adapters.DashboardViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    // variabel

    ViewPager screenPager;
    TabLayout tabIndicator;
    Button btnNext;
    Button btnDaftar;
    Animation btnDaftarAnim;
    int position = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // call activity checking!
        SecondComing();

        // Welcome list view
        WelcomeBriefing();

        // "DAFTAR SEKARANG" button listener
        DaftarButtonListener();

    }

    //-------------------------------------------------------------------------------------------//

    // when this activity about to launch, check does this activity had been opened before
    private void SecondComing() {

        if (restorePrefsData()) {
            Intent loginActivity = new Intent(DashboardActivity.
                    this, SignUpActivity.class);
            startActivity(loginActivity);
            finish();
        }
    }

    // check does this apps ever been opened before?
    private boolean restorePrefsData() {

        SharedPreferences pref = getApplicationContext().
                getSharedPreferences("myPref", MODE_PRIVATE);
        return pref.getBoolean("isIntroOpened", false);
    }

    // saving the value that this apps even been opened before!
    private void savePrefsData() {

        SharedPreferences pref = getApplicationContext().
                getSharedPreferences("myPref", MODE_PRIVATE);
        SharedPreferences.Editor editor  =  pref.edit();
        editor.putBoolean("isIntroOpened", true);
        editor.apply();

    }

    //-------------------------------------------------------------------------------------------//

    private void WelcomeBriefing() {

        // informasi dalam list screen
        final List<DashboardScreenItem> mList = new ArrayList<>();
        mList.add(new DashboardScreenItem(R.drawable.welcome_1,
                "REGISTRASI MUDAH",
                "REGISTRASI AKUN DENGAN MENGGUNAKAN EMAIL DAN NO. HANDPHONE, " +
                        "KEMUDIAN LENGKAPI DATA ANDA UNTUK DAPAT MENGAJUKAN PINJAMAN"));
        mList.add(new DashboardScreenItem(R.drawable.welcome_2,
                "DATA PENGGUNA AMAN",
                "HANYA DATA YANG DIPERBOLEHKAN MENURUT ATURAN OJK YANG DAPAT " +
                        "DIAKSES OLEH PLATFORM DAN PENDANA"));
        mList.add(new DashboardScreenItem(R.drawable.welcome_3,
                "MUDAH DIAKSES",
                "NIKMATI KEMUDAHAN MENJADI PENDANA ATAUPUN PEMINJAM DALAM " +
                        "PLATFORM KAMI"));

        // setup viewpager
        DashboardViewPagerAdapter dashboardViewPagerAdapter;

        screenPager = findViewById(R.id.screen_viewpager);
        dashboardViewPagerAdapter = new DashboardViewPagerAdapter(this, mList);
        screenPager.setAdapter(dashboardViewPagerAdapter);

        // bottom part, IYKWIM!
        tabIndicator = findViewById(R.id.tab_indicator);
        btnNext = findViewById(R.id.button_next);
        btnDaftar = findViewById(R.id.button_daftar);

        // setup tab layout with viewpager
        tabIndicator.setupWithViewPager(screenPager);
        tabIndicator.setSelectedTabIndicator(0); // hide the ass of that ugly tab indicator

        // tap on that "NEXT" button listener
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                position = screenPager.getCurrentItem();
                if (position < mList.size()) {
                    position++;
                    screenPager.setCurrentItem(position);
                }
                if (position == mList.size()-1) { // method pas udah nyampe list terakhir

                    // panggil method untuk sembunyikan tombol next dan indikator tab
                    loadLastScreen();

                }
            }
        });

        // tabLayout and change listener
        tabIndicator.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == mList.size()-1) {
                    loadLastScreen();
                } else {
                    unloadLastScreen();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    //-------------------------------------------------------------------------------------------//

    // METHOD FOR SHOWING THE REGISTRATION BUTTON
    // ALSO HIDE TAB INDICATOR AND THAT TREMENDOUS UGLY NEXT BUTTON!!
    private void loadLastScreen() {

        btnDaftar.setVisibility(View.VISIBLE);
        btnNext.setVisibility(View.INVISIBLE);
        tabIndicator.setVisibility(View.VISIBLE);

        // ANIMASI PADA TOMBOL "DAFTAR SEKARANG"
        btnDaftarAnim = AnimationUtils.
                loadAnimation(this, R.anim.btn_daftar_anim);
        btnDaftarAnim.setStartOffset(100);
        btnDaftar = findViewById(R.id.button_daftar);
        btnDaftar.setAnimation(btnDaftarAnim);
    }

    // METHOD FOR SHOWING BACK THE REGISTRATION BUTTON
    // ALSO WITH THAT TAB INDICATOR AND THAT TREMENDOUS UGLY NEXT BUTTON!!
    private void unloadLastScreen() {

        btnDaftar.setVisibility(View.INVISIBLE);
        btnNext.setVisibility(View.VISIBLE);
        tabIndicator.setVisibility(View.VISIBLE);
    }

    //-------------------------------------------------------------------------------------------//

    private void DaftarButtonListener() {

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // menuju login activity
                Intent loginActivity = new Intent(DashboardActivity.
                        this, SignUpActivity.class);
                startActivity(loginActivity);

                // save boolean value to storage, soo next time user run the apps
                // we could know that he/she already checked the intro screen activity
                // using shared preference.

                // save value to the isIntroOpened = true
                savePrefsData();

                // call finish() after an intent you can't go back to the previous activity
                // with the "back" button
                finish();

            }
        });
    }

    //-------------------------------------------------------------------------------------------//

    @Override
    public void onBackPressed() {
        // lock back button
    }

}