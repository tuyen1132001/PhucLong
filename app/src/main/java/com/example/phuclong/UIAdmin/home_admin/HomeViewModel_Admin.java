package com.example.phuclong.UIAdmin.home_admin;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel_Admin extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModel_Admin() {
        mText = new MutableLiveData<>();
        mText.setValue("");
    }

    public LiveData<String> getText() {
        return mText;
    }
}