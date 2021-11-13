package com.example.phuclong;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
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

public class SignIn extends AppCompatActivity {
    Button signIn, cancel;
    EditText Phonenumber, Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        matching();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dataref = database.getReference("User");
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog dialog = new ProgressDialog(SignIn.this);
                dialog.setMessage("Vui lòng chờ...");
                dialog.show();

                dataref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(Phonenumber.getText().toString().trim().equals("") ||
                                Password.getText().toString().trim().equals("")) {
                            dialog.dismiss();
                            Toast.makeText(SignIn.this, "Vui lòng điền đầy đủ ", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            if (snapshot.child(Phonenumber.getText().toString()).exists()) {
                                // Get user inf
                                dialog.dismiss();
                                User user = snapshot.child(Phonenumber.getText().toString()).getValue(User.class);
                                if (user.getPassword().equals(Password.getText().toString())) {
                                    Common.currentUser = user;
                                    startActivity(new Intent(SignIn.this, Home.class));
                                } else
                                    Toast.makeText(SignIn.this, "Sai mật khẩu", Toast.LENGTH_SHORT).show();
                            } else {
                                dialog.dismiss();
                                Toast.makeText(SignIn.this, "Tài khoản không tồn tại", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void matching() {
        Phonenumber = (EditText) findViewById(R.id.et_Phonenumber);
        Password = (EditText) findViewById(R.id.et_Password);
        signIn = (Button) findViewById(R.id.btn_signin);
        cancel = (Button) findViewById(R.id.btn_cancel);

    }
}