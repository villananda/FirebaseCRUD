package com.villapp.firebasecrud;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.villapp.firebasecrud.adapter.ListAdapter;
import com.villapp.firebasecrud.model.User;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

/**
 * Created by ASUS-PC on 25/07/2017.
 */

public class List extends Fragment {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private DatabaseReference mFireDbUser;
    private FirebaseDatabase mFirebaseInstance;
    ArrayList<User> arrayUser;
    ListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        final ListView listView = (ListView)view.findViewById(R.id.list_item);
        mFirebaseInstance = FirebaseDatabase.getInstance();
//        set offline cache
//        mFirebaseInstance.setPersistenceEnabled(true);
        mFireDbUser = mFirebaseInstance.getReference("users");
        mFireDbUser.keepSynced(true);
        mFireDbUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayUser = collectUsers((Map<String, User>)dataSnapshot.getValue());

                adapter = new ListAdapter(getContext(), arrayUser);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(LOG_TAG, databaseError.getMessage());
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User u = adapter.getItem(position);
                Bundle bundle = new Bundle();
                bundle.putParcelable("_user_",u);

                Intent intent = new Intent(getContext(), EditActivity.class);
                intent.putExtra("bundle", bundle);
                getActivity().startActivity(intent);
            }
        });
        return view;
    }

    private ArrayList<User> collectUsers(Map<String, User> user){
        final ArrayList<User> arrayUser = new ArrayList<>();

        for (Map.Entry<String, User> entry: user.entrySet()){
            Map singleUser = (Map)entry.getValue();
            User u = new User();
            u.setName((String) singleUser.get("name"));
            u.setEmail((String) singleUser.get("email"));
            u.setUid((String) singleUser.get("uid"));
            arrayUser.add(u);
        }
        return arrayUser;
    }
}