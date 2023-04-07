package com.example.applicationadminapp;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.applicationadminapp.databinding.ActivityAddShirtBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddShirt extends AppCompatActivity {

    private ActivityAddShirtBinding binding;
    private FirebaseDatabase database;
    private FirebaseStorage storage;
    private DatabaseReference databaseReference,databaseReference2,databaseReference3;
    int requestcode=1;
    String valuee="not yet generated";
    ArrayList<Uri>uris=new ArrayList<>();
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddShirtBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Barcodes/AllBarCodes/");
        databaseReference2 = FirebaseDatabase.getInstance().getReference("Barcodes/lastCode/codeDate");
        databaseReference3 = FirebaseDatabase.getInstance().getReference("Barcodes/lastCode/");
        addItemsOnSpinner();

/*        launcher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri uri) {
                try {
                    binding.imageView.setImageURI(uri);
                }catch (Exception e){
                    Toast.makeText(AddShirt.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                final StorageReference reference=storage.getReference("image/")
                        .child(binding.barNo.getText().toString().trim());
                reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                newShirt sh=new newShirt(binding.barNo.getText().toString().trim(),
                                        binding.size.getText().toString().trim(),
                                        binding.style.getText().toString().trim(),
                                        binding.quantity.getText().toString().trim(),
                                        uri.toString(),
                                        binding.price.getText().toString().trim(),
                                        binding.color.getText().toString().trim(),"NA");
                                database.getReference("Store/"+binding.spinner.getSelectedItem().toString().trim()+"/")
                                        .child(binding.barNo.getText().toString().trim())
                                        .setValue(sh).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(AddShirt.this, "Success", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });*/

        binding.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBarcodeForItem();
                openFileChosser();
            }
        });

        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String barCode=valuee.substring(0,8)+((Integer.parseInt(valuee.substring(8)))+1);
                databaseReference3.setValue(new code(barCode));
                databaseReference.push().setValue(new BarCode(barCode));
                final StorageReference reference=storage.getReference("image/")
                        .child(barCode);
                reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri urii) {

                                newShirt ns=new newShirt(barCode,
                                        binding.size.getText().toString(),
                                        binding.style.getText().toString(),
                                        binding.quantity.getText().toString(),
                                        urii.toString(),
                                        binding.price.getText().toString(),
                                        binding.company.getText().toString().trim(),
                                        binding.spinner.getSelectedItem().toString().trim());
                                //database.getReference("Store/"+binding.spinner.getSelectedItem().toString().trim()+"/")
                                database.getReference("Store/")
                                        .child(barCode)
                                        .setValue(ns).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(AddShirt.this, "Added One Item", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(AddShirt.this, "Failure", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                    }
                });

            }
        });

        binding.submit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(int i=0;i<uris.size();i++){
                    String barCode=valuee.substring(0,8)+((Integer.parseInt(valuee.substring(8)))+(i+1));
                    databaseReference3.setValue(new code(barCode));
                    databaseReference.push().setValue(new BarCode(barCode));

                    final StorageReference reference=storage.getReference("image/")
                            .child(barCode);
                    reference.putFile(uris.get(i)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri urii) {

                                    newShirt ns=new newShirt(barCode,
                                            binding.size.getText().toString(),
                                            binding.style.getText().toString(),
                                            binding.quantity.getText().toString(),
                                            urii.toString(),
                                            binding.price.getText().toString(),
                                            binding.company.getText().toString().trim(),
                                            binding.spinner.getSelectedItem().toString().trim());
                                    //database.getReference("Store/"+binding.spinner.getSelectedItem().toString().trim()+"/")
                                    database.getReference("Store/")
                                            .child(barCode)
                                            .setValue(ns).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(AddShirt.this, "Added One Item", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(AddShirt.this, "Failure", Toast.LENGTH_SHORT).show();
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
                uri=data.getData();
                binding.imageView.setImageURI(uri);
            }
        }
    }

    public void openFileChosser(){

        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
        startActivityForResult(intent,requestcode);
    }

    // add items into spinner dynamically
    public void addItemsOnSpinner() {

        List<String> list = new ArrayList<String>();
        list.add("Select Item");
        list.add("shirt");
        list.add("t-shirt");
        list.add("jacket");
        list.add("jeans");
        list.add("trausor");
        list.add("shorts");
        list.add("cap");
        list.add("wallet");
        list.add("wallet");
        list.add("belt");
        list.add("sunglass");
        list.add("sweater");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinner.setAdapter(dataAdapter);
    }

    public void getBarcodeForItem(){
        try {
            databaseReference2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    try {
                        valuee = snapshot.getValue(String.class);
                        String d=valuee.substring(0,2);
                        String m=valuee.substring(2,4);
                        String y=valuee.substring(4,8);
                        String date1=d+"-"+m+"-"+y;

                        SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy");
                        String date2 = s.format(new Date());

                        if(date1.compareTo(date2)<0){
                            date2=date2.replace("-","");
                            valuee=date2+"0";
                        }
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(), "Fail to fetch", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getApplicationContext(), "Failure to get last code", Toast.LENGTH_SHORT).show();
                }
            });

        }catch (Exception e){
            Toast.makeText(this,"Function "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

/*    public void addBulkItem(newShirt ns,String cate){

        final StorageReference reference=storage.getReference("image/")
                .child(ns.getBcode());
        reference.putFile(Uri.parse(ns.getUrl())).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri urii) {
                        ns.setUrl(urii.toString());
                        database.getReference("Store/"+cate.trim()+"/")
                                .child(ns.getBcode())
                                .setValue(ns).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(AddShirt.this, "Added One Item", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AddShirt.this, "Failure", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });

    }*/
}