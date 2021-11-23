package com.example.phuclong.UIAdmin.gallery_admin;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GalleryViewModel_Admin extends ViewModel {

    private MutableLiveData<String> mText;

    public GalleryViewModel_Admin() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}