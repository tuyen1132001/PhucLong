package com.example.phuclong.UIAdmin.home_admin;

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

import com.example.phuclong.databinding.FragmentHomeBinding;
import com.example.phuclong.databinding.FragmentAdminBinding;
import com.example.phuclong.databinding.FragmentGalleryAdminBinding;
import com.example.phuclong.databinding.FragmentGalleryBinding;

public class HomeFragment_Admin extends Fragment {

    private HomeViewModel_Admin homeViewModelAdmin;
    private FragmentAdminBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModelAdmin =
                new ViewModelProvider(this).get(HomeViewModel_Admin.class);

        binding = FragmentAdminBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHomeAdmin;
        homeViewModelAdmin.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
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