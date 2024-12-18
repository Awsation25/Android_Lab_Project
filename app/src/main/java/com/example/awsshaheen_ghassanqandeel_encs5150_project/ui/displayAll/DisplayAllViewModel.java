package com.example.awsshaheen_ghassanqandeel_encs5150_project.ui.displayAll;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DisplayAllViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public DisplayAllViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}