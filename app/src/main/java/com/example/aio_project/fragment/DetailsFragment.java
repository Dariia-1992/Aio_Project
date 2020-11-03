package com.example.aio_project.fragment;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aio_project.R;
import com.example.aio_project.model.DataRepository;
import com.example.aio_project.model.ModelDTO;

import java.util.Objects;

import androidx.fragment.app.Fragment;

/**
 * Created by Alexey Matrosov on 27.10.2020.
 */

public class DetailsFragment extends Fragment {
    private View view;
    private ModelDTO entry;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_details, container, false);

        Bundle args = getArguments();
        if (args == null)
            return view;

        String id = DetailsFragmentArgs.fromBundle(args).getItemId();
        entry = DataRepository.findById(id);
        if (entry == null)
            return view;

        TextView toolbarTitle = view.findViewById(R.id.toolbarTitle);
        toolbarTitle.setText(entry.getTitle());

        View backButton = view.findViewById(R.id.toolbarIconBack);
        backButton.setOnClickListener(v -> requireActivity().onBackPressed());

        TextView text = view.findViewById(R.id.detailsText);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            text.setText(Html.fromHtml(entry.getDescription(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            text.setText(Html.fromHtml(entry.getDescription()));
        }

        return view;
    }
}
