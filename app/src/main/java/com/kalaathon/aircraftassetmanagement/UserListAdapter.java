package com.kalaathon.aircraftassetmanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.FirebaseDatabase;
import com.kalaathon.aircraftassetmanagement.models.User;

import java.util.List;


public class UserListAdapter extends ArrayAdapter<User> {
    private static final String TAG = "UserListAdapter";

    private LayoutInflater mInflater;
    private List<User> mUsers = null;
    private int layoutResource;
    private Context mContext;
    private String mString;

    public UserListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<User> objects)throws NullPointerException {
        super(context, resource, objects);
        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutResource = resource;
        this.mUsers = objects;
    }

    private static class ViewHolder{
        TextView empid, name;
        Button verify;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        final ViewHolder holder;

        //if(convertView == null){
        convertView = mInflater.inflate(layoutResource, parent, false);
        holder = new ViewHolder();
        holder.empid = (TextView) convertView.findViewById(R.id.list_empid);
        holder.name = (TextView) convertView.findViewById(R.id.list_name);
        holder.verify=(Button) convertView.findViewById(R.id.list_verify);

        convertView.setTag(holder);
    /*}else{
            holder = (ViewHolder) convertView.getTag();
        }*/


        holder.empid.setText(getItem(position).getEmpid());
        holder.name.setText(getItem(position).getName());
        holder.verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseDatabase.getInstance().getReference().child("employee").child(holder.empid.getText().toString()).child("verify").setValue("true");
                mUsers.remove(position);
                notifyDataSetChanged();
                Toast.makeText(mContext, holder.name.getText().toString()+" has been verified.", Toast.LENGTH_SHORT).show();

            }
        });

        return convertView;
    }
}
