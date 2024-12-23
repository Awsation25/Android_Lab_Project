package com.example.awsshaheen_ghassanqandeel_encs5150_project.ui.home;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.awsshaheen_ghassanqandeel_encs5150_project.CompletionStatus;
import com.example.awsshaheen_ghassanqandeel_encs5150_project.DataBaseHelper;
import com.example.awsshaheen_ghassanqandeel_encs5150_project.PriorityLevel;
import com.example.awsshaheen_ghassanqandeel_encs5150_project.R;
import com.example.awsshaheen_ghassanqandeel_encs5150_project.Task;
import com.example.awsshaheen_ghassanqandeel_encs5150_project.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    Button newBtn;
    private FragmentHomeBinding binding;
    private List<Task> tasks;
    private List<String> filteredTasks;
    TextView taskText;
    String selectedStatus;
    boolean filterButtonClicked=false;
    String textToFind;
    // Constants for Notification
    private static final String MY_CHANNEL_ID = "task_notifications";
    private static final String MY_CHANNEL_NAME = "Task Notifications";
    private static final int NOTIFICATION_ID = 101;

    private void createNotification(String title, String body) {
        NotificationManager notificationManager = requireContext().getSystemService(NotificationManager.class);
        if (notificationManager != null) {
            NotificationChannel channel = new NotificationChannel(
                    MY_CHANNEL_ID,
                    MY_CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("Notifications for tasks");
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(requireContext(), MY_CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(body));

        // Step 3: Display the notification
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(requireContext());
        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        DataBaseHelper dataBaseHelper = DataBaseHelper.getInstance(getContext());

        // Display tasks in the TextView
        LinearLayout layout=binding.vLayout ;
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
        Button setNotify=new Button(getContext());
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
        popupView.addView(setNotify);
        setNotify.setText("Set Notify");

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
                setNotify.setOnClickListener(v -> createNotification(task.getTittle(),task.getDescription()));

            });
        }
        filteredTasks = new ArrayList<>();

        SearchView search_bar=binding.search;
        for (Task task : tasks) {
            filteredTasks.add(task.toString()); // Assuming `toString()` returns the task title
        }
        search_bar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                layout.removeAllViews(); // Clear all existing views in the layout

                boolean found = false;
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