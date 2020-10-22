/*
 * Created by Albert Kristaen (s6joxx) on 10/22/20, 11:34 AM
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 6/25/20, 2:17 AM
 */

package com.xoxltn.pinjam_aja.pendana;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.xoxltn.pinjam_aja.R;

import java.text.DateFormat;
import java.util.Date;

public class AdapterFundReq extends FirestoreRecyclerAdapter<ModelFundReq,
        AdapterFundReq.FundReqHolder> {

    private OnItemClickListener listener;

    public AdapterFundReq(@NonNull FirestoreRecyclerOptions<ModelFundReq> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull FundReqHolder holder,int position,
                                    @NonNull ModelFundReq model) {

        Date date = model.getPinjaman_tanggal_req();
        String date_now = DateFormat.getDateInstance(DateFormat.FULL).format(date);
        holder.mTextViewTanggal.setText(date_now);

        int pinjaman = model.getPinjaman_besar();
        switch (pinjaman) {
            case 500000: {
                String pinjaman_now = "Rp. 500.000,-";

                holder.mTextViewNominalPinjaman.setText(pinjaman_now);
                holder.mCardFundRef.setCardBackgroundColor(0xFFEDEFD3);
                break;
            }
            case 1000000: {
                String pinjaman_now = "Rp. 1.000.000,-";
                holder.mTextViewNominalPinjaman.setText(pinjaman_now);
                holder.mCardFundRef.setCardBackgroundColor(0xFFD3EFEB);

                break;
            }
            case 1500000: {
                String pinjaman_now = "Rp. 1.500.000,-";
                holder.mTextViewNominalPinjaman.setText(pinjaman_now);
                holder.mCardFundRef.setCardBackgroundColor(0xFFEFD3DC);
                break;
            }
        }

    }

    @NonNull
    @Override
    public FundReqHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pendana_fund_req,
                parent, false);
        return new FundReqHolder(v);
    }

    //-------------------------------------------------------------------------------------------//

    class FundReqHolder extends RecyclerView.ViewHolder {
        TextView mTextViewTanggal;
        TextView mTextViewNominalPinjaman;
        CardView mCardFundRef;

        FundReqHolder(@NonNull final View itemView) {
            super(itemView);

            mTextViewTanggal = itemView.findViewById(R.id.home_tanggal_permintaan);
            mTextViewNominalPinjaman = itemView.findViewById(R.id.home_nominal_pinjaman);
            mCardFundRef = itemView.findViewById(R.id.home_req_card);

            // ON CLICK LISTENER
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }

                }
            });
        }
    }

    // sent on click to activity
    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
