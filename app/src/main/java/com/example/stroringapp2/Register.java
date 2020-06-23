package com.example.stroringapp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    EditText rname,remail,rpass,rrpass;
    Button bregister;
    TextView mloginbtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    ProgressBar pbar;
    String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        rname = findViewById(R.id.runame);
        remail = findViewById(R.id.remail);
        rpass = findViewById(R.id.rpass);
        rrpass = findViewById(R.id.rrpass);
        bregister = findViewById(R.id.bregister);
        mloginbtn = findViewById(R.id.already);
        pbar = findViewById(R.id.progressBar);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();


        if(fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        bregister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final String name=rname.getText().toString();
                final String email=remail.getText().toString().trim();
                final String pass=rpass.getText().toString().trim();
                String ripass=rrpass.getText().toString().trim();

                if(TextUtils.isEmpty(name)){
                    rname.setError("Username is required");
                    return;
                }
                if(TextUtils.isEmpty(email)){
                    remail.setError("Email is Required");
                    return;
                }
                if(TextUtils.isEmpty(pass)){
                    rpass.setError("Password is Required");
                    return;
                }
                if(TextUtils.isEmpty(ripass)){
                    rrpass.setError("Conform Password is Required");
                    return;
                }
                if(pass.equals(ripass)==false){
                    rrpass.setError("Password did not match");
                    return;
                }
                if(pass.length()<6){  rpass.setError("Password should be atleast 6 character long"); return;  }
                pbar.setVisibility(View.VISIBLE);

                // Now perform Registration

                fAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            uid = fAuth.getCurrentUser().getUid();
                            DocumentReference dr = fStore.collection("users").document(uid);
                            Map<String,ArrayList<String>> map = new HashMap<>();
                            dr.set(map);
                            Toast.makeText(Register.this,"Registration Successful",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Register.this,MainActivity.class));

                        }
                        else{
                            Toast.makeText(Register.this,"Sorry, Error Encounter."+ task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            pbar.setVisibility(View.INVISIBLE);
                        }
                    }
                });

            }
        });


        mloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });
    }
}
