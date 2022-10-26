package com.koros.mobihealthapp.ui.fitness;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.koros.mobihealthapp.databinding.FragmentFitnessBinding;

public class FitnessFragment extends Fragment {

    private FragmentFitnessBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FitnessViewModel fitnessViewModel =
                new ViewModelProvider(this).get(FitnessViewModel.class);

        binding = FragmentFitnessBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textFitness;
        fitnessViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}