package com.example.quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.quiz.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    FirebaseAuth mAuth;
    Button button;
    EditText semail,spass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        button = findViewById(R.id.login_btn);
        mAuth = FirebaseAuth.getInstance();
        semail = findViewById(R.id.email);
        spass = findViewById(R.id.password);

        if(mAuth.getCurrentUser()!=null){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

        button.setOnClickListener(view ->{
            createUser();
        });

        /**button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email , pass;
                email = binding.email.getText().toString();
                pass = binding.password.getText().toString();

                auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();

                        }else{
                            Toast.makeText(LoginActivity.this, task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT);
                        }

                    }
                });
            }
        });*/

        binding.cAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,SignupActivity.class));
                finish();
            }
        });
    }
    private  void createUser(){
        String email=binding.email.getText().toString();
        String password=binding.password.getText().toString();

        if (TextUtils.isEmpty(email)){
            semail.setError("Email cannot be empty");
            semail.requestFocus();
        }else if (TextUtils.isEmpty(password)){
            spass.setError("Password cannot be empty");
            spass.requestFocus();
        }else{
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(LoginActivity.this, "User logged in successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }else{
                        Toast.makeText(LoginActivity.this, "Log in Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

}