package com.koros.mobihealthapp.ui.diseases;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.koros.mobihealthapp.databinding.FragmentDiseasesBinding;

public class DiseasesFragment extends Fragment {

    private FragmentDiseasesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DiseasesViewModel diseasesViewModel =
                new ViewModelProvider(this).get(DiseasesViewModel.class);

        binding = FragmentDiseasesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textDiseases;
        diseasesViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}