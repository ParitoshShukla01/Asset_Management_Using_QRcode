package com.kalaathon.aircraftassetmanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.kalaathon.aircraftassetmanagement.models.Records;

import java.util.List;


public class DataListAdapter extends ArrayAdapter<Records> {

    private LayoutInflater mInflater;
    private List<Records> mUsers = null;
    private int layoutResource;
    private Context mContext;

    public DataListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Records> objects)throws NullPointerException {
        super(context,resource,objects);
        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutResource = resource;
        this.mUsers = objects;
    }

    private static class ViewHolder{
        TextView date, empid, name ,operation,status;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        final ViewHolder holder;

        //if(convertView == null){
        convertView = mInflater.inflate(layoutResource, parent, false);
        holder = new ViewHolder();
        holder.empid = (TextView) convertView.findViewById(R.id.empid);
        holder.name = (TextView) convertView.findViewById(R.id.name);
        holder.date = (TextView) convertView.findViewById(R.id.date);
        holder.operation = (TextView) convertView.findViewById(R.id.operation);
        holder.status = (TextView) convertView.findViewById(R.id.status);

        convertView.setTag(holder);
    /*}else{
            holder = (ViewHolder) convertView.getTag();
        }*/


        String str=getItem(position).getDate();
        String[] arrOfStr = str.split("_", 2);
        holder.date.setText("Date: "+arrOfStr[0]);
        holder.empid.setText("Emp Id: "+getItem(position).getEmpid());

        FirebaseDatabase.getInstance().getReference().child("employee").child(getItem(position).getEmpid()).child("name")
                .get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful())
                    holder.name.setText("Name: "+String.valueOf(task.getResult().getValue()));
            }
        });

        holder.operation.setText("Operation: "+getItem(position).getOperation());
        holder.status.setText("Status: "+getItem(position).getStatus());

        return convertView;
    }
}
