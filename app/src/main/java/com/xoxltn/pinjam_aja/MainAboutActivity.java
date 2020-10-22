/*
 * Created by Albert Kristaen (s6joxx) on 10/22/20, 11:34 AM
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 6/23/20, 1:11 PM
 */

package com.xoxltn.pinjam_aja;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainAboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_about);
    }

    //-------------------------------------------------------------------------------------------//

    @Override
    public void onBackPressed() {
        finish();
    }
}
