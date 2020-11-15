package com.example.aio_project.fragment;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.aio_project.R;
import com.example.aio_project.TabInfo;
import com.example.aio_project.adapter.TabsAdapter;
import com.example.aio_project.model.Category;
import com.example.aio_project.model.DataRepository;
import com.example.aio_project.model.ModelDTO;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.widget.PopupWindowCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

/**
 * Created by Alexey Matrosov on 27.10.2020.
 */

public class TabsFragment extends Fragment implements IMainFragment {
    private View view;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabsAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create list of tabs
        List<TabInfo> tabs = new ArrayList<>();
        tabs.add(new TabInfo(getString(R.string.tab_mods), R.drawable.icon_mods, R.drawable.icon_mods_true, Category.MOD, "mod_mods"));
        tabs.add(new TabInfo(getString(R.string.tab_textures), R.drawable.icon_textures, R.drawable.icon_textures_true, Category.TEXTURE, "mod_textures"));
        tabs.add(new TabInfo(getString(R.string.tab_maps), R.drawable.icon_maps,  R.drawable.icon_maps_true, Category.MAP, "mod_maps"));
        tabs.add(new TabInfo(getString(R.string.tab_seeds), R.drawable.icon_seeds,  R.drawable.icon_seeds_true, Category.SEED, "mod_seeds"));
        tabs.add(new TabInfo(getString(R.string.tab_skins), R.drawable.icon_skins, R.drawable.icon_skins_true, Category.SKIN, "mod_skins"));

        // Create tabs adapter
        adapter = new TabsAdapter(requireContext(), getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, tabs, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tabs, container, false);
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);
        View vipIcon = view.findViewById(R.id.vipIcon);
        View sortByButton = view.findViewById(R.id.sortButtonContainer);
        
        initSearchView();

        // Connect tabLayout and viewPager
        viewPager.setOffscreenPageLimit(adapter.getCount());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        updateTabsState(0);

        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                updateTabsState(position);
            }
        });

        vipIcon.setOnClickListener(v -> goToVip());
        sortByButton.setOnClickListener(sortByClickListener);

        return view;
    }

    // region IMainFragment

    @Override
    public void goToDetails(ModelDTO item) {
        DataRepository.updateViewsCount(item);

        DetailsFragmentArgs args = new DetailsFragmentArgs.Builder(item.getId())
                .build();

        Navigation.findNavController(view)
                .navigate(R.id.action_tabs_to_details, args.toBundle());
    }

    @Override
    public void goToVip() {
        Navigation.findNavController(view)
                .navigate(R.id.action_tabs_to_vip);
    }

    // endregion

    private void initSearchView() {
        SearchView searchView = view.findViewById(R.id.searchView);

        EditText searchEdit = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        searchEdit.setTextColor(Color.parseColor("#ffffff"));
        searchEdit.setTypeface(Typeface.createFromAsset(requireContext().getAssets(), "fonts/Roboto-Regular.ttf"));

        ImageView searchButton = searchView.findViewById(androidx.appcompat.R.id.search_mag_icon);
        searchButton.getLayoutParams().width = (int) requireContext().getResources().getDimension(R.dimen.search_button_width);
        searchButton.requestLayout();

        ImageView closeButton = searchView.findViewById(androidx.appcompat.R.id.search_close_btn);
        closeButton.setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_ATOP);
        closeButton.setPadding(0, 10, 0, 10);
        closeButton.requestLayout();
    }

    private void updateTabsState(int selectedPosition) {
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            assert tab != null;
            tab.setCustomView(null);
            tab.setCustomView(adapter.createTabView(i, i == selectedPosition));
        }
    }

    private void updateSortType(TabContentFragment.SortType type) {
        TabContentFragment fragment = (TabContentFragment) adapter.getItem(viewPager.getCurrentItem());
        fragment.changeSortType(type);
    }

    private final View.OnClickListener sortByClickListener = v -> {
        View chooserView = LayoutInflater.from(getContext()).inflate(R.layout.popup_sort_by, null);
        PopupWindow pw = new PopupWindow(chooserView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        View selectionNewest = chooserView.findViewById(R.id.selection_1);
        View selectionDownloads = chooserView.findViewById(R.id.selection_2);
        View selectionViews = chooserView.findViewById(R.id.selection_3);

        selectionNewest.setOnClickListener(view1 -> { updateSortType(TabContentFragment.SortType.Newest); pw.dismiss(); });
        selectionDownloads.setOnClickListener(view1 -> { updateSortType(TabContentFragment.SortType.Downloads); pw.dismiss(); });
        selectionViews.setOnClickListener(view1 -> { updateSortType(TabContentFragment.SortType.Views); pw.dismiss(); });

        int offsetX = (int) getResources().getDimension(R.dimen.popup_sort_offset_x);
        int offsetY = (int) getResources().getDimension(R.dimen.popup_sort_offset_y);

        pw.setOutsideTouchable(true);
        PopupWindowCompat.setOverlapAnchor(pw, true);
        PopupWindowCompat.showAsDropDown(pw, v, -offsetX, offsetY, Gravity.NO_GRAVITY);
    };
}
