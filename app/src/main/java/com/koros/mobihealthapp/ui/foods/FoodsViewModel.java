package com.koros.mobihealthapp.ui.foods;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FoodsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public FoodsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is foods fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}