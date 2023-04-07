package com.example.applicationadminapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.view.View;
import android.widget.Toast;

import com.example.applicationadminapp.databinding.ActivityMainBinding;
import com.google.common.base.MoreObjects;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private String userId,userPass;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseReference = FirebaseDatabase.getInstance().getReference("AdminPanel/");
                                /*A a = new A("RamSapkal", "RAMsapkal"); // add panel
                        databaseReference.setValue(a);*/
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                A post = dataSnapshot.getValue(A.class);
                userId=post.getUserid();
                userPass=post.getUserpass();
                // ..
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
            }
        };
        databaseReference.addValueEventListener(postListener);

        binding.loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid=binding.username.getText().toString().trim();
                String pass=binding.password.getText().toString().trim();
                if(uid.isEmpty()){
                    Toast.makeText(MainActivity.this, "User id is empty", Toast.LENGTH_SHORT).show();
                }else if(pass.isEmpty()){
                    Toast.makeText(MainActivity.this, "Password is empty", Toast.LENGTH_SHORT).show();
                }else{
                    try {
                        startActivity(new Intent(MainActivity.this,AddItems.class));
/*                        if(!uid.equals(userId)){
                            Toast.makeText(MainActivity.this, "Wrong User Id", Toast.LENGTH_SHORT).show();
                        }else if(!pass.equals(userPass)){
                            Toast.makeText(MainActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MainActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this,AddItems.class));
                        }*/
                    }catch (Exception e){
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }
}