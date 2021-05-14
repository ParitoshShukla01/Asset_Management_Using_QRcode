package com.kalaathon.aircraftassetmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.skydoves.powerspinner.PowerSpinnerView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class UpdateQr extends AppCompatActivity {

    private PowerSpinnerView operations,status;
    private EditText name,number,uid;
    private Button submit;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mReference;
    private String newname,newnumber,check;
    private ProgressBar mProgressBar;
    private String time;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_qr);
        time=getTimestamp();
        String str = getIntent().getStringExtra("uid");
        check=getIntent().getStringExtra("new");
        String[] arr_operations=getResources().getStringArray(R.array.operation);
        String[] arr_status=getResources().getStringArray(R.array.status);

        operations=findViewById(R.id.operation);
        status=findViewById(R.id.status);
        name=findViewById(R.id.asset_name);
        uid=findViewById(R.id.uid);
        number=findViewById(R.id.aircraft_number);
        submit=findViewById(R.id.submit);
        mProgressBar=findViewById(R.id.progress);

        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mReference=mFirebaseDatabase.getReference();

        uid.setText(str);
        uid.setEnabled(false);

        if(check.equals("false"))
        {

            mReference.child("mro").child(str).child("asset").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {

                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                    }
                    else {
                        newname=String.valueOf(task.getResult().getValue());
                        name.setText(newname);
                    }
                }
            });

            mReference.child("mro").child(str).child("number").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {

                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                    }
                    else {
                        newnumber=String.valueOf(task.getResult().getValue());
                        number.setText(newnumber);
                    }
                }
            });
            name.setEnabled(false);
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String tname,tnumber,tuid,empid;
                int toperations,tstatus;

                tname=name.getText().toString();
                tnumber=number.getText().toString();
                tuid=uid.getText().toString();

                toperations=operations.getSelectedIndex();
                tstatus=status.getSelectedIndex();
                empid= FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber().toString().substring(3);



                if(tname!=null && tnumber!=null && tuid!=null && toperations!=-1 && tstatus!=-1)
                {
                    mProgressBar.setVisibility(View.VISIBLE);
                    submit.setVisibility(View.GONE);

                    if(newnumber !=null && !newnumber.equals(tnumber) )
                        mReference.child("mro").child(tuid).child("number").setValue(tnumber);

                    if(check.equals("true"))
                    {
                        mReference.child("mro").child(tuid).child("asset").setValue(tname);
                        mReference.child("mro").child(tuid).child("number").setValue(tnumber);
                        mReference.child("mro").child(tuid).child("uid").setValue(tuid);
                    }

                    mReference.child("manager").child(tuid).child(time).child("date").setValue(time);
                    mReference.child("manager").child(tuid).child(time).child("empid").setValue(empid);
                    mReference.child("manager").child(tuid).child(time).child("operation").setValue(arr_operations[toperations].toString());
                    mReference.child("manager").child(tuid).child(time).child("status").setValue(arr_status[tstatus].toString());

                }
                else
                    Toast.makeText(UpdateQr.this, "Please fill all fields.", Toast.LENGTH_SHORT).show();

                Toast.makeText(UpdateQr.this, "Asset information updated", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(UpdateQr.this,scan.class));
                finish();
            }
        });



    }
    private String getTimestamp(){
        CustomTimeStamp obj=new CustomTimeStamp();
        try {
            this.time=obj.printTime(UpdateQr.this);
        } catch (IOException e) {
            e.printStackTrace();
            Calendar c=Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'_'HH:mm:ss");
            sdf.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
            Date date=c.getTime();
            this.time= sdf.format(date);
        }
        return time;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(UpdateQr.this,scan.class));
        finish();
        super.onBackPressed();
    }
}