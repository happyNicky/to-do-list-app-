package com.example.to_do_list_app;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class detailsSection extends AppCompatActivity {
private EditText descriptionTitle,descriptionDetail;
private Button goBackBtn;
  private  int theme= Color.WHITE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_details_section);

        descriptionTitle=findViewById(R.id.discriptionTitle);
        descriptionDetail=findViewById(R.id.discriptionBody);
        goBackBtn=findViewById(R.id.goBackBtn);

        Intent intent=getIntent();
        String title=intent.getStringExtra("title");
        String description=intent.getStringExtra("desicription");
        theme=intent.getIntExtra("theme",Color.WHITE);

        descriptionTitle.setText(title);
        descriptionDetail.setText(description);
        goBackBtn.setOnClickListener(v -> {
            onBackPressed();
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
           TextView textTitle=  v.findViewById(R.id.TitleTxt);
           TextView textDiscription=v.findViewById(R.id.discriptionTxt);

           if(theme==Color.BLACK)
           {  textTitle.setTextColor(Color.WHITE);
               textDiscription.setTextColor(Color.WHITE);
           }
           else
           {
               textTitle.setTextColor(Color.BLACK);
               textDiscription.setTextColor(Color.BLACK);
           }
            v.findViewById(R.id.main).setBackgroundColor(theme);
            return insets;
        });
    }
}