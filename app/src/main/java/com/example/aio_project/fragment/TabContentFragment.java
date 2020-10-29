package com.example.aio_project.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aio_project.R;
import com.example.aio_project.adapter.TabContentAdapter;
import com.example.aio_project.model.DataRepository;
import com.example.aio_project.model.ModelDTO;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Alexey Matrosov on 27.10.2020.
 */

public class TabContentFragment extends Fragment {
    private static final String SERVER_NAME_EXTRA = "server_name_extra";

    private View view;
    private TabContentAdapter adapter;
    private String serverName;

    private final List<ModelDTO> visibleItems = new ArrayList<>();

    public static TabContentFragment createFragment(String serverName) {
        Bundle bundle = new Bundle();
        bundle.putString(SERVER_NAME_EXTRA, serverName);

        TabContentFragment fragment = new TabContentFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            serverName = bundle.getString(SERVER_NAME_EXTRA);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tab_content, container, false);

        // Init adapter
        adapter = new TabContentAdapter(visibleItems, itemClickListener);

        // Init recyclerView
        RecyclerView recyclerView = view.findViewById(R.id.itemsContainer);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        DataRepository.loadDataAsync(serverName, items -> {
            visibleItems.clear();
            visibleItems.addAll(items);
            adapter.notifyDataSetChanged();
        }, message -> {

        });

        return view;
    }

    private final TabContentAdapter.OnItemClickListener itemClickListener = item -> {
        visibleItems.clear();
        adapter.notifyDataSetChanged();
    };
}
