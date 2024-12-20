package com.example.awsshaheen_ghassanqandeel_encs5150_project.ui.completedTasks;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.awsshaheen_ghassanqandeel_encs5150_project.CompletionStatus;
import com.example.awsshaheen_ghassanqandeel_encs5150_project.DataBaseHelper;
import com.example.awsshaheen_ghassanqandeel_encs5150_project.Task;
import com.example.awsshaheen_ghassanqandeel_encs5150_project.databinding.FragmentCompletedTasksBinding;

import java.util.ArrayList;
import java.util.List;

public class CompletedTasksFragment extends Fragment {
    private List<Task> tasks;
    TextView taskText;
    Button newBtn;
    private FragmentCompletedTasksBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
       CompletedTasksViewModel completedTasksViewModel =
               new ViewModelProvider(this).get(CompletedTasksViewModel.class);
        binding= FragmentCompletedTasksBinding.inflate(inflater, container, false);
        View root =binding.getRoot();
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
                true // Focusable to handle outside touches
        );
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        Button delete= new Button(getContext());
        Button share=new Button(getContext());
        Button markCompleted= new Button(getContext());
        GradientDrawable background = new GradientDrawable();
        background.setShape(GradientDrawable.RECTANGLE);
        background.setColor(Color.parseColor("#6200EE"));
        background.setCornerRadius(50);
        GradientDrawable background2 = new GradientDrawable();
        background2.setShape(GradientDrawable.RECTANGLE);
        background2.setColor(Color.RED);
        background2.setCornerRadius(50); // Rounded corners
        GradientDrawable background3 = new GradientDrawable();
        background3.setShape(GradientDrawable.RECTANGLE);
        background3.setColor(Color.BLUE);
        background3.setCornerRadius(50);
        popupView.setOrientation(LinearLayout.VERTICAL);
        markCompleted.setText("Mark As Completed");
        markCompleted.setTextColor(Color.WHITE);
        markCompleted.setBackground(background);
        popupView.addView(markCompleted);
        share.setText("Share via Email");
        share.setTextColor(Color.WHITE);
        share.setAllCaps(false);


        share.setBackground(background);

        share.setElevation(8f);
        popupView.addView(share);
        delete.setText("Delete Task");
        delete.setTextColor(Color.WHITE);
        delete.setBackground(background2);
        popupView.addView(delete);
        tasks = dataBaseHelper.findCompletedTasks();

        for(Task task:tasks){
            newBtn = new Button(requireContext());
            newBtn.setText(task.toString());
            newBtn.setBackgroundColor(Color.BLUE);
            newBtn.setTextColor(Color.WHITE);
            newBtn.setBackground(background3);
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
        return root;


    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void shareTaskViaEmail(Task task) {
        String subject = "Task: " + task.getTittle();
        String body = task.toString();

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("message/rfc822");
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, body);
        startActivity(Intent.createChooser(emailIntent, "Send task via email..."));

    }


}