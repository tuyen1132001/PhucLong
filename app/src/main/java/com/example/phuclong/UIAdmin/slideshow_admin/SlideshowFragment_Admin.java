package com.example.phuclong.UIAdmin.slideshow_admin;

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

import com.example.phuclong.databinding.FragmentSlideshowBinding;
import com.example.phuclong.databinding.FragmentSlideshowAdminBinding;

public class SlideshowFragment_Admin extends Fragment {

    private SlideshowViewModel_Admin slideshowViewModelAdmin;
    private FragmentSlideshowAdminBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModelAdmin =
                new ViewModelProvider(this).get(SlideshowViewModel_Admin.class);

        binding = FragmentSlideshowAdminBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textSlideshowAdmin;
        slideshowViewModelAdmin.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
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