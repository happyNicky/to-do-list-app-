package com.example.to_do_list_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class addNote extends AppCompatActivity {
  private EditText titleInput, description;
  private dataBase db;
  private Button saveBtn;
  private dataModelClass dm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        db=new dataBase(this);
        setContentView(R.layout.activity_add_note);
        titleInput=findViewById(R.id.TitleInput);
        description=findViewById(R.id.discription);
        Intent intent=getIntent();
        String day= intent.getStringExtra("day");
        saveBtn= findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(v -> {

            String titleInputTxt=titleInput.getText().toString();
            String descriptionTxt=description.getText().toString();
            if(titleInputTxt.isEmpty())
            {
                titleInput.setError("you need title");
            }
            else
            {
                dm=new dataModelClass(titleInputTxt,false,day,descriptionTxt);
                db.addOne(dm);
                onBackPressed();
            }


        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}