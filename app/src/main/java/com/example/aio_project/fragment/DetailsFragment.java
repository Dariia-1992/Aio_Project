package com.example.aio_project.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aio_project.R;

import java.util.Objects;

import androidx.fragment.app.Fragment;

/**
 * Created by Alexey Matrosov on 27.10.2020.
 */

public class DetailsFragment extends Fragment {
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_details, container, false);

        View backButton = view.findViewById(R.id.toolbarIconBack);
        backButton.setOnClickListener(v -> requireActivity().onBackPressed());

        return view;
    }
}
