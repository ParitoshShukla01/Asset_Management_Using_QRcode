package com.kalaathon.aircraftassetmanagement;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Manager_login extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mReference;
    String user,password;
    private EditText name,pass;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_login);
        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mReference=mFirebaseDatabase.getReference();
        name=findViewById(R.id.manager_name);
        pass=findViewById(R.id.manager_pass);
        btn=findViewById(R.id.manager_login);


        mReference.child("employee").child("manager").child("name").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    user=String.valueOf(task.getResult().getValue());
                    Log.e("TAG", "onComplete: "+user);
                    setuser(user);
                }
            }
        });

        mReference.child("employee").child("manager").child("password").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    password=String.valueOf(task.getResult().getValue());
                    Log.e("TAG", "onComplete: "+password);
                    setpass(password);
                }
            }
        });



    }

    private void setuser(String u) {
        this.user=u;
    }

    private void setpass(String p) {
        this.password=p;

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(name.getText().toString().equals(user) && pass.getText().toString().equals(password) && pass!=null && user!=null)
                {
                    startActivity(new Intent(Manager_login.this,Manager_dashborad.class));
                    Toast.makeText(Manager_login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("MyPref", 0);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putBoolean("login", true);
                    editor.apply();
                    finish();
                }
                else
                    Toast.makeText(Manager_login.this, "Please try again.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}