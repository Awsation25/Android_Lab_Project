package com.example.awsshaheen_ghassanqandeel_encs5150_project.ui.displayAll;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.awsshaheen_ghassanqandeel_encs5150_project.CompletionStatus;
import com.example.awsshaheen_ghassanqandeel_encs5150_project.DataBaseHelper;
import com.example.awsshaheen_ghassanqandeel_encs5150_project.Task;
import com.example.awsshaheen_ghassanqandeel_encs5150_project.databinding.FragmentHomeBinding;
import com.example.awsshaheen_ghassanqandeel_encs5150_project.databinding.FragmentSlideshowBinding;

import java.util.ArrayList;
import java.util.List;

public class DisplayAllFragment extends Fragment {

    private FragmentSlideshowBinding binding;
    Button newBtn;
    private List<Task> tasks;
    private List<String> filteredTasks;
    TextView taskText;
    String selectedStatus;
    boolean filterButtonClicked=false;
    String textToFind;
    ListView listView;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DisplayAllViewModel displayAllViewModel =
                new ViewModelProvider(this).get(DisplayAllViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        DataBaseHelper dataBaseHelper = DataBaseHelper.getInstance(getContext());

        // Display tasks in the TextView
        LinearLayout layout=binding.vLayout ;
        filteredTasks = new ArrayList<>();
        List<Button> buttonList = new ArrayList<>();
        List<Button> filteredButtonList = new ArrayList<>();
        LinearLayout popupView = new LinearLayout(getContext());
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
        params.setMargins(0, 0, 0, 16);

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

        tasks = dataBaseHelper.getAllTasks();
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

        SearchView search_bar=binding.search;
        for (Task task : tasks) {
            filteredTasks.add(task.toString()); // Assuming `toString()` returns the task title
        }
        search_bar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                layout.removeAllViews();
                filteredButtonList.clear();
                for (Task task : tasks) {
                    if (task.toString().toLowerCase().contains(query.toLowerCase())) {
                        Button taskButton = new Button(requireContext());
                        taskButton.setText(task.toString());
                        taskButton.setBackground(background3);
                        taskButton.setTextColor(Color.WHITE);
                        taskButton.setLayoutParams(params);
                        layout.addView(taskButton);
                        filteredButtonList.add(taskButton);
                    }}
                for (int i=0;i<filteredButtonList.size();i++){
                    Button button=filteredButtonList.get(i);
                    taskText=new TextView(getContext());
                    taskText.setTextColor(Color.WHITE);
                    taskText.setTextSize(20);
                    popupView.addView(taskText);
                    Task task = tasks.get(i);
                    button.setOnClickListener(view -> {
                        taskText.setText("");
                        taskText.setText(task.toString());

                        popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);
                        delete.setOnClickListener(v5 -> {
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

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                layout.removeAllViews();
                filteredButtonList.clear();
                for (Task task : tasks) {
                    if (task.toString().toLowerCase().contains(newText.toLowerCase())) {
                        Button taskButton = new Button(requireContext());
                        taskButton.setText(task.toString());
                        taskButton.setBackground(background3);
                        taskButton.setLayoutParams(params);
                        taskButton.setTextColor(Color.WHITE);
                        filteredButtonList.add(taskButton);
                        layout.addView(taskButton);

                    }
                }

                for (int i=0;i<filteredButtonList.size();i++){
                    Button button=filteredButtonList.get(i);
                    taskText=new TextView(getContext());
                    taskText.setTextColor(Color.WHITE);
                    taskText.setTextSize(20);
                    popupView.addView(taskText);
                    Task task = tasks.get(i);
                    button.setOnClickListener(view -> {
                        taskText.setText("");
                        taskText.setText(task.toString());

                        popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);
                        delete.setOnClickListener(v5 -> {
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



                return false;
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