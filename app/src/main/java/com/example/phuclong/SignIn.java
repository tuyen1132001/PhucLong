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
    Button signIn;
    EditText Phonenumber,Password;

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
                dialog.setMessage("Vui long cho..");
                dialog.show();

                dataref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.child(Phonenumber.getText().toString()).exists()) {
                            // Get user inf
                            dialog.dismiss();
                            User user = snapshot.child(Phonenumber.getText().toString()).getValue(User.class);
                            if (user.getPassword().equals( Password.getText().toString())){
                                Common.currentUser = user;
                                startActivity(new Intent(SignIn.this,Home.class));
                            }

                            else
                                Toast.makeText(SignIn.this, "Sai Mat Khau", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            dialog.dismiss();
                            Toast.makeText(SignIn.this, "Tai khoan khong ton tai", Toast.LENGTH_SHORT).show();
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
        signIn = (Button) findViewById(R.id.btn_signin);

    }
}