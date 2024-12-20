package com.example.awsshaheen_ghassanqandeel_encs5150_project.ui.profile;

import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.awsshaheen_ghassanqandeel_encs5150_project.DataBaseHelper;
import com.example.awsshaheen_ghassanqandeel_encs5150_project.R;
import com.example.awsshaheen_ghassanqandeel_encs5150_project.databinding.FragmentProfileBinding;
import com.example.awsshaheen_ghassanqandeel_encs5150_project.databinding.FragmentSearchBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class ProfileFragment extends Fragment {


    private FragmentProfileBinding binding;
    private String oldEmail = null;
    private String newEmail = null;
    private String newPassword = null;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        DataBaseHelper dataBaseHelper = DataBaseHelper.getInstance(getContext());

        EditText oldEmailEditText = binding.oldEmailEditText;
        EditText newEmailEditText = binding.newEmailEditText;
        EditText newPasswordEditText = binding.newPasswordEditText;
        Button editButton = binding.editButton;
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldEmail = oldEmailEditText.getText() != null && !oldEmailEditText.getText().toString().trim().isEmpty()
                        ? oldEmailEditText.getText().toString().trim()
                        : null;

                String newEmail = newEmailEditText.getText() != null && !newEmailEditText.getText().toString().trim().isEmpty()
                        ? newEmailEditText.getText().toString().trim()
                        : null;

                String newPassword = newPasswordEditText.getText() != null && !newPasswordEditText.getText().toString().trim().isEmpty()
                        ? newPasswordEditText.getText().toString().trim()
                        : null;

                if (oldEmail == null && newEmail == null && newPassword == null) {
                    Toast.makeText(getContext(), "Enter at least one field to update", Toast.LENGTH_SHORT).show();
                } else {
                    dataBaseHelper.editEmailAndPassword(oldEmail, newEmail, newPassword);
                }

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