package com.kalaathon.aircraftassetmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kalaathon.aircraftassetmanagement.models.Records;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Past_records extends AppCompatActivity {

    private TextView name,number,uid;
    private DataListAdapter mAdapter;
    private ListView recordlist;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_records);

        String str= getIntent().getStringExtra("uid");
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        name=findViewById(R.id.asset_name);
        number=findViewById(R.id.aircraft_number);
        uid=findViewById(R.id.uid);
        recordlist=findViewById(R.id.record_list);
        mProgressBar=findViewById(R.id.records_progress);

        reference.child("mro").child(str).child("asset").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    name.setText("Asset name: "+String.valueOf(task.getResult().getValue()));
                }
            }
        });

        reference.child("mro").child(str).child("number").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    number.setText("Aircraft number: "+String.valueOf(task.getResult().getValue()));
                }
            }
        });

        uid.setText("U.I.D: "+str);

        reference.child("manager").child(str).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Records> records=new ArrayList<>();
                for (DataSnapshot singleSnapshot : snapshot.getChildren())
                {
                    records.add(singleSnapshot.getValue(Records.class));
                }
                Collections.sort(records, new Comparator<Records>() {
                    @Override
                    public int compare(Records o1, Records o2) {
                        return o2.getDate().compareTo(o1.getDate());
                    }
                });
                updateUsersList(records);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void updateUsersList(List<Records> senduser) {
        try {
            mAdapter = new DataListAdapter(Past_records.this, R.layout.listdata, senduser);
            recordlist.setAdapter(mAdapter);
            mProgressBar.setVisibility(View.GONE);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Past_records.this,Manager_dashborad.class));
        finish();
        super.onBackPressed();
    }
}