package com.example.phuclong.UIAdmin.slideshow_admin;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SlideshowViewModel_Admin extends ViewModel {

    private MutableLiveData<String> mText;

    public SlideshowViewModel_Admin() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}