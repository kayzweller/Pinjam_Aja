/*
 * Created by Albert Kristaen (s6joxx) on 2/10/21, 9:45 PM
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 2/10/21, 9:39 PM
 */

package com.xoxltn.pinjam_aja;

import android.os.Looper;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainSplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_splash_screen);

        AnimationStart();
        DelayToWelcome();

    }

    //-------------------------------------------------------------------------------------------//

    private void AnimationStart() {

        // variabel
        Animation logoAnim, sloganAnim;
        ImageView logoImage;
        TextView judul, slogan;


        // animasi splash screen
        logoAnim = AnimationUtils.loadAnimation(this,R.anim.logo_anim);
        sloganAnim = AnimationUtils.loadAnimation(this,R.anim.slogan_anim);

        // delay animasi
        sloganAnim.setStartOffset(500);

        // tautan animasi ke object
        logoImage = findViewById(R.id.logo_splash);
        judul = findViewById(R.id.judul);
        slogan = findViewById(R.id.slogan);

        logoImage.setAnimation(logoAnim);
        judul.setAnimation(sloganAnim);
        slogan.setAnimation(sloganAnim);
    }

    private void DelayToWelcome() {

        // splash screen
        int SPLASH_SCREEN_DELAY = 5000;
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(MainSplashScreenActivity.
                    this, MainWelcomeActivity.class);
            startActivity(intent);
            finish();
        }, SPLASH_SCREEN_DELAY);
    }

    //-------------------------------------------------------------------------------------------//

    @Override
    public void onBackPressed() {
        // kunci fungsi tombol kembali waktu splash screen
    }

}
