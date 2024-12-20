package com.example.awsshaheen_ghassanqandeel_encs5150_project;

import android.app.Application;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TaskJSONParser {
    private static Context context;

    public TaskJSONParser(Context context) {
        TaskJSONParser.context = context;
    }

    private static EmailSharedPrefManager sharedEmail = EmailSharedPrefManager.getInstance(context);
    private static String userEmail = sharedEmail.readString("Email", null);

    public static List<Task> getObjectFromJason(String jason) {
        List<Task> tasks;
        try {
            JSONArray jsonArray = new JSONArray(jason);
            tasks = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = new JSONObject();
                jsonObject = (JSONObject) jsonArray.get(i);
                Task task = new Task();


                task.setEmail(userEmail);
                task.setTittle(jsonObject.getString("title"));
                task.setDescription(jsonObject.getString("description"));
                task.setDueDate(jsonObject.getString("dueDate"));
                task.setPriorityLevel(PriorityLevel.valueOf(jsonObject.getString("priorityLevel").toUpperCase()));
                task.setCompletionStatus(CompletionStatus.valueOf(jsonObject.getString("completionStatus").toUpperCase()));
                task.setReminder(jsonObject.getBoolean("reminder"));

                tasks.add(task);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return tasks;
    }
}
