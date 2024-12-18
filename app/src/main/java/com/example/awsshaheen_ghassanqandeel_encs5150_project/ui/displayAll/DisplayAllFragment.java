package com.example.awsshaheen_ghassanqandeel_encs5150_project.ui.displayAll;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.awsshaheen_ghassanqandeel_encs5150_project.DataBaseHelper;
import com.example.awsshaheen_ghassanqandeel_encs5150_project.databinding.FragmentSlideshowBinding;

public class DisplayAllFragment extends Fragment {

    private FragmentSlideshowBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DisplayAllViewModel displayAllViewModel =
                new ViewModelProvider(this).get(DisplayAllViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        DataBaseHelper dataBaseHelper = DataBaseHelper.getInstance(getContext());
        final TextView textView = binding.addNewtextView;
        String tasks = dataBaseHelper.getAllTasksSorted();
        if (tasks.isEmpty()) {
            textView.setText("No tasks found.");
        } else {
            textView.setText(tasks);
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}