package com.example.phuclong.UIAdmin.gallery_admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.phuclong.databinding.FragmentGalleryAdminBinding;
import com.example.phuclong.databinding.FragmentGalleryBinding;
import com.example.phuclong.databinding.FragmentAdminBinding;

public class GalleryFragment_Admin extends Fragment {

    private GalleryViewModel_Admin galleryViewModelAdmin;
    private FragmentGalleryAdminBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModelAdmin =
                new ViewModelProvider(this).get(GalleryViewModel_Admin.class);

        binding = FragmentGalleryAdminBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textGalleryAdmin;
        galleryViewModelAdmin.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}