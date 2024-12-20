package com.example.awsshaheen_ghassanqandeel_encs5150_project.ui.search;

import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.awsshaheen_ghassanqandeel_encs5150_project.CompletionStatus;
import com.example.awsshaheen_ghassanqandeel_encs5150_project.DataBaseHelper;
import com.example.awsshaheen_ghassanqandeel_encs5150_project.R;
import com.example.awsshaheen_ghassanqandeel_encs5150_project.Task;
import com.example.awsshaheen_ghassanqandeel_encs5150_project.databinding.FragmentCompletedTasksBinding;
import com.example.awsshaheen_ghassanqandeel_encs5150_project.databinding.FragmentSearchBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding;
    private String selectedStartDate;
    private String selectedEndDate;
    private List<Task> tasks;
    TextView taskText;
    Button newBtn;
    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding= FragmentSearchBinding.inflate(inflater, container, false);
        View root =binding.getRoot();
        Button search=binding.searchButton;
        EditText startDate=binding.startDate;
        EditText endDate=binding.endDate;
        startDate.setOnClickListener(c -> {
            // Create a Calendar instance
            final Calendar calendar = Calendar.getInstance();

            // Open the DatePickerDialog
            DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                    (view, year, month, dayOfMonth) -> {
                        // Format the selected date
                        calendar.set(year, month, dayOfMonth);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        selectedStartDate = dateFormat.format(calendar.getTime());
                        startDate.setText(selectedStartDate);
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );
            datePickerDialog.show();
        });
        endDate.setOnClickListener(c -> {

            final Calendar calendar = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                    (view, year, month, dayOfMonth) -> {

                        calendar.set(year, month, dayOfMonth);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        selectedEndDate = dateFormat.format(calendar.getTime());


                        endDate.setText(selectedEndDate);
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );
            datePickerDialog.show();
        });
        DataBaseHelper dataBaseHelper = DataBaseHelper.getInstance(getContext());

        // Display tasks in the TextView
        LinearLayout layout=binding.vLayout ;
        LinearLayout popupView = new LinearLayout(getContext());
        List<Button> buttonList = new ArrayList<>();
        popupView.setBackgroundColor(Color.GRAY);
        PopupWindow popupWindow = new PopupWindow(
                popupView,
                LinearLayout.LayoutParams.MATCH_PARENT,
                900,
                true
        );
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, 0, 16); // Bottom margin of 16dp

        Button delete= new Button(getContext());
        Button share=new Button(getContext());
        Button markCompleted= new Button(getContext());
        GradientDrawable background = new GradientDrawable();
        background.setShape(GradientDrawable.RECTANGLE);
        background.setColor(Color.parseColor("#6200EE"));
        background.setCornerRadius(50); // Rounded corners
        GradientDrawable background2 = new GradientDrawable();
        background2.setShape(GradientDrawable.RECTANGLE);
        background2.setColor(Color.RED);
        background2.setCornerRadius(50); // Rounded corners
        GradientDrawable background3 = new GradientDrawable();
        background3.setShape(GradientDrawable.RECTANGLE);
        background3.setColor(Color.BLUE);
        background3.setCornerRadius(50); // Rounded corners

        popupView.setOrientation(LinearLayout.VERTICAL);
        markCompleted.setText("Mark As Completed");
        markCompleted.setTextColor(Color.WHITE);
        markCompleted.setBackground(background);
        popupView.addView(markCompleted);
        share.setText("Share via Email");
        share.setTextColor(Color.WHITE);
        share.setBackground(background);
        popupView.addView(share);
        delete.setText("Delete Task");
        delete.setTextColor(Color.WHITE);
        delete.setBackground(background2);
        popupView.addView(delete);
        binding.searchButton.setOnClickListener(v1 -> {
            tasks = dataBaseHelper.findByDateTasks(selectedStartDate, selectedEndDate);

            for(Task task:tasks){
                newBtn = new Button(requireContext());
                newBtn.setText(task.toString());
                newBtn.setBackground(background3);
                newBtn.setTextColor(Color.WHITE);
                newBtn.setLayoutParams(params);
                layout.addView(newBtn);
                buttonList.add(newBtn);
            }

            for (int i=0;i<buttonList.size();i++){
                Button button=buttonList.get(i);
                Task task =tasks.get(i);
                taskText=new TextView(getContext());
                taskText.setTextColor(Color.WHITE);
                taskText.setTextSize(20);
                popupView.addView(taskText);

                button.setOnClickListener(view -> {
                    taskText.setText("");
                    taskText.setText(task.toString());

                    popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);
                    delete.setOnClickListener(v -> {
                        dataBaseHelper.deleteTaskById(task.getId());
                        layout.removeView(button);
                        popupWindow.dismiss();
                    });
                    markCompleted.setOnClickListener(view1 -> {
                        task.setCompletionStatus(CompletionStatus.COMPLETED);
                        taskText.setText("");
                        taskText.setText(task.toString());
                        dataBaseHelper.updateStatus(task);
                        button.setText(task.toString());
                    });
                    share.setOnClickListener(view1 -> {
                        shareTaskViaEmail(task);
                    });

                });
            }
        });


        return root;

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;

    }
    private void shareTaskViaEmail(Task task) {
        String subject = "Task: " + task.getTittle();
        String body = "Here are the details of the task:\n\n" +
                "Title: " + task.getTittle() + "\n" +
                "Description: " + task.getDescription() + "\n" +
                "Status: " + task.getCompletionStatus() + "\n";

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("message/rfc822");
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, body);
        startActivity(Intent.createChooser(emailIntent, "Send task via email..."));

    }
}