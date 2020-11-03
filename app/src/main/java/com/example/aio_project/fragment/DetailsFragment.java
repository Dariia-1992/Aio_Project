package com.example.aio_project.fragment;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.aio_project.R;
import com.example.aio_project.model.DataRepository;
import com.example.aio_project.model.ModelDTO;
import com.example.aio_project.utils.TextUtils;

import java.util.Objects;

import androidx.fragment.app.Fragment;
import androidx.transition.TransitionManager;

/**
 * Created by Alexey Matrosov on 27.10.2020.
 */

public class DetailsFragment extends Fragment {
    private View view;
    private ModelDTO entry;

    private View downloadButton;
    private View installButton;
    private View progressContainer;
    private ProgressBar downloadingProgress;

    private View detailsContainer;
    private View readMoreButton;
    private TextView detailsText;

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

        downloadButton = view.findViewById(R.id.downloadButtonContainer);
        installButton = view.findViewById(R.id.installButtonContainer);
        progressContainer = view.findViewById(R.id.progressBarContainer);
        downloadingProgress = view.findViewById(R.id.progressBar);
        detailsContainer = view.findViewById(R.id.detailsContainer);
        readMoreButton = view.findViewById(R.id.detailsReadMore);
        detailsText = view.findViewById(R.id.detailsText);

        initViews();

        return view;
    }

    private void initViews() {
        readMoreButton.setVisibility(View.GONE);
        detailsText.setVisibility(View.GONE);

        // Init text part
        String description = entry.getDescription();
        if (description != null) {
            readMoreButton.setVisibility(View.VISIBLE);
            TextUtils.setHtmlText(description, detailsText);
        }

        // Click listeners
        readMoreButton.setOnClickListener(readMoreButtonClickListener);
    }

    private final View.OnClickListener readMoreButtonClickListener = v -> {
        TransitionManager.beginDelayedTransition((RelativeLayout) view);

        detailsText.setVisibility(View.VISIBLE);
        readMoreButton.setVisibility(View.GONE);
    };
}
