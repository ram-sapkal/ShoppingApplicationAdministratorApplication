package com.example.applicationadminapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.applicationadminapp.databinding.ActivityBannerAddBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.UUID;

public class BannerAdd extends AppCompatActivity {

    private ActivityBannerAddBinding binding;
    private FirebaseDatabase database;
    private FirebaseStorage storage;
    private DatabaseReference databaseReference;
    int requestcode=1;
    String valuee="not yet generated";
    ArrayList<Uri> uris=new ArrayList<>();
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityBannerAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Discount/Banner/");

        binding.banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChosser();
            }
        });

        binding.bannerA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(uris.size()<=0){
                    binding.bannerA.setError("plz select image first");
                    binding.bannerA.requestFocus();
                }else{
                    String uniqueID;
                    binding.banner.setImageURI(uris.get(0));
                    for(int i=0;i<uris.size();i++){
                        uniqueID = UUID.randomUUID().toString();
                        Toast.makeText(BannerAdd.this, uniqueID, Toast.LENGTH_SHORT).show();
                        final StorageReference reference=storage.getReference("banner/")
                                .child(uniqueID);
                        reference.putFile(uris.get(i)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri urii) {

                                        database.getReference("Discount/Banner/")
                                                .push()
                                                .setValue(urii.toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(getApplicationContext(), "Added One Item", Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                });
                            }
                        });

                        //--------------------------------------------------
                    }
                    uris.clear();
                }

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == requestcode && resultCode== Activity.RESULT_OK){
            if(data==null)
                return;

            if(null != data.getClipData()){
                for(int i=0;i<data.getClipData().getItemCount();i++){
                    uris.add(data.getClipData().getItemAt(i).getUri());
                }
            }else{
                uris.add(data.getData());
            }
        }
    }

    public void openFileChosser(){

        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
        startActivityForResult(intent,requestcode);
    }
}