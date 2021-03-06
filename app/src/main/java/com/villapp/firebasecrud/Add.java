package com.villapp.firebasecrud;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.villapp.firebasecrud.model.User;

import org.w3c.dom.Text;

/**
 * Created by ASUS-PC on 27/07/2017.
 */

public class Add extends Fragment {

    private static final String TAG = MainActivity.class.getSimpleName();
    private EditText inputName, inputEmail;
    private Button btnSave;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private String userId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add, container,false);
        inputName = (EditText) view.findViewById(R.id.name);
        inputEmail = (EditText) view.findViewById(R.id.email);
        btnSave = (Button) view.findViewById(R.id.btn_save);

        mFirebaseInstance = FirebaseDatabase.getInstance();

        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference("users");

        // store app title to 'app_title' node
        mFirebaseInstance.getReference("app_title").setValue("Realtime Database");

        // app_title change listener
        mFirebaseInstance.getReference("app_title").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e(TAG, "App title updated");

                String appTitle = dataSnapshot.getValue(String.class);

                // update toolbar title
                ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(appTitle);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read app title value.", error.toException());
            }
        });

        // Save / update the user
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = inputName.getText().toString();
                String email = inputEmail.getText().toString();

                // Check for already existed userId
                if (TextUtils.isEmpty(userId)) {
                    if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email)){
                        Toast.makeText(getContext(), "Nama atau email harus diisi", Toast.LENGTH_SHORT).show();
                    }else{
                        createUser(name, email);
                    }
                }
            }
        });
        return view;
    }
    /**
     * Creating new user node under 'users'
     */
    private void createUser(String name, String email) {
        // TODO
        // In real apps this userId should be fetched
        // by implementing firebase auth
        if (TextUtils.isEmpty(userId)) {
            userId = mFirebaseDatabase.push().getKey();
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setUid(userId);

        mFirebaseDatabase.child(userId).setValue(user, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                Toast.makeText(getContext(), "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                MainActivity.viewPager.setCurrentItem(0);

                String tagname = "android:switcher:"+R.id.pagerView+":"+0;
                List listfragment = (List)getActivity().getSupportFragmentManager().findFragmentByTag(tagname);

                listfragment.updateList();
            }
        });
    }
}
