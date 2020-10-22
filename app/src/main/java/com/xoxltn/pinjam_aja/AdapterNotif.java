/*
 * Created by Albert Kristaen (s6joxx) on 10/22/20, 11:34 AM
 * Copyright (c) 2020 . All rights reserved.
 * Last modified 7/5/20, 2:46 AM
 */

package com.xoxltn.pinjam_aja;

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

import java.text.DateFormat;
import java.util.Date;

public class AdapterNotif extends FirestoreRecyclerAdapter
        <ModelNotif, AdapterNotif.NotifHolder> {

    private OnItemClickListener listener;

    public AdapterNotif(@NonNull FirestoreRecyclerOptions<ModelNotif> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull NotifHolder holder,int position,
                                    @NonNull ModelNotif model) {

        boolean type = model.getNotif_type();
        if (type) {
            holder.mNotifCard.setCardBackgroundColor(0xFFDCEDC8);
        } else {
            holder.mNotifCard.setCardBackgroundColor(0xFFFFC2BC);
        }

        String title = model.getNotif_title();
        holder.mTextNotifTitle.setText(title);

        Date date = model.getNotif_date();
        String date_call = DateFormat.getDateInstance(DateFormat.FULL).format(date);
        holder.mTextNotifDate.setText(date_call);

        String detail = model.getNotif_detail();
        holder.mTextNotifDetail.setText(detail);
    }

    @NonNull
    @Override
    public NotifHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_notif,
                parent, false);
        return new NotifHolder(v);
    }

    //-------------------------------------------------------------------------------------------//

    class NotifHolder extends RecyclerView.ViewHolder {

        TextView mTextNotifTitle;
        TextView mTextNotifDate;
        TextView mTextNotifDetail;
        CardView mNotifCard;

        public NotifHolder(@NonNull View itemView) {
            super(itemView);

            mTextNotifTitle = itemView.findViewById(R.id.text_notif_title);
            mTextNotifDate = itemView.findViewById(R.id.text_notif_date);
            mTextNotifDetail = itemView.findViewById(R.id.text_notif_detail);
            mNotifCard = itemView.findViewById(R.id.notif_card);

            // ON CLICK LISTENER
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onItemClick(getSnapshots().getSnapshot(position), position);
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
