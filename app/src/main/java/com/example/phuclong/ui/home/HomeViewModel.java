package com.example.phuclong.ui.home;

import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeViewModel extends ViewModel {


    private MutableLiveData<String> mText;

    public HomeViewModel() {

        mText = new MutableLiveData<>();
        mText.setValue("");



    }

    public LiveData<String> getText() {
        return mText;
    }
}