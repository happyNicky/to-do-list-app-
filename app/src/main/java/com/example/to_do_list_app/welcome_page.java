package com.example.to_do_list_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import android.content.Intent; import android.content.SharedPreferences;

public class welcome_page extends AppCompatActivity {

    private Intent intent;
    private EditText userName;
    private String userNameTxt;
    private Button nextBtn;
    private ProgressBar pg_bar;
    private dataBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.welcome_page);

        userName=findViewById(R.id.userName);
        nextBtn=findViewById(R.id.NextBtn);
        pg_bar=findViewById(R.id.Progress_bar);
        db= new dataBase(getApplicationContext());


        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("hasOpenedBefore", true);
        editor.apply();

        nextBtn.setOnClickListener(v -> {
            pg_bar.setVisibility(View.VISIBLE);
            userNameTxt=userName.getText().toString();
            db.addUser(userNameTxt);
            Intent intent = new Intent(welcome_page.this, MainActivity.class);
            intent.putExtra("userName",userNameTxt);
            startActivity(intent);
            finish();

        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}