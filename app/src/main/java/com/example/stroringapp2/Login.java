package com.example.stroringapp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    EditText lemail,lpass;
    Button blogin;
    TextView createac;
    ProgressBar pbr;
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        lemail =findViewById(R.id.lemail);
        lpass =findViewById(R.id.lpass);
        createac =findViewById(R.id.create);
        blogin =findViewById(R.id.blogin);
        pbr =findViewById(R.id.progressBar2);
        fAuth = FirebaseAuth.getInstance();

        if(fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
        blogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=lemail.getText().toString().trim();
                String pass=lpass.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    lemail.setError("Email is Required");
                    return;
                }
                if(TextUtils.isEmpty(pass)){
                    lpass.setError("Password is Required");
                    return;
                }
                pbr.setVisibility(View.VISIBLE);

                //Authentication of the User

                fAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Login.this,"Login Successful",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                        else{
                            Toast.makeText(Login.this,"Login Failed. Email or Password may be wrong",Toast.LENGTH_SHORT).show();
                            pbr.setVisibility(View.INVISIBLE);
                        }
                    }
                });
            }
        });

        createac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });
    }
}
