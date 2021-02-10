/*
 * Created by Albert Kristaen (s6joxx) on 2/10/21, 9:45 PM
 * Copyright (c) 2021 . All rights reserved.
 * Last modified 10/22/20, 11:34 AM
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
