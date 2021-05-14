package com.kalaathon.aircraftassetmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

public class scan extends AppCompatActivity implements View.OnClickListener {

    private Button scanbtn;
    private String uid;
    private IntentIntegrator qrScan;
    private ImageView qrimg;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference;
    private ImageView mImageView;
    private ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mDatabaseReference=mFirebaseDatabase.getReference();
        mImageView=findViewById(R.id.logout);
        mProgressBar=findViewById(R.id.progress);

        scanbtn = (Button) findViewById(R.id.scan);
        qrimg=(ImageView)findViewById(R.id.qrimg);

        scanbtn.setOnClickListener(this);
        qrScan = new IntentIntegrator(this);

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(scan.this,MainActivity.class));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                try {
                    //converting the data to json
                    JSONObject obj = new JSONObject(result.getContents());
                    uid=obj.getString("UID");

                    Query query=mDatabaseReference.child("mro").orderByChild("uid").equalTo(uid);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if(snapshot.getChildrenCount()>0) {
                                Intent i = new Intent(scan.this, UpdateQr.class);
                                i.putExtra("uid", uid);
                                i.putExtra("new","false");
                                startActivity(i);
                                finish();
                            }

                            else
                            {
                                Intent i = new Intent(scan.this, UpdateQr.class);
                                i.putExtra("uid", uid);
                                i.putExtra("new","true");
                                startActivity(i);
                                finish();
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                    //encoded format not matches
                    Toast.makeText(this, "Not a valid QR code.", Toast.LENGTH_LONG).show();
                    scanbtn.setVisibility(View.VISIBLE);
                    mProgressBar.setVisibility(View.GONE);
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onClick(View view) {
        qrScan.initiateScan();
        scanbtn.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
    }
}