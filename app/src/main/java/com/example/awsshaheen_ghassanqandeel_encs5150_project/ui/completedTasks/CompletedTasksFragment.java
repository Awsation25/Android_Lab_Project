package com.example.awsshaheen_ghassanqandeel_encs5150_project.ui.completedTasks;

import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.awsshaheen_ghassanqandeel_encs5150_project.databinding.FragmentCompletedTasksBinding;
public class CompletedTasksFragment extends Fragment {

    private FragmentCompletedTasksBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
       CompletedTasksViewModel completedTasksViewModel =
               new ViewModelProvider(this).get(CompletedTasksViewModel.class);
        binding= FragmentCompletedTasksBinding.inflate(inflater, container, false);
        View root =binding.getRoot();
        binding.tttttt.setText("awss");

        return root;

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



}