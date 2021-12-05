package com.example.phuclong.Admin.Category;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.phuclong.Category;
import com.example.phuclong.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class AddCategory extends AppCompatActivity {
    EditText name;
    Button imange,save,cancel;
    FirebaseStorage storage;
    StorageReference reference;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    Uri uri;
    Category category;
    int size;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        matching();
        Bundle extra = getIntent().getExtras();
        if(extra != null){
            this.size = extra.getInt("size");
        }
        storage = FirebaseStorage.getInstance();
        reference=storage.getReference("Category/");
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Category");
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddCategory.this, ManageCategory.class));
                finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(uri !=null){
                    String sname =name.getText().toString();
                    String simage = UUID.randomUUID().toString();
                   final StorageReference imagefolder = reference.child(simage);
                    imagefolder.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            imagefolder.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    //set value new category
                                    String id = (size+1)+"";
                                    category = new Category(sname,uri.toString());
                                    databaseReference.child(id).setValue(category);
                                }
                            });
                        }
                    }).addOnFailureListener(e -> {
                        Toast.makeText(AddCategory.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
                    });
                    Toast.makeText(AddCategory.this,"Thêm thành công",Toast.LENGTH_SHORT).show();
                }

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



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && resultCode == RESULT_OK && data !=null && data.getData()!=null){
            uri = data.getData();

        }
    }

    private void matching() {
        name = (EditText) findViewById(R.id.et_addcategoryname);
        imange= (Button) findViewById(R.id.btn_addcategoryuploadimage);
        save =(Button) findViewById(R.id.btn_addcategorysave);
        cancel = (Button) findViewById(R.id.btn_addcategory_cancel);

    }
}