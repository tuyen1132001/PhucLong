package com.example.phuclong;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUp extends AppCompatActivity {

    EditText Phonenumber,Password,Name;
    Button signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        matching();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dataref = database.getReference("User");
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog dialog = new ProgressDialog(SignUp.this);
                dialog.setMessage("Vui long cho..");
                dialog.show();
                dataref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.child(Phonenumber.getText().toString()).exists()){
                            dialog.dismiss();
                            Toast.makeText(SignUp.this, "Số điện thoại đã tồn tại!", Toast.LENGTH_SHORT).show();
                        }else{
                            dialog.dismiss();
                            User user = new User(Password.getText().toString(),Name.getText().toString());
                            dataref.child(Phonenumber.getText().toString()).setValue(user);
                            Toast.makeText(SignUp.this, "Tạo tài khoản thành công", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
    }
    private void matching() {
        Phonenumber = (EditText) findViewById(R.id.et_Phonenumber);
        Password = (EditText) findViewById(R.id.et_Password);
        Name = (EditText) findViewById(R.id.et_Name);
        signup = (Button) findViewById(R.id.btn_signup);

    }
}