package com.example.phuclong;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SignUp extends AppCompatActivity {

    EditText Email, Password, Name, Phone;
    Button signup, cancel;
    FirebaseAuth auth;
    FirebaseFirestore store;
    boolean valid = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        auth = FirebaseAuth.getInstance();
        store = FirebaseFirestore.getInstance();

        matching();


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkField(Email);
                checkField(Name);
                checkField(Phone);
                checkField(Password);

                if (valid) {
                    auth.createUserWithEmailAndPassword(Email.getText().toString(), Password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            FirebaseUser user = auth.getCurrentUser();
                            Toast.makeText(SignUp.this, "Tài khoản tạo thành công", Toast.LENGTH_SHORT).show();

                            DocumentReference df = store.collection("Users").document(user.getUid());
                            Map<String, Object> userInfo = new HashMap<>();
                            userInfo.put("ID", user.getUid());
                            userInfo.put("Email", Email.getText().toString().trim());
                            userInfo.put("Name", Name.getText().toString().trim());
                            userInfo.put("Phone", Phone.getText().toString().trim());
                            userInfo.put("Password", Password.getText().toString().trim());

                            userInfo.put("Role", "user");

                            df.set(userInfo);

                            DatabaseReference dbInfor = FirebaseDatabase.getInstance().getReference("InforUser");
                            dbInfor.child(user.getUid()).child("ID").setValue(user.getUid());
                            dbInfor.child(user.getUid()).child("Email").setValue(Email.getText().toString().trim());
                            dbInfor.child(user.getUid()).child("Name").setValue(Name.getText().toString().trim());
                            dbInfor.child(user.getUid()).child("Phone").setValue(Phone.getText().toString().trim());
                            dbInfor.child(user.getUid()).child("Role").setValue("User");

                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SignUp.this, "Tạo tài khoản thất bại", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public boolean checkField(EditText textField) {
        if (textField.getText().toString().isEmpty()) {
            textField.setError("Vui lòng điền thông tin");
            valid = false;
        } else {
            valid = true;
        }
        return valid;
    }


    private void matching() {
        Email = (EditText) findViewById(R.id.et_signup_email);
        Password = (EditText) findViewById(R.id.et_signup_Password);
        Name = (EditText) findViewById(R.id.et_signup_Name);
        Phone = (EditText) findViewById(R.id.et_signup_Phonenumber);
        signup = (Button) findViewById(R.id.btn_signup);
        cancel = (Button) findViewById(R.id.btn_signup_cancel);
    }
}