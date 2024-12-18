package com.example.awsshaheen_ghassanqandeel_encs5150_project.ui.NewTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class newTaskViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public newTaskViewModel() {
        mText = new MutableLiveData<>();

    }

    public LiveData<String> getText() {
        return mText;
    }
}