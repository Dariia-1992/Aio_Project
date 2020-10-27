package com.example.aio_project.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aio_project.R;
import com.example.aio_project.adapter.TabContentAdapter;
import com.example.aio_project.model.ModelDTO;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Alexey Matrosov on 27.10.2020.
 */

public class TabContentFragment extends Fragment {
    private View view;
    private TabContentAdapter adapter;

    private final List<ModelDTO> visibleItems = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tab_content, container, false);

        // Init adapter
        adapter = new TabContentAdapter(visibleItems, itemClickListener);

        // Init recyclerView
        RecyclerView recyclerView = view.findViewById(R.id.itemsContainer);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }

    private final TabContentAdapter.OnItemClickListener itemClickListener = item -> {

    };
}
