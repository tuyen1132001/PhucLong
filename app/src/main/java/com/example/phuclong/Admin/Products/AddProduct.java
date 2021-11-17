package com.example.phuclong.Admin.Products;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.phuclong.Product;
import com.example.phuclong.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.UUID;

public class AddProduct extends AppCompatActivity {
    EditText name,price,discourt,description;
    Button save,image,cancel;
    AutoCompleteTextView menulist;
    FirebaseStorage storage;
    StorageReference reference;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    Uri uri;
    Product product;
    int size;
    ArrayAdapter<String> adapteritem;
    ArrayAdapter<String> data;
    String[] item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        matching();
        data= new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        Bundle extra = getIntent().getExtras();
        if(extra != null){
            this.size = extra.getInt("size");
        }

        storage = FirebaseStorage.getInstance();
        reference=storage.getReference("Products/");
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Products");

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(uri !=null){
                    String sname =name.getText().toString();
                    String simage = UUID.randomUUID().toString();
                    String sdescription = description.getText().toString();
                    String sprice = price.getText().toString();
                    String sdiscourt = discourt.getText().toString();
                    String scategoryid = menulist.getText().toString();
                    final StorageReference imagefolder = reference.child(simage);
                    imagefolder.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            imagefolder.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    //set value new Product
                                   int categoryid = getcategoryid(item,scategoryid)+1;
                                    product = new Product(sname,uri.toString(),sdescription,sprice,sdiscourt,categoryid+"");
                                    databaseReference.child(size+"").setValue(product);
                                }

                            });
                        }
                    }).addOnFailureListener(e -> {
                        Toast.makeText(AddProduct.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
                    });
                    Toast.makeText(AddProduct.this,"Thêm thành công",Toast.LENGTH_SHORT).show();
                }

                finish();
            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"SElECT IMAGE"),100);
            }
        });
        categorylist();

    }
    private int getcategoryid(String[] item , String scategory){
        for (int i = 0; i < data.getCount(); i++) {
            if(item[i].equals(scategory))
                return i;
        }
        return -1;
    }

    private void categorylist() {

        FirebaseDatabase cate = FirebaseDatabase.getInstance();
        DatabaseReference ref = cate.getReference("Category");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int i =0;
                    for (DataSnapshot record : snapshot.getChildren()) {
                        String key = record.getKey();
                        data.add(key);
                    }
                    item = new String[data.getCount()];
                    for (DataSnapshot record : snapshot.getChildren()) {
                        HashMap<String, Object> hashMap = (HashMap<String, Object>) record.getValue();
                        String value = hashMap.get("name").toString();
                        item[i] = value;
                        i++;
                    }
                    adapteritem = new ArrayAdapter<String>(AddProduct.this,R.layout.list_menu_item,item);
                    menulist.setAdapter(adapteritem);
                }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Error","LOI"+error.toString());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && resultCode == RESULT_OK && data !=null && data.getData()!=null){
            uri = data.getData();

        }
    }

    private void matching() {
        name = (EditText) findViewById(R.id.et_addproduct_name);
        price =(EditText) findViewById(R.id.et_addproduct_price);
        discourt =(EditText) findViewById(R.id.et_addproduct_discourt);
        description =(EditText) findViewById(R.id.et_addproduct_description);
        save = (Button) findViewById(R.id.btn_addproduct_save);
        image = (Button) findViewById(R.id.btn_addproduct_image);
        cancel =(Button) findViewById(R.id.btn_addproduct_cancel);
        menulist = (AutoCompleteTextView) findViewById(R.id.auto_tvmenulist);

    }
}