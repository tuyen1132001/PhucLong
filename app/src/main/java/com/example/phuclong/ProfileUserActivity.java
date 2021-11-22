package com.example.phuclong;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

public class ProfileUserActivity extends AppCompatActivity {
    EditText hoten, sodienthoai, email, ngay, thang, namsinh, diachi;
    RadioButton nam, nu;
    Button sua, luu, huy;
    String ID, viewOf, gioitinh, ngaysinh, avarCu, test = "";
    ImageView back, changepro, ava;
    Uri uri;
    private static final int MY_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_user);
        matching();
        Bundle extras = getIntent().getExtras();
        ID = extras.getString("Userid");
        viewOf = extras.getString("View");

        load();
        edit();
        changeAva();
    }

    private void changeAva() {
        changepro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, MY_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            uri = data.getData();
            test = uri.toString();
            Picasso.with(getApplication()).load(uri).into(ava);
        } else {
            return;
        }
    }

    private void edit() {
        sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hoten.isEnabled() == false) {
                    hoten.setEnabled(true);
                    sodienthoai.setEnabled(true);
                    email.setEnabled(true);
                    ngay.setEnabled(true);
                    thang.setEnabled(true);
                    namsinh.setEnabled(true);
                    nam.setEnabled(true);
                    nu.setEnabled(true);
                    diachi.setEnabled(true);
                    sua.setVisibility(View.INVISIBLE);
                    luu.setVisibility(View.VISIBLE);
                    huy.setVisibility(View.VISIBLE);
                    changepro.setVisibility(View.VISIBLE);
                }
            }
        });
        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mydialog = new AlertDialog.Builder(ProfileUserActivity.this);
                mydialog.setTitle("Xác nhận");
                mydialog.setMessage("Hủy bỏ thay đổi?");
                mydialog.setPositiveButton("[HỦY BỎ]", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        test = "";
                        if (avarCu.equals("ic_baseline_account_circle_24")) {
                            ava.setImageResource(getResources().getIdentifier("ic_baseline_account_circle_24", "drawable", getPackageName()));
                        } else {
                            Picasso.with(ProfileUserActivity.this).load(avarCu).into(ava);
                        }
                        load();
                    }
                });
                mydialog.setNegativeButton("[QUAY LẠI]", new DialogInterface.OnClickListener() {
                    @SuppressLint("WrongConstant")
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog alertDialog = mydialog.create();
                alertDialog.show();
            }
        });
        luu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mydialog = new AlertDialog.Builder(ProfileUserActivity.this);
                mydialog.setTitle("Xác nhận");
                mydialog.setMessage("Lưu thay đổi?");
                mydialog.setPositiveButton("[LƯU]", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        StorageReference khoAnh = FirebaseStorage.getInstance().getReference("Image-Profile");
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("InforUser");
                        reference.child(ID).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (test.equals("")) {
                                    if (hoten.getText().toString().trim().equals("")) {
                                        Toast.makeText(ProfileUserActivity.this, "Tên không được bỏ trống", Toast.LENGTH_LONG).show();
                                    } else {
                                        reference.child(ID).child("Name").setValue(hoten.getText().toString().trim());
                                        if (!sodienthoai.getText().toString().trim().equals(""))
                                            reference.child(ID).child("Phone").setValue(sodienthoai.getText().toString().trim());
                                        else
                                            Toast.makeText(ProfileUserActivity.this, "Số điện thoại không được bỏ trống", Toast.LENGTH_LONG).show();
                                        if (!email.getText().toString().trim().equals(""))
                                            reference.child(ID).child("Email").setValue(email.getText().toString().trim());
                                        else
                                            Toast.makeText(ProfileUserActivity.this, "Email không được bỏ trống", Toast.LENGTH_LONG).show();

                                        if (!ngay.getText().toString().trim().equals("")
                                                && !thang.getText().toString().trim().equals("")
                                                && !namsinh.getText().toString().trim().equals("")) {
                                            String ngaysinh = "";
                                            if (Integer.valueOf(ngay.getText().toString()) < 10) {
                                                if (Integer.valueOf(thang.getText().toString()) < 10)
                                                    ngaysinh = "0" + Integer.valueOf(ngay.getText().toString())
                                                            + "-0" + Integer.valueOf(thang.getText().toString())
                                                            + "-" + namsinh.getText().toString();
                                                else
                                                    ngaysinh = "0" + Integer.valueOf(ngay.getText().toString())
                                                            + "-" + thang.getText().toString()
                                                            + "-" + namsinh.getText().toString();
                                            } else {
                                                if (Integer.valueOf(thang.getText().toString()) < 10)
                                                    ngaysinh = ngay.getText().toString()
                                                            + "-0" + Integer.valueOf(thang.getText().toString())
                                                            + "-" + namsinh.getText().toString();
                                                else
                                                    ngaysinh = ngay.getText().toString()
                                                            + "-" + thang.getText().toString()
                                                            + "-" + namsinh.getText().toString();
                                            }
                                            reference.child(ID).child("NgaySinh").setValue(ngaysinh);
                                        }
                                        if (nam.isChecked())
                                            reference.child(ID).child("GioiTinh").setValue("Nam");
                                        else
                                            reference.child(ID).child("GioiTinh").setValue("Nữ");

                                        if (!diachi.getText().toString().trim().equals(""))
                                            reference.child(ID).child("DiaChi").setValue(diachi.getText().toString().trim());
                                        Toast.makeText(ProfileUserActivity.this, "Đã lưu chỉnh sửa", Toast.LENGTH_SHORT).show();
                                        load();
                                    }

                                } else {
                                    ContentResolver contentResolver = getContentResolver();
                                    MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

                                    StorageReference fileReference = khoAnh.child(System.currentTimeMillis() + "." + mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)));
                                    fileReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    if (hoten.getText().toString().trim().equals("")) {
                                                        Toast.makeText(ProfileUserActivity.this, "Tên không được bỏ trống", Toast.LENGTH_LONG).show();
                                                    } else {
                                                        reference.child(ID).child("Name").setValue(hoten.getText().toString().trim());
                                                        if (!sodienthoai.getText().toString().trim().equals(""))
                                                            reference.child(ID).child("Phone").setValue(sodienthoai.getText().toString().trim());
                                                        else
                                                            Toast.makeText(ProfileUserActivity.this, "Số điện thoại không được bỏ trống", Toast.LENGTH_LONG).show();
                                                        if (!email.getText().toString().trim().equals(""))
                                                            reference.child(ID).child("Email").setValue(email.getText().toString().trim());
                                                        else
                                                            Toast.makeText(ProfileUserActivity.this, "Email không được bỏ trống", Toast.LENGTH_LONG).show();

                                                        if (!ngay.getText().toString().trim().equals("")
                                                                && !thang.getText().toString().trim().equals("")
                                                                && !namsinh.getText().toString().trim().equals("")) {
                                                            String ngaysinh = "";
                                                            if (Integer.valueOf(ngay.getText().toString()) < 10) {
                                                                if (Integer.valueOf(thang.getText().toString()) < 10)
                                                                    ngaysinh = "0" + Integer.valueOf(ngay.getText().toString())
                                                                            + "-0" + Integer.valueOf(thang.getText().toString())
                                                                            + "-" + namsinh.getText().toString();
                                                                else
                                                                    ngaysinh = "0" + Integer.valueOf(ngay.getText().toString())
                                                                            + "-" + thang.getText().toString()
                                                                            + "-" + namsinh.getText().toString();
                                                            } else {
                                                                if (Integer.valueOf(thang.getText().toString()) < 10)
                                                                    ngaysinh = ngay.getText().toString()
                                                                            + "-0" + Integer.valueOf(thang.getText().toString())
                                                                            + "-" + namsinh.getText().toString();
                                                                else
                                                                    ngaysinh = ngay.getText().toString()
                                                                            + "-" + thang.getText().toString()
                                                                            + "-" + namsinh.getText().toString();
                                                            }
                                                            reference.child(ID).child("NgaySinh").setValue(ngaysinh);
                                                        }
                                                        if (nam.isChecked())
                                                            reference.child(ID).child("GioiTinh").setValue("Nam");
                                                        else
                                                            reference.child(ID).child("GioiTinh").setValue("Nữ");

                                                        if (!diachi.getText().toString().trim().equals(""))
                                                            reference.child(ID).child("DiaChi").setValue(diachi.getText().toString().trim());
                                                        reference.child(ID).child("HinhDaiDien").setValue(uri.toString());
                                                        Toast.makeText(ProfileUserActivity.this, "Đã lưu chỉnh sửa", Toast.LENGTH_SHORT).show();
                                                        load();
                                                    }
                                                }
                                            });
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(ProfileUserActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                });
                mydialog.setNegativeButton("[QUAY LẠI]", new DialogInterface.OnClickListener() {
                    @SuppressLint("WrongConstant")
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog alertDialog = mydialog.create();
                alertDialog.show();
            }
        });
    }


    private void load() {
        DatabaseReference myReference = FirebaseDatabase.getInstance().getReference("InforUser");
        myReference.child(ID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    HashMap<String, Object> hashMap = (HashMap<String, Object>) snapshot.getValue();
                    if (hashMap.get("Name") != null)
                        hoten.setText(hashMap.get("Name").toString());
                    if (hashMap.get("Email") != null)
                        email.setText(hashMap.get("Email").toString());
                    if (hashMap.get("Phone") != null)
                        sodienthoai.setText(hashMap.get("Phone").toString());
                    if (hashMap.get("NgaySinh") != null) {
                        ngaysinh = hashMap.get("NgaySinh").toString();
                        String[] ngaythangnamsinh = ngaysinh.split("-");
                        ngay.setText(ngaythangnamsinh[0]);
                        thang.setText(ngaythangnamsinh[1]);
                        namsinh.setText(ngaythangnamsinh[2]);
                    }
                    if (hashMap.get("HinhDaiDien") != null) {
                        Picasso.with(ProfileUserActivity.this).load(hashMap.get("HinhDaiDien").toString()).into(ava);
                        avarCu = hashMap.get("HinhDaiDien").toString();
                    } else {
                        avarCu = "ic_baseline_account_circle_24";
                    }
                    if (hashMap.get("GioiTinh") != null) {
                        gioitinh = hashMap.get("GioiTinh").toString();
                        if (gioitinh.equals("Nam"))
                            nam.setChecked(true);
                        else
                            nu.setChecked(true);
                    }
                    if (hashMap.get("DiaChi") != null) {
                        diachi.setText(hashMap.get("DiaChi").toString());
                    }
                    hoten.setEnabled(false);
                    email.setEnabled(false);
                    sodienthoai.setEnabled(false);
                    ngay.setEnabled(false);
                    thang.setEnabled(false);
                    namsinh.setEnabled(false);
                    nam.setEnabled(false);
                    nu.setEnabled(false);
                    diachi.setEnabled(false);
                    if (viewOf.equals("Admin"))
                        sua.setVisibility(View.INVISIBLE);
                    else
                        sua.setVisibility(View.VISIBLE);
                    luu.setVisibility(View.INVISIBLE);
                    huy.setVisibility(View.INVISIBLE);
                    changepro.setVisibility(View.GONE);
                } catch (Exception e) {
                    Log.d("Loi JSON", e.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("Loi chi tiet", "LoadPost:onCanceled", error.toException());
            }
        });
    }

    private void matching() {
        sua = (Button) findViewById(R.id.btn_pf_edit);
        luu = (Button) findViewById(R.id.btn_pf_luu);
        huy = (Button) findViewById(R.id.btn_pf_huy);
        hoten = (EditText) findViewById(R.id.et_pf_hoten);
        email = (EditText) findViewById(R.id.et_pf_email);
        ngay = (EditText) findViewById(R.id.et_pf_ngay);
        thang = (EditText) findViewById(R.id.et_pf_thang);
        namsinh = (EditText) findViewById(R.id.et_pf_namsinh);
        nam = (RadioButton) findViewById(R.id.rdo_pf_nam);
        nu = (RadioButton) findViewById(R.id.rdo_pf_nu);
        diachi = (EditText) findViewById(R.id.et_pf_diachi);
        sodienthoai = (EditText) findViewById(R.id.et_pf_sdt);
        back = (ImageView) findViewById(R.id.img_profile_back);
        changepro = (ImageView) findViewById(R.id.img_change_profile);
        ava = (ImageView) findViewById(R.id.img_avatar_profile);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}