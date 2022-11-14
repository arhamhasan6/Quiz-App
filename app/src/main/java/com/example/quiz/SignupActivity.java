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

import com.example.quiz.databinding.ActivitySignupBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

public class SignupActivity extends AppCompatActivity {

    ActivitySignupBinding binding;
    private FirebaseAuth mAuth;
    FirebaseFirestore database;
    Button button;
    EditText semail,spass,sname,srefercode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // binding=ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_signup);
        semail = findViewById(R.id.email_s);
        spass = findViewById(R.id.password_s);
        sname = findViewById(R.id.name_s);
        srefercode = findViewById(R.id.code_s);
        button = findViewById(R.id.signup_button);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);

        button.setOnClickListener(view ->{
            createUser();
        });



       /** button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String email,pass,name,refercode;
                email = semail.getText().toString();
                pass = spass.getText().toString();
                name = sname.getText().toString();
                refercode = srefercode.getText().toString();


                user User = new user(name,email,pass,refercode);

                authorize.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(SignupActivity.this, email+pass, Toast.LENGTH_SHORT).show();
                            String uid = task.getResult().getUser().getUid();
                            database
                                    .collection("users")
                                    .document(uid)
                                    .set(User).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                startActivity(new Intent(SignupActivity.this,MainActivity.class));
                                                finish();
                                            }else{
                                                Toast.makeText(SignupActivity.this, task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT);
                                            }
                                        }
                                    });
                            Toast.makeText(SignupActivity.this, "Success", Toast.LENGTH_SHORT).show();

                        }else{
                            Toast.makeText(SignupActivity.this, task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT);
                        }
                    }
                });


            }
        });*/
    }
    private  void createUser(){
        String email=semail.getText().toString();
        String password=spass.getText().toString();
        String name = sname.getText().toString();
        String refercode = srefercode.getText().toString();
        if (TextUtils.isEmpty(email)){
            semail.setError("Email cannot be empty");
            semail.requestFocus();
        }else if (TextUtils.isEmpty(password)){
            spass.setError("Password cannot be empty");
            spass.requestFocus();
        }else{
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        user User = new user(name,email,password,refercode);
                        Toast.makeText(SignupActivity.this, "User Registered successfully", Toast.LENGTH_SHORT).show();
                        String uid = task.getResult().getUser().getUid();
                        database
                                .collection("users")
                                .document(uid)
                                .set(User).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            startActivity(new Intent(SignupActivity.this,MainActivity.class));
                                            finish();
                                        }else{
                                            Toast.makeText(SignupActivity.this, task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT);
                                        }
                                    }
                                });
                        startActivity((new Intent(SignupActivity.this,LoginActivity.class)));
                    }else{
                        Toast.makeText(SignupActivity.this, "Registration Error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}