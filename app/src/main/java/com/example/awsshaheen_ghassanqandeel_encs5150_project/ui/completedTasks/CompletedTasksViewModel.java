package com.example.awsshaheen_ghassanqandeel_encs5150_project.ui.completedTasks;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CompletedTasksViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public CompletedTasksViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }
    public LiveData<String> getText() {
        return mText;
    }
}