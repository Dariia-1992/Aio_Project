package com.example.aio_project.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aio_project.R;
import com.example.aio_project.adapter.TabContentAdapter;
import com.example.aio_project.model.Category;
import com.example.aio_project.model.DataRepository;
import com.example.aio_project.model.ModelDTO;
import com.example.aio_project.utils.LocalStorage;
import com.example.aio_project.utils.TextUtils;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Alexey Matrosov on 27.10.2020.
 */

public class TabContentFragment extends Fragment {
    public enum SortType { Newest, Downloads, Views }

    private static final String SERVER_NAME_EXTRA = "server_name_extra";
    private static final String CATEGORY_EXTRA = "category_extra";

    private IMainFragment mainFragment;
    private View view;
    private TabContentAdapter adapter;
    private View notFoundContainer;

    private String searchStr;
    private Category category;
    private String serverName;

    private SortType sortType;
    private final List<ModelDTO> sourceItems = new ArrayList<>();
    private final List<ModelDTO> visibleItems = new ArrayList<>();

    public static TabContentFragment createFragment(Category category, String serverName, IMainFragment mainFragment) {
        Bundle bundle = new Bundle();
        bundle.putString(SERVER_NAME_EXTRA, serverName);
        bundle.putInt(CATEGORY_EXTRA, category.ordinal());

        TabContentFragment fragment = new TabContentFragment();
        fragment.setArguments(bundle);
        fragment.setMainFragment(mainFragment);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            serverName = bundle.getString(SERVER_NAME_EXTRA);

            int categoryIndex = bundle.getInt(CATEGORY_EXTRA);
            if (categoryIndex >= Category.values().length || categoryIndex < 0)
                categoryIndex = 0;

            category = Category.values()[categoryIndex];
        }

        sortType = LocalStorage.readSortForCategory(requireContext(), serverName);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tab_content, container, false);
        notFoundContainer = view.findViewById(R.id.notFoundLabel);

        notFoundContainer.setVisibility(View.GONE);

        // Init adapter
        adapter = new TabContentAdapter(visibleItems, category, itemClickListener);

        // Init recyclerView
        RecyclerView recyclerView = view.findViewById(R.id.itemsContainer);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        DataRepository.loadDataAsync(category, serverName, items -> {
            sourceItems.clear();
            sourceItems.addAll(items);

            updateCurrentItems();
        }, message -> {

        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        searchStr = mainFragment.getSearchStr();
        updateCurrentItems();
        mainFragment.initAdBanner();
    }

    public void setMainFragment(IMainFragment mainFragment) {
        this.mainFragment = mainFragment;
    }

    public void changeSortType(SortType type) {
        sortType = type;
        updateCurrentItems();

        LocalStorage.saveSortForCategory(requireContext(), serverName, type);
    }

    public void setSearchText(String text) {
        searchStr = text;
        updateCurrentItems();
    }

    private void updateCurrentItems() {
        visibleItems.clear();

        // Search
        for (ModelDTO entry : sourceItems) {
            if (containsIgnoreCase(entry.getTitle(), searchStr))
                visibleItems.add(entry);
        }

        // Sort visible items
        sortItems();

        // Update UI
        notFoundContainer.setVisibility(visibleItems.isEmpty() && !android.text.TextUtils.isEmpty(searchStr) ? View.VISIBLE : View.GONE);

        // Update adapter
        adapter.notifyDataSetChanged();
    }

    private void sortItems() {
        Collections.sort(visibleItems, (left, right) -> {
            switch (sortType) {
                case Newest: {
                    return right.getTimestamp().compareTo(left.getTimestamp());
                }
                case Downloads: {
                    long leftValue = TextUtils.parseLong(left.getDownloadcount());
                    long rightValue = TextUtils.parseLong(right.getDownloadcount());
                    return Long.compare(rightValue, leftValue);
                }
                case Views: {
                    long leftValue = TextUtils.parseLong(left.getViewcount());
                    long rightValue = TextUtils.parseLong(right.getViewcount());
                    return Long.compare(rightValue, leftValue);
                }
                default: throw new IllegalStateException("Unknown sort type");
            }
        });
    }

    private static boolean containsIgnoreCase(String str, String searchStr) {
        if (str == null)
            return false;

        if (searchStr == null)
            return true;

        final int length = searchStr.length();
        if (length == 0)
            return true;

        for (int i = str.length() - length; i >= 0; i--) {
            if (str.regionMatches(true, i, searchStr, 0, length))
                return true;
        }
        return false;
    }

    private final TabContentAdapter.OnItemClickListener itemClickListener = item -> {
        if (mainFragment != null)
            mainFragment.goToDetails(item);
    };
}
