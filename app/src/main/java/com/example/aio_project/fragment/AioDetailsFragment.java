package com.example.aio_project.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.aio_project.MainActivity;
import com.example.aio_project.PremiumActivity;
import com.example.aio_project.R;
import com.example.aio_project.model.ModelDTO;

public class AioDetailsFragment extends Fragment {
    public static final String ARG_ITEM_ID = "itemId";
    private View view;
    private View readMoreButton;
    private ModelDTO skins;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_aio_details, container, false);

        ImageView vip = view.findViewById(R.id.vip);
        vip.setOnClickListener(view1 -> getVip());

        MainActivity activity = (MainActivity) requireActivity();
        ImageView backButton = view.findViewById(R.id.back_icon);
        backButton.setOnClickListener(view1 -> activity.onBackPressed());
        readMoreButton = view.findViewById(R.id.read_more_view);
        readMoreButton.setOnClickListener(view1 -> openDetails());

        return view;
    }

    private void getVip() {
        Intent intent = new Intent(getActivity(), PremiumActivity.class);
        startActivity(intent);
    }

    private void openDetails() {
        readMoreButton.setVisibility(View.GONE);
        TextView details = view.findViewById(R.id.descriptionView);

        details.setVisibility(View.VISIBLE);
    }
}
