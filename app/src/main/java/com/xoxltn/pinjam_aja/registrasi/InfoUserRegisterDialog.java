/*
 * Created by Albert Kristaen (Kayzweller) on 25/06/20 02.03
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 23/06/20 13.19
 */

package com.xoxltn.pinjam_aja.registrasi;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.xoxltn.pinjam_aja.MainRulesActivity;

public class InfoUserRegisterDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Perhatian!")
                .setMessage("Sebelum melanjutkan Registrasi, " +
                        "Anda diharapkan membaca Aturan dan Resiko " +
                        "serta menyetujuinya terlebih dahulu.")
                .setNegativeButton("Lanjut >", (dialog, which) -> {
                    Intent goToFaq = new Intent(getActivity(), MainRulesActivity.class);
                    startActivity(goToFaq);
                });

        return builder.create();
    }

}

