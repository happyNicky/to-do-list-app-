package com.example.to_do_list_app;

import static com.example.to_do_list_app.MainActivity.getDay;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.media.CamcorderProfile;
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
  private int backgroundColor= Color.WHITE;

    public daysDataAdapter(Context context, List<String> titles) {
        this.context = context;
        this.titles = titles;
    }
    private boolean isDark=false;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.fragment_days_row_layout,parent,false));
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if(isDark)
        {   holder.etTitle.setBackgroundColor(backgroundColor);
            holder.recyRow.setBackgroundColor(backgroundColor);
            holder.recyCard.setBackgroundColor(backgroundColor);
            holder.etTitle.setTextColor(Color.WHITE);
            GradientDrawable drawable = new GradientDrawable();
             drawable.setCornerRadius(16);
             drawable.setStroke(4, Color.parseColor("#656161"));
             holder.etTitle.setBackground(drawable);
        }
        else
        {   holder.etTitle.setBackgroundColor(backgroundColor);
            holder.recyRow.setBackgroundColor(backgroundColor);
            holder.recyCard.setBackgroundColor(backgroundColor);

            holder.etTitle.setTextColor(Color.BLACK);
        }
        holder.etTitle.setText(titles.get(position));
        holder.etTitle.setTag(position);
        // Set OnTouchListener for the EditText drawable end
        db=new dataBase(context.getApplicationContext());
        holder.etTitle.setOnTouchListener((v, event) -> {

            if (event.getAction() == MotionEvent.ACTION_UP)
        { if (event.getRawX() >= (holder.etTitle.getRight() - holder.etTitle.getCompoundDrawables()[2].getBounds().width()))
        {
            showConfirmationDialog(position);
            return true;
        }
        else if(event.getRawX()<(holder.etTitle.getRight()-holder.etTitle.getCompoundDrawables()[2].getBounds().width()))
        {   String details= db.titleDetails(holder.etTitle.getText().toString(), getDay());
            Intent intent=new Intent(context,detailsSection.class);
            intent.putExtra("title",holder.etTitle.getText().toString());
            intent.putExtra("desicription",details);
            intent.putExtra("theme",main.theme);
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
    public void changeBackgroundColor(int color)
    {   this.backgroundColor=color;
         if(color== Color.BLACK)
             isDark=true;
         else
             isDark=false;
        notifyDataSetChanged();

    }
    private void showConfirmationDialog( int position ) {
        new AlertDialog.Builder(context).setTitle("Confirmation Required")
                .setMessage("Are you sure you want to remove this task form your list?")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                               db.setCompleted(titles.get(position),getDay());
                                removeTitle(position);
                        } }) .setNegativeButton("Cancel", null)
                .setIcon(R.drawable.about_icon) .show();
    }

}
