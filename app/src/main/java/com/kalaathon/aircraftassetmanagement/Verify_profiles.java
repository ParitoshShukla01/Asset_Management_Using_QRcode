package com.kalaathon.aircraftassetmanagement;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kalaathon.aircraftassetmanagement.models.User;

import java.util.ArrayList;
import java.util.List;

public class Verify_profiles extends AppCompatActivity {

    private UserListAdapter mAdapter;
    private ListView mListView;
    private ProgressBar listprogress;
    private RelativeLayout mRelativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_profiles);

        listprogress = findViewById(R.id.listprogress);
        listprogress.setVisibility(View.VISIBLE);
        mListView = findViewById(R.id.list_verify);
        mRelativeLayout=findViewById(R.id.rel_blank);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        reference.child("employee").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<User> senduser = new ArrayList<>();
                for (DataSnapshot singleSnapshot : snapshot.getChildren()) {
                    Log.e("TAG", "onDataChange: "+ singleSnapshot.getValue(User.class).getVerify());
                    if(singleSnapshot.getValue(User.class).getVerify().equals("false"))
                        senduser.add(singleSnapshot.getValue(User.class));
                }
                listprogress.setVisibility(View.GONE);
                if(senduser.isEmpty())
                {
                    mListView.setVisibility(View.GONE);
                    mRelativeLayout.setVisibility(View.VISIBLE);
                }
                else
                    updateUsersList(senduser);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void updateUsersList(List<User> senduser) {
        try {
            mAdapter = new UserListAdapter(Verify_profiles.this, R.layout.listprofiles, senduser);
            mListView.setAdapter(mAdapter);
            listprogress.setVisibility(View.GONE);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}