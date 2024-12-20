package com.example.awsshaheen_ghassanqandeel_encs5150_project.ui.home;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.awsshaheen_ghassanqandeel_encs5150_project.CompletionStatus;
import com.example.awsshaheen_ghassanqandeel_encs5150_project.DataBaseHelper;
import com.example.awsshaheen_ghassanqandeel_encs5150_project.PriorityLevel;
import com.example.awsshaheen_ghassanqandeel_encs5150_project.Task;
import com.example.awsshaheen_ghassanqandeel_encs5150_project.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    Button newBtn;
    private FragmentHomeBinding binding;
    private List<Task> tasks;
    TextView taskText;
    String selectedStatus;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
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


        Button delete= new Button(getContext());
        Button share=new Button(getContext());
        Button markCompleted= new Button(getContext());


        popupView.setOrientation(LinearLayout.VERTICAL);
        markCompleted.setText("Mark As Completed");
        markCompleted.setTextColor(Color.WHITE);
        markCompleted.setBackgroundColor(Color.GREEN);
        popupView.addView(markCompleted);
        share.setText("Share via Email");
        share.setTextColor(Color.WHITE);
        share.setBackgroundColor(Color.BLUE);
        popupView.addView(share);
        delete.setText("Delete Task");
        delete.setTextColor(Color.WHITE);
        delete.setBackgroundColor(Color.RED);
        popupView.addView(delete);
        tasks = dataBaseHelper.getAllTasks();

        for(Task task:tasks){
            newBtn = new Button(requireContext());
            newBtn.setText(task.toString());
            newBtn.setBackgroundColor(Color.BLUE);
            newBtn.setTextColor(Color.WHITE);
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

            });
        }
            return root;


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



}