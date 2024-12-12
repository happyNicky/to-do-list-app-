package com.example.to_do_list_app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class addNote extends AppCompatActivity {
    private EditText titleInput, description;
    private RelativeLayout main;
    private TextView noteTitleText, discriptionTitleText;
    private dataBase db;
    private Button saveBtn;
    private boolean fromSaveButton = false;
    private int theme = Color.WHITE;
    private String titleInputTxt;
    private String descriptionTxt;
    private String day;
    private dataModelClass dm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_note); // Ensure this is called first

        main = findViewById(R.id.main);
        db = new dataBase(this);
        noteTitleText = findViewById(R.id.noteTitleText);
        discriptionTitleText = findViewById(R.id.discriptionText);
        titleInput = findViewById(R.id.TitleInput);
        description = findViewById(R.id.discription);

        Intent intent = getIntent();
        day = intent.getStringExtra("day");
        theme = intent.getIntExtra("theme", Color.BLUE);
        saveBtn = findViewById(R.id.saveBtn);

        if (theme == Color.BLACK) {
            noteTitleText.setTextColor(Color.WHITE);
            discriptionTitleText.setTextColor(Color.WHITE);
            main.setBackgroundColor(theme);
        } else {
            noteTitleText.setTextColor(Color.BLACK);
            discriptionTitleText.setTextColor(Color.BLACK);
            main.setBackgroundColor(theme);
        }

        saveBtn.setOnClickListener(v -> {
            titleInputTxt = titleInput.getText().toString();
            descriptionTxt = description.getText().toString();
            if (titleInputTxt.isEmpty()) {
                titleInput.setError("you need a title");
            } else {
                dm = new dataModelClass(titleInputTxt, false, day, descriptionTxt);
                db.addOne(dm);
                fromSaveButton = true;
                onBackPressed();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            if (theme == Color.BLACK) {
                noteTitleText.setTextColor(Color.WHITE);
                discriptionTitleText.setTextColor(Color.WHITE);
                titleInput.setTextColor(Color.WHITE);
            }
            main.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void onBackPressed() {
        if (fromSaveButton) {
            super.onBackPressed();
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Confirmation Required")
                    .setMessage("Do you want to save this title?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            titleInputTxt = titleInput.getText().toString();
                            descriptionTxt = description.getText().toString();
                            dm = new dataModelClass(titleInputTxt, false, day, descriptionTxt);
                            db.addOne(dm);
                            fromSaveButton = true;
                            addNote.super.onBackPressed(); // Calling super onBackPressed correctly
                        }
                    })
                    .setNegativeButton("No", null)
                    .setIcon(R.drawable.about_icon)
                    .show();
        }
    }
}
