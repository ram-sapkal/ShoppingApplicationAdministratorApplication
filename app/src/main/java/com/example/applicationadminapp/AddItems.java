package com.example.applicationadminapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.applicationadminapp.databinding.ActivityAddItemsBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddItems extends AppCompatActivity {

    private ActivityAddItemsBinding binding;

    //
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddItemsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        /*databaseReference = FirebaseDatabase.getInstance().getReference("Store/shirts/");
        *//*newShirt a = new newShirt("123", "RAMsapkal"); // add panel
                        databaseReference.setValue(a);*/

        binding.addshirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddItems.this,AddShirt.class));
            }
        });

        binding.bannerAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddItems.this,BannerAdd.class));
            }
        });
        binding.tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddItems.this,MainActivity2.class));
            }
        });
    }
}