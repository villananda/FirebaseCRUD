package com.villapp.firebasecrud.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.villapp.firebasecrud.EditActivity;
import com.villapp.firebasecrud.R;
import com.villapp.firebasecrud.model.User;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by ASUS-PC on 27/07/2017.
 */

public class ListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<User> users;

    public ListAdapter(Context context, ArrayList<User> users) {
        this.context = context;
        this.users = users;
    }

    public void setUsers(ArrayList<User> users) { this.users = users; }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public User getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        viewHolder holder;

        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.row_item, parent, false);
            holder = new viewHolder(convertView);
            convertView.setTag(holder);;
        }else{
            holder = (viewHolder)convertView.getTag();
        }
        User us = getItem(position);

        holder.us_name.setText(us.getName());
        holder.us_email.setText(us.getEmail());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User u = getItem(position);
                Bundle bundle = new Bundle();
                bundle.putParcelable("_user_",u);

                Intent intent = new Intent(context, EditActivity.class);
                intent.putExtra("bundle", bundle);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    class viewHolder{
        TextView us_name,us_email;
        public viewHolder(View view){
            us_name = (TextView)view.findViewById(R.id.item_name);
            us_email = (TextView)view.findViewById(R.id.item_email);
        }
    }
}
