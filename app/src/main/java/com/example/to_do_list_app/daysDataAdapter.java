package com.example.to_do_list_app;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class daysDataAdapter extends RecyclerView.Adapter<MyViewHolder> {
  private Context context;
  private List<String> titles;
  private View.OnTouchListener onTouchListener;
  private dataBase db;
  private MainActivity main;

    public daysDataAdapter(Context context, List<String> titles) {
        this.context = context;
        this.titles = titles;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.fragment_days_row_layout,parent,false));
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.etTitle.setText(titles.get(position));
        holder.etTitle.setTag(position);
        // Set OnTouchListener for the EditText drawable end
        db=new dataBase(context.getApplicationContext());
        holder.etTitle.setOnTouchListener((v, event) -> {

            if (event.getAction() == MotionEvent.ACTION_UP)
        { if (event.getRawX() >= (holder.etTitle.getRight() - holder.etTitle.getCompoundDrawables()[2].getBounds().width()))
        {   db.setCompleted(titles.get(position),main.getDay());
            removeTitle(position);
            return true;
        }
        else if(event.getRawX()<(holder.etTitle.getRight()-holder.etTitle.getCompoundDrawables()[2].getBounds().width()))
        {   String details= db.titleDetails(holder.etTitle.getText().toString(),main.getDay());
            Intent intent=new Intent(context,detailsSection.class);
            intent.putExtra("title",holder.etTitle.getText().toString());
            intent.putExtra("desicription",details);
          v.getContext().startActivity(intent);
        }
        }

            return false;
        });
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }
    public void removeTitle(int position) {
        titles.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, titles.size());
    }


}
