package com.kalaathon.aircraftassetmanagement;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth=FirebaseAuth.getInstance();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i;
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();
                boolean login = pref.getBoolean("login", false);
                if (login)
                {
                    i=new Intent(MainActivity.this,Manager_dashborad.class);
                }
                else if(mAuth.getCurrentUser()==null) {
                    i=new Intent(MainActivity.this,ChoosePost.class);
                } else {
                    i=new Intent(MainActivity.this,scan.class);
                }

                startActivity(i);
                finish();
            }
        }, 1000);
    }
}