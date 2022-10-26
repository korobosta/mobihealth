package com.koros.mobihealthapp.ui.foods;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.koros.mobihealthapp.databinding.FragmentFoodsBinding;

public class FoodsFragment extends Fragment {

    private FragmentFoodsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FoodsViewModel foodsViewModel =
                new ViewModelProvider(this).get(FoodsViewModel.class);

        binding = FragmentFoodsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textFoods;
        foodsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}