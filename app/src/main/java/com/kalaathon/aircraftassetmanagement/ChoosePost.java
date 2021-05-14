package com.kalaathon.aircraftassetmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class ChoosePost extends AppCompatActivity {

    private CardView manager;
    private CardView mro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_post);
        manager=findViewById(R.id.asset_manager);
        mro=findViewById(R.id.mro_official);

        mro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChoosePost.this,MroLogin.class));
                finish();
            }
        });

        manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChoosePost.this,Manager_login.class));
                finish();
            }
        });

    }
}