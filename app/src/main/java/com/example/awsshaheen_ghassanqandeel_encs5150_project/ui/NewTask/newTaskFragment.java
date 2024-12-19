package com.example.awsshaheen_ghassanqandeel_encs5150_project.ui.NewTask;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.awsshaheen_ghassanqandeel_encs5150_project.CompletionStatus;
import com.example.awsshaheen_ghassanqandeel_encs5150_project.DataBaseHelper;
import com.example.awsshaheen_ghassanqandeel_encs5150_project.EmailSharedPrefManager;
import com.example.awsshaheen_ghassanqandeel_encs5150_project.PriorityLevel;
import com.example.awsshaheen_ghassanqandeel_encs5150_project.Task;
import com.example.awsshaheen_ghassanqandeel_encs5150_project.databinding.FragmentNewtaskBinding;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class newTaskFragment extends Fragment {

    private FragmentNewtaskBinding binding;
    private Boolean putReminder = false;
    private String selectedPriority;
    private String selectedStatus;
    private String selectedDate;
    private String userEmail;
    private EmailSharedPrefManager sharedEmail;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        newTaskViewModel newTaskViewModel =
                new ViewModelProvider(this).get(newTaskViewModel.class);
        sharedEmail =EmailSharedPrefManager.getInstance(getContext());

        userEmail=sharedEmail.readString("Email",null);
        if (userEmail != null) {
            Toast.makeText(getContext(),userEmail, Toast.LENGTH_SHORT).show();
        }


        binding = FragmentNewtaskBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        EditText dueDate = binding.taskDateEditText;
        CheckBox reminder = binding.addReminderCheckBox;
        CheckBox noReminder = binding.noReminderCheckBox;
        DataBaseHelper dataBaseHelper = DataBaseHelper.getInstance(getContext());
        reminder.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                putReminder = true;
                noReminder.setChecked(false);
            }

        });

        noReminder.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
                reminder.setChecked(false);
        });

        dueDate.setOnClickListener(c -> {
            // Create a Calendar instance
            final Calendar calendar = Calendar.getInstance();

            // Open the DatePickerDialog
            DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                    (view, year, month, dayOfMonth) -> {
                        // Format the selected date
                        calendar.set(year, month, dayOfMonth);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        selectedDate = dateFormat.format(calendar.getTime());

                        // Set the date to the EditText
                        dueDate.setText(selectedDate);
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );

            // Show the DatePickerDialog
            datePickerDialog.show();
        });


        Spinner prioritySpinner = binding.spinner;


        // Create a list of items
        List<String> items = new ArrayList<String>();
        items.add("LOW");
        items.add("MEDIUM");
        items.add("HIGH");


        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(), android.R.layout.simple_spinner_item, items
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prioritySpinner.setAdapter(adapter);


        prioritySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 selectedPriority = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedPriority=PriorityLevel.MEDIUM.toString();
            }
        });

        Spinner stateSpinner = binding.spinner2;
        List<String> stateItems = new ArrayList<String>();
        stateItems.add("COMPLETED");
        stateItems.add("IN_PROGRESS");
        ArrayAdapter<String> stateAdapter = new ArrayAdapter<>(
                requireContext(), android.R.layout.simple_spinner_item, stateItems
        );

        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(stateAdapter);

        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedStatus = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedStatus=CompletionStatus.IN_PROGRESS.toString();
            }
        });


        binding.addTaskButton.setOnClickListener(v -> {

            String taskTitle = Objects.requireNonNull(binding.taskTitleEditText.getText()).toString();
            String taskDescription = binding.taskDescriptionEditText.getText().toString();
            String Date = dueDate.getText().toString();
            PriorityLevel priorityLevel = PriorityLevel.valueOf(selectedPriority);
            CompletionStatus completionStatus = CompletionStatus.valueOf(selectedStatus);
            Task task = new Task();
            task.setEmail(userEmail);
            task.setTittle(taskTitle);
            task.setDescription(taskDescription);
            task.setDueDate(Date);
            task.setCompletionStatus(completionStatus);
            task.setPriorityLevel(priorityLevel);
            task.setReminder(putReminder);
            dataBaseHelper.insertTask(task);
        });
        final TextView textView = binding.addNewtextView;

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}