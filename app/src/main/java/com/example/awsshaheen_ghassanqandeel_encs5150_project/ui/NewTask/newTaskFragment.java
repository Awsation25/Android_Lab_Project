package com.example.awsshaheen_ghassanqandeel_encs5150_project.ui.NewTask;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.awsshaheen_ghassanqandeel_encs5150_project.CompletionStatus;
import com.example.awsshaheen_ghassanqandeel_encs5150_project.DataBaseHelper;
import com.example.awsshaheen_ghassanqandeel_encs5150_project.PriorityLevel;
import com.example.awsshaheen_ghassanqandeel_encs5150_project.Task;
import com.example.awsshaheen_ghassanqandeel_encs5150_project.databinding.FragmentNewtaskBinding;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class newTaskFragment extends Fragment {

    private FragmentNewtaskBinding binding;
    private Boolean putReminder=false;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        newTaskViewModel newTaskViewModel =
                new ViewModelProvider(this).get(newTaskViewModel.class);

        binding = FragmentNewtaskBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        EditText dueDate=binding.taskDateEditText;
        CheckBox reminder=binding.addReminderCheckBox;
        CheckBox noReminder=binding.noReminderCheckBox;
        DataBaseHelper dataBaseHelper = DataBaseHelper.getInstance(getContext());
        reminder.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                putReminder=true;
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
                        String selectedDate = dateFormat.format(calendar.getTime());

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


        binding.addTaskButton.setOnClickListener(v ->{
            String taskTitle=binding.taskTitleEditText.getText().toString();
            String taskDescription=binding.taskDescriptionEditText.getText().toString();
            String Date=dueDate.getText().toString();
            PriorityLevel priorityLevel= PriorityLevel.valueOf(binding.priorityLevelEditText.getText().toString());
            CompletionStatus completionStatus= CompletionStatus.valueOf(binding.completionStatusEditText.getText().toString());
            Task task =new Task();
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