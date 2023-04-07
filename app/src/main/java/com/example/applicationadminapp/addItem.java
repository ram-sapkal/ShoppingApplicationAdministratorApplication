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

import com.example.applicationadminapp.databinding.ActivityAddItemBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class addItem extends AppCompatActivity {

    private ActivityAddItemBinding binding;

    private FirebaseDatabase database;
    private FirebaseStorage storage;
    private DatabaseReference databaseReference,databaseReference2,databaseReference3;
    int requestcode=1;
    String valuee="not yet generated";
    ArrayList<Uri>uris=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddItemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Barcodes/");
        databaseReference2 = FirebaseDatabase.getInstance().getReference("Barcodes/lastCode/codeDate");
        databaseReference3 = FirebaseDatabase.getInstance().getReference("Barcodes/lastCode/");
        //databaseReference3.setValue(new code("181220220"));



        binding.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*                String code=binding.barCode.getText().toString().trim();
                if(code.isEmpty()){
                    binding.barCode.setError("Generate barcode");
                    binding.barCode.requestFocus();
                }else{*/
                    getBarcodeForItem();
                openFileChosser();
                //}
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
/*                    String barCode=valuee.substring(0,8)+((Integer.parseInt(valuee.substring(8)))+(i+1));
                    databaseReference3.setValue(new code(barCode));*/
                }
            }else{
                uris.add(data.getData());
                //Uri uri = data.getData();
/*                String barCode=valuee.substring(0,8)+((Integer.parseInt(valuee.substring(8)))+1);
                databaseReference3.setValue(new code(barCode));*/
            }
        }
    }

    public void openFileChosser(){

        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
        startActivityForResult(intent,requestcode);
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
                        Toast.makeText(addItem.this, "Fail to fetch", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(addItem.this, "Failure to get last code", Toast.LENGTH_SHORT).show();
                }
            });

        }catch (Exception e){
            Toast.makeText(this,"Function "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}