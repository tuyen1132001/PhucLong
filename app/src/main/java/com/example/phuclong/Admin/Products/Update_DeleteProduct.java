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
import android.widget.ImageView;
import android.widget.Toast;

import com.example.phuclong.Product;
import com.example.phuclong.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.UUID;

public class Update_DeleteProduct extends AppCompatActivity {
    EditText name,price,discount,description;
    Button update,image,cancel,delete;
    ImageView imageView;
    AutoCompleteTextView menulist;
    FirebaseStorage storage;
    StorageReference storagereference;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    Uri uri;
    Product product;
    String  productid,url;
    ArrayAdapter<String> adapteritem;
    ArrayAdapter<String> datacategory;
    String[] item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete_product);
        matching();
        storage = FirebaseStorage.getInstance();
        storagereference=storage.getReference("Products/");
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Products");
        Intent intent = getIntent();
        if(intent !=null)
            productid = intent.getStringExtra("productid");
        if(!productid.isEmpty()) {
            databaseReference.child(productid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    product = snapshot.getValue(Product.class);
                    Picasso.with(getBaseContext()).load(product.getImage()).into(imageView);
                    name.setText(product.getName());
                    price.setText(product.getPrice());
                    discount.setText(product.getDiscourt());
                    description.setText(product.getDescription());
                    url = product.getImage();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = productid;
                String sname =name.getText().toString();
                String simage = UUID.randomUUID().toString();
                String sdescription = description.getText().toString();
                String sprice = price.getText().toString();
                String sdiscount = discount.getText().toString();
                String scategoryid = menulist.getText().toString();
                int categoryid = getcategoryid(item,scategoryid)+1;
                if(uri !=null){
                    final StorageReference imagefolder = storagereference.child(simage);
                    imagefolder.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            imagefolder.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    //set value new Product
                                    product = new Product(sname,uri.toString(),sdescription,sprice,sdiscount,categoryid+"");
                                    databaseReference.child(id).setValue(product);
                                }

                            });
                        }
                    }).addOnFailureListener(e -> {
                        Toast.makeText(Update_DeleteProduct.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
                    });
                    finish();
                    Toast.makeText(Update_DeleteProduct.this,"Cập nhập thành công",Toast.LENGTH_SHORT).show();
                    StorageReference sstorage = FirebaseStorage.getInstance().getReferenceFromUrl(url);
                    sstorage.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                        }
                    });
                }else{
                    product.setName(sname);
                    product.getImage();
                    product.setPrice(sprice);
                    product.setDiscourt(sdiscount);
                    product.setCategoryid(categoryid+"");
                    product.setDescription(sdescription);
                    databaseReference.child(id).setValue(product);
                    Toast.makeText(Update_DeleteProduct.this,"Cập nhập thành công",Toast.LENGTH_SHORT).show();
                    finish();

                };
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
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Picasso.with(null);
                StorageReference sstorage = FirebaseStorage.getInstance().getReferenceFromUrl(url);
                sstorage.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    }
                });
                databaseReference.child(productid).removeValue();
                Toast.makeText(Update_DeleteProduct.this,"Xoá thành công thành công",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        categorylist();
    }
    private int getcategoryid(String[] item , String scategory){
        for (int i = 0; i < item.length; i++) {
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
                    i++;
                }
                item = new String[i];
                i=0;
                for (DataSnapshot record : snapshot.getChildren()) {
                    HashMap<String, Object> hashMap = (HashMap<String, Object>) record.getValue();
                    String value = hashMap.get("name").toString();
                    item[i] = value;
                    i++;
                }
                adapteritem = new ArrayAdapter<String>(Update_DeleteProduct.this,R.layout.list_menu_item,item);
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
        name = (EditText) findViewById(R.id.et_update_delete_product_name);
        price =(EditText) findViewById(R.id.et_update_delete_product_price);
        discount =(EditText) findViewById(R.id.et_update_delete_product_discount);
        description =(EditText) findViewById(R.id.et_update_delete_product_description);
        update = (Button) findViewById(R.id.btn_update_delete_product_save);
        image = (Button) findViewById(R.id.btn_update_delete_product_image);
        cancel =(Button) findViewById(R.id.btn_update_delete_product_cancel);
        delete =(Button)findViewById(R.id.btn_update_delete_product_delete);
        imageView =(ImageView) findViewById(R.id.im_update_delete_product);
        menulist = (AutoCompleteTextView) findViewById(R.id.auto_update_delete_product_tvmenulist);

    }
}