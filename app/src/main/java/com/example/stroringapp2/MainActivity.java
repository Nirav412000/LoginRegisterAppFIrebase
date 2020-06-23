package com.example.stroringapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    Button addrecord,getrecords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addrecord = findViewById(R.id.addrecord);
        getrecords = findViewById(R.id.getrecords);

        addrecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,AddRecordsPage.class));
            }
        });

        getrecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,GetRecordsPage.class));
            }
        });

    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(MainActivity.this, "Logout Succefully", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(MainActivity.this,Login.class));
        finish();
    }
}
