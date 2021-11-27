package com.example.phuclong.Admin.Category;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.phuclong.Category;
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

import java.util.UUID;

public class Update_DeleteCategory extends AppCompatActivity {

    EditText name;
    Button imange,update,cancel,delete;
    ImageView imageView;
    FirebaseStorage storage;
    StorageReference storagereference;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    Category category;
    Uri uri;
    String Categoryid;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete_category);
        matching();
        storage = FirebaseStorage.getInstance();
        storagereference=storage.getReference("Category/");
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Category");
        Intent intent = getIntent();
        if(intent !=null)
            Categoryid = intent.getStringExtra("categoryid");
        if(!Categoryid.isEmpty()) {
            databaseReference.child(Categoryid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    category = snapshot.getValue(Category.class) ;
                    Picasso.with(getBaseContext()).load(category.getImage()).into(imageView);
                    name.setText(category.getName());
                    url = category.getImage();

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
        imange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"SElECT IMAGE"),100);
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = Categoryid;
                String sname =name.getText().toString();
                if(uri !=null){
                    String simage = UUID.randomUUID().toString();
                    final StorageReference imagefolder = storagereference.child(simage);
                    imagefolder.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            imagefolder.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    //set value update category
                                    category.setImage(uri.toString());
                                    category.setName(sname);
                                    databaseReference.child(id).setValue(category);
                                }
                            });
                        }
                    }).addOnFailureListener(e -> {
                        Toast.makeText(Update_DeleteCategory.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
                    });
                    Toast.makeText(Update_DeleteCategory.this,"Cập nhập thành công",Toast.LENGTH_SHORT).show();
                    finish();
                    StorageReference sstorage = FirebaseStorage.getInstance().getReferenceFromUrl(url);
                    sstorage.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                        }
                    });
                }else{
                    category.setName(sname);
                    category.getImage();
                    databaseReference.child(id).setValue(category);
                    Toast.makeText(Update_DeleteCategory.this,"Cập nhập thành công",Toast.LENGTH_SHORT).show();
                    finish();

                }
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
                databaseReference.child(Categoryid).removeValue();
                Toast.makeText(Update_DeleteCategory.this,"Xoá thành công",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
    private void matching() {
        name = (EditText) findViewById(R.id.et_update_delete_category_name);
        imange= (Button) findViewById(R.id.btn_update_delete_categoryuploadimage);
        update =(Button) findViewById(R.id.btn_update_delete_categoryupdate);
        cancel = (Button) findViewById(R.id.btn_update_delete_category_cancel);
        delete = (Button) findViewById(R.id.btn_update_delete_categorydelete) ;
        imageView =(ImageView) findViewById(R.id.im_update_delete_category);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && resultCode == RESULT_OK && data !=null && data.getData()!=null){
            uri = data.getData();
        }
    }
}

