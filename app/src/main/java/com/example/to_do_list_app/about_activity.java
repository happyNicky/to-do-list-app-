package com.example.to_do_list_app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class about_activity extends AppCompatActivity {
   private ImageButton goBack;
   private Button readMore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_about);
        goBack=findViewById(R.id.goBackBtn);
        readMore=findViewById(R.id.readMoreBtn);

        goBack.setOnClickListener(v -> {
            onBackPressed();
        });

        readMore.setOnClickListener(v -> {
            String gitHubUrl="https://github.com/happyNicky";
            Intent intent= new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(gitHubUrl));
            startActivity(intent);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}