package com.example.to_do_list_app;

import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {
    EditText etTitle;
    FrameLayout recyRow;
    CardView recyCard;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        etTitle=itemView.findViewById(R.id.monRow);
        recyRow=itemView.findViewById(R.id.recyROw);
        recyCard=itemView.findViewById(R.id.cardRecy);
    }
}
