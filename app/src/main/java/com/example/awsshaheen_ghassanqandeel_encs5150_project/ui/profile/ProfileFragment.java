package com.example.awsshaheen_ghassanqandeel_encs5150_project.ui.profile;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.awsshaheen_ghassanqandeel_encs5150_project.R;
import com.example.awsshaheen_ghassanqandeel_encs5150_project.databinding.FragmentProfileBinding;
import com.example.awsshaheen_ghassanqandeel_encs5150_project.databinding.FragmentSearchBinding;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding= FragmentProfileBinding.inflate(inflater, container, false);
        View root =binding.getRoot();


        return root;

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;

    }



}