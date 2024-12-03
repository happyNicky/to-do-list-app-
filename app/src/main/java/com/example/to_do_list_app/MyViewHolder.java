package com.example.to_do_list_app;

import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {
    EditText etTitle;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        etTitle=itemView.findViewById(R.id.monRow);
    }
}
