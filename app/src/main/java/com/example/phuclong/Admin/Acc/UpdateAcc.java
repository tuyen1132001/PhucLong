package com.example.phuclong.Admin.Acc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.phuclong.ProfileUserActivity;
import com.example.phuclong.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class UpdateAcc extends AppCompatActivity {

    Button btnCancel, btnUpdate;
    String uID, uEmail, uName, uPassword, uPhone, uRole;
    EditText etEmail, etName, etPassword, etPhone, etRole;
    FirebaseFirestore db;
    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_acc);

        maching();

        Bundle bundle = getIntent().getExtras();
        uID = bundle.getString("uID");
        uEmail = bundle.getString("uEmail");
        uName = bundle.getString("uName");
        uPassword = bundle.getString("uPassword");
        uPhone = bundle.getString("uPhone");
        uRole = bundle.getString("uRole");
        etEmail.setText(uEmail);
        etName.setText(uName);
        etPassword.setText(uPassword);
        etPhone.setText(uPhone);
        etRole.setText(uRole);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Email = etEmail.getText().toString();
                String Name = etName.getText().toString();
                String Password = etPassword.getText().toString();
                String Phone = etPhone.getText().toString();
                String Id = uID;
                String Role = etRole.getText().toString();

                Bundle bundle1 = getIntent().getExtras();

                updateToFireStore(Id ,Email, Name, Password, Phone, Role);
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void updateToFireStore(String id, String email, String name, String password, String phone, String role) {
        db.collection("Users").document(id).update("Email", email,
                "Name", name,
                "Password", password,
                "Phone", phone,
                "Role", role)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(UpdateAcc.this, "Thay đổi dữ liệu thành công", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(UpdateAcc.this, ShowActivity.class));
                            finish();
                        }
                        else{
                            Toast.makeText(UpdateAcc.this, "Error: ..." + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UpdateAcc.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void maching() {
        btnCancel = (Button) findViewById(R.id.btn_cancel_acc);
        btnUpdate = (Button) findViewById(R.id.btn_update_acc);
        etEmail = (EditText) findViewById(R.id.et_acc_email);
        etName =(EditText) findViewById(R.id.et_acc_name);
        etPassword =(EditText) findViewById(R.id.et_acc_password);
        etPhone =(EditText) findViewById(R.id.et_acc_phone);
        etRole = (EditText) findViewById(R.id.et_acc_role);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
    }
}