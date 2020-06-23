package com.example.stroringapp2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class GetRecordsPage extends AppCompatActivity {
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    RecyclerView AccountList;
    String uid;
    ArrayList<String> ss1;
    ArrayList<String> ss2;
    ArrayList<String> ss3;
    String[][] str = {{"Empty","Empty","Empty"}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_records_page);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        AccountList = findViewById(R.id.AccountList);
        uid = fAuth.getCurrentUser().getUid();


        final DocumentReference dr= fStore.collection("users").document(uid);
        dr.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    ss1 = (ArrayList<String>)documentSnapshot.get("accnames");
                    ss2 = (ArrayList<String>)documentSnapshot.get("accunames");
                    ss3 = (ArrayList<String>)documentSnapshot.get("accpasswords");


                    if(ss1 != null){
                        str = new String[ss1.size()][3];

                        for (int i = 0; i < ss1.size(); i++) {
                            str[i][0] = ss1.get(i);
                            str[i][1] = ss2.get(i);
                            str[i][2] = ss3.get(i);
                        }
                    }

                    AccountList.setLayoutManager(new LinearLayoutManager(GetRecordsPage.this));
                    AccountList.setAdapter(new AccountHolder(str));
                }
            }
        });

            }
}
