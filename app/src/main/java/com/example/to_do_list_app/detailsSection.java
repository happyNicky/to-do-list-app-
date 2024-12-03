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

public class detailsSection extends AppCompatActivity {
private EditText descriptionTitle,descriptionDetail;
private Button goBackBtn;
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

        descriptionTitle.setText(title);
        descriptionDetail.setText(description);
        goBackBtn.setOnClickListener(v -> {
            onBackPressed();
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}