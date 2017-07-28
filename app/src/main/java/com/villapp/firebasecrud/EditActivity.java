package com.villapp.firebasecrud;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.villapp.firebasecrud.model.User;

public class EditActivity extends AppCompatActivity {

    EditText et_name,et_email;
    Button btn_update;
    String uid;
    private DatabaseReference mFireDbUser;
    private FirebaseDatabase mFirebaseInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Edit Form");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        et_name = (EditText)findViewById(R.id.edit_name);
        et_email = (EditText)findViewById(R.id.edit_email);
        btn_update = (Button)findViewById(R.id.btn_update);

        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFireDbUser = mFirebaseInstance.getReference("users");

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        if (bundle != null){
            User user = bundle.getParcelable("_user_");
            et_name.setText(user.getName());
            et_email.setText(user.getEmail());
            uid = user.getUid();
        }

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et_name.getText().toString();
                String email = et_email.getText().toString();
                if(!TextUtils.isEmpty(uid)){
                    if (!TextUtils.isEmpty(name))mFireDbUser.child(uid).child("name").setValue(name);
                    if (!TextUtils.isEmpty(email))mFireDbUser.child(uid).child("email").setValue(email, new DatabaseReference.CompletionListener(){

                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            Toast.makeText(getBaseContext(), "Data has been updated", Toast.LENGTH_SHORT).show();
                            Bundle bundle = new Bundle();
                            bundle.putString("uid_",uid);
                            List listFrag = new List();
                            listFrag.setArguments(bundle);
                            finish();
                        }
                    });

                }
            }
        });
    }
}
