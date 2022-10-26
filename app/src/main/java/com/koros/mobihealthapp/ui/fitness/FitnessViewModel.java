package com.koros.mobihealthapp.ui.fitness;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FitnessViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public FitnessViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is fitness fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}