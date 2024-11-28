package com.example.to_do_list_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signUpPage extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText email,password,userName;
    private String txtEmail,txtPassword,txtUserName;
    private Button signUpButton;
    private ProgressBar pg_bar;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up_page);

        email=findViewById(R.id.emailEditTxtSignUpPage);
        password=findViewById(R.id.passwordEditTxtSignUpPage);
        userName=findViewById(R.id.userNameEditTxtSignUpPage);
        signUpButton=findViewById(R.id.sign_up_button);
        pg_bar=findViewById(R.id.progress_bar);
        mAuth=FirebaseAuth.getInstance();



        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pg_bar.setVisibility(View.VISIBLE);
                txtEmail=email.getText().toString();
                txtPassword=password.getText().toString();
                txtUserName=userName.getText().toString();
              boolean checkValidEmail= utilClass.isValidEmail(txtEmail);
              if (txtEmail.isEmpty())
                 email.setError("invalid email!");
              else if(txtPassword.isEmpty())
                  password.setError("invalid password!");
              else if (txtUserName.isEmpty())
                  userName.setError("invalid user name!");
              else if(!checkValidEmail)
              {
                  email.setError("invalid email!");
                  email.setText("");
              }
              else
              {

                  mAuth.createUserWithEmailAndPassword(txtEmail,txtPassword).addOnCompleteListener(signUpPage.this,
                          task -> {
                              if(task.isSuccessful())
                              {   pg_bar.setVisibility(View.GONE);
                                  intent= new Intent(signUpPage.this, MainActivity.class);
                                  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                  startActivity(intent);

                              }
                              else
                              {
                                  Toast.makeText(signUpPage.this, "signed up failled !", Toast.LENGTH_SHORT).show();
                                  pg_bar.setVisibility(View.GONE);
                              }
                          });
              }
            }
        });




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
     }
