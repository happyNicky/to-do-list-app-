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
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class loginPage extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private Intent intent;
    private EditText email,password;
    private String txtEmail,txtPassword;
    private TextView signUpTxtView;
    private Button loginBtn;
    private ProgressBar pg_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_page);

        email=findViewById(R.id.emailEditTxt);
        password=findViewById(R.id.passwordEditTxt);
        loginBtn=findViewById(R.id.loginButton);
        signUpTxtView=findViewById(R.id.sign_up_button);
        pg_bar=findViewById(R.id.Progress_bar);
        mAuth=FirebaseAuth.getInstance();



        loginBtn.setOnClickListener(v -> {
            pg_bar.setVisibility(View.VISIBLE);
            txtEmail=email.getText().toString();
            txtPassword=password.getText().toString();

            boolean checkValidEmail= utilClass.isValidEmail(txtEmail);
            if(txtEmail.isEmpty())
                email.setError("invalid email!");
            else if(txtPassword.isEmpty())
                password.setError("invalid password!");
            else if(!checkValidEmail)
            {
                email.setError("invalid email!");
                email.setText("");
            }
            else
            {
                mAuth.signInWithEmailAndPassword(txtEmail,txtPassword).addOnCompleteListener(loginPage.this,task -> {
                    if(task.isSuccessful())
                    {   pg_bar.setVisibility(View.GONE);
                        intent= new Intent(loginPage.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                    else
                    {     pg_bar.setVisibility(View.GONE);
                        Toast.makeText(this, "incorrect email or password", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });

        signUpTxtView.setOnClickListener(v -> {

            intent=new Intent(loginPage.this,signUpPage.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}