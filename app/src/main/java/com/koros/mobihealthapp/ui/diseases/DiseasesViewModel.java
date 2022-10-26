package com.koros.mobihealthapp.ui.diseases;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DiseasesViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public DiseasesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is diseases fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}