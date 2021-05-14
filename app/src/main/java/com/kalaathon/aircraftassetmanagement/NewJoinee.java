package com.kalaathon.aircraftassetmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewJoinee extends AppCompatActivity {

    private EditText name,phno;
    private Button mButton;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mFirebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_joinee);

        String str = getIntent().getStringExtra("phno");
        mFirebaseAuth=FirebaseAuth.getInstance();
        mFirebaseAuth.signOut();

        name=findViewById(R.id.name_reg);
        phno=findViewById(R.id.phno_reg);
        mButton=findViewById(R.id.submit_reg);
        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mDatabaseReference=mFirebaseDatabase.getReference();

        phno.setText(str);
        phno.setEnabled(false);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String tname,tphno;

                tname=name.getText().toString();
                tphno=phno.getText().toString();

                if(tname!=null)
                {
                    mDatabaseReference.child("employee").child(tphno).child("empid").setValue(tphno);
                    mDatabaseReference.child("employee").child(tphno).child("name").setValue(tname);
                    mDatabaseReference.child("employee").child(tphno).child("verify").setValue("false");

                    Intent intent = new Intent(NewJoinee.this, MroLogin.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Toast.makeText(NewJoinee.this, "Profile submitted for verification.", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    finish();

                }
                else
                {
                    Toast.makeText(NewJoinee.this, "Please fill all fields !", Toast.LENGTH_SHORT).show();

                }

            }
        });


    }
}