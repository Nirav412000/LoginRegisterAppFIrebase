package com.example.stroringapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.accessibilityservice.AccessibilityService;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class AddRecordsPage<s> extends AppCompatActivity {
    EditText accname,accuname,accpass;
    ProgressBar pbr;
    Button addr;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String uid;
    ArrayList<String> ss1;
    ArrayList<String> ss2;
    ArrayList<String> ss3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_records_page);
        accname = findViewById(R.id.accname);
        accuname = findViewById(R.id.accuname);
        accpass = findViewById(R.id.accpass);
        pbr = findViewById(R.id.progressBar3);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        addr = findViewById(R.id.addr);

        addr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String s1=accname.getText().toString();
                final String s2=accuname.getText().toString();
                final String s3=accpass.getText().toString();

                if(TextUtils.isEmpty(s1)){
                    accname.setError("Required");
                    return;
                }
                if(TextUtils.isEmpty(s2)){
                    accname.setError("Required");
                    return;
                }
                if(TextUtils.isEmpty(s3)){
                    accname.setError("Required");
                    return;
                }

                uid = fAuth.getCurrentUser().getUid();
                final DocumentReference dr= fStore.collection("users").document(uid);
                pbr.setVisibility(View.VISIBLE);

                dr.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                   @Override
                   public void onSuccess(DocumentSnapshot documentSnapshot) {
                       if(documentSnapshot.exists()){
                           ss1 = (ArrayList<String>)documentSnapshot.get("accnames");
                           ss2 = (ArrayList<String>)documentSnapshot.get("accunames");
                           ss3 = (ArrayList<String>)documentSnapshot.get("accpasswords");

                           if(ss1 == null){
                               ss1 = new ArrayList<>();
                               ss2 = new ArrayList<>();
                               ss3 = new ArrayList<>();
                           }

                           ss1.add(s1);
                           ss2.add(s2);
                           ss3.add(s3);

                           Map<String,ArrayList<String>> mapp=new HashMap<>();
                           mapp.put("accnames",ss1);
                           mapp.put("accunames",ss2);
                           mapp.put("accpasswords",ss3);

                           dr.set(mapp).addOnSuccessListener(new OnSuccessListener<Void>() {
                               @Override
                               public void onSuccess(Void aVoid) {
                                   Toast.makeText(AddRecordsPage.this,"Record Added Succefully",Toast.LENGTH_SHORT).show();
                               }
                           });

                            pbr.setVisibility(View.INVISIBLE);
                       }
                   }
               });



            }
        });

    }
}
