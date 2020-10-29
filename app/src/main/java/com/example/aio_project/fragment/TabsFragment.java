package com.example.aio_project.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aio_project.R;
import com.example.aio_project.TabInfo;
import com.example.aio_project.adapter.TabsAdapter;
import com.example.aio_project.model.Category;
import com.example.aio_project.model.ModelDTO;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tabs, container, false);
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);
        View vipIcon = view.findViewById(R.id.vipIcon);

        // Create list of tabs
        List<TabInfo> tabs = new ArrayList<>();
        tabs.add(new TabInfo(getString(R.string.tab_mods), R.drawable.icon_mods, R.drawable.icon_mods_true, Category.MOD, "mod_mods"));
        tabs.add(new TabInfo(getString(R.string.tab_textures), R.drawable.icon_textures, R.drawable.icon_textures_true, Category.TEXTURE, "mod_textures"));
        tabs.add(new TabInfo(getString(R.string.tab_maps), R.drawable.icon_maps,  R.drawable.icon_maps_true, Category.MAP, "mod_maps"));
        tabs.add(new TabInfo(getString(R.string.tab_seeds), R.drawable.icon_seeds,  R.drawable.icon_seeds_true, Category.SEED, "mod_seeds"));
        tabs.add(new TabInfo(getString(R.string.tab_skins), R.drawable.icon_skins, R.drawable.icon_skins_true, Category.SKIN, "mod_skins"));

        // Create tabs adapter
        adapter = new TabsAdapter(requireContext(), getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, tabs, this);

        // Connect tabLayout and viewPager
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

        return view;
    }

    // region IMainFragment

    @Override
    public void goToDetails(ModelDTO item) {
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

    private void updateTabsState(int selectedPosition) {
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            assert tab != null;
            tab.setCustomView(null);
            tab.setCustomView(adapter.createTabView(i, i == selectedPosition));
        }
    }
}
