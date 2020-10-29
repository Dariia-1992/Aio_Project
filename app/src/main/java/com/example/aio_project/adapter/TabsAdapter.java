package com.example.aio_project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aio_project.R;
import com.example.aio_project.TabInfo;
import com.example.aio_project.fragment.IMainFragment;
import com.example.aio_project.fragment.TabContentFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * Created by Alexey Matrosov on 27.10.2020.
 */

public class TabsAdapter extends FragmentPagerAdapter {
    private final Context context;
    private final List<TabInfo> tabInfoList;
    private final List<TabContentFragment> tabFragmentList;

    public TabsAdapter(@NonNull Context context, @NonNull FragmentManager fm, int behavior, List<TabInfo> tabInfoList, IMainFragment mainFragment) {
        super(fm, behavior);
        this.context = context;
        this.tabInfoList = tabInfoList;

        tabFragmentList = new ArrayList<>();
        for (TabInfo info : tabInfoList) {
            TabContentFragment fragment = TabContentFragment.createFragment(info.category, info.serverName, mainFragment);
            tabFragmentList.add(fragment);
        }
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return tabFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return tabFragmentList.size();
    }

    public View createTabView(int position, boolean isSelected) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_tab, null);
        TextView tabTextView = view.findViewById(R.id.tabTextView);
        ImageView tabImageView = view.findViewById(R.id.tabImageView);

        TabInfo info = tabInfoList.get(position);
        tabTextView.setText(info.name);
        tabTextView.setTextColor(ContextCompat.getColor(context, isSelected ? R.color.tab_selected_color : R.color.white));
        tabImageView.setImageResource(isSelected ? info.selectedIconId : info.iconId);

        return view;
    }
}
