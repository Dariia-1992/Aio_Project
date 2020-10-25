package com.example.aio_project.adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


import com.example.aio_project.R;

import java.util.ArrayList;
import java.util.List;

public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> tabTitle = new ArrayList<>();
    private List<Integer> tabIconList = new ArrayList<>();
    private Context context;


    public SampleFragmentPagerAdapter(@NonNull FragmentManager fm, int behavior, Context context) {
        super(fm, behavior);
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitle.get(position);
        //return null;
    }

    public void addFragment(Fragment fragment, String title, int tabIcon) {
        fragmentList.add(fragment);
        tabTitle.add(title);
        tabIconList.add(tabIcon);
    }

    public View getTabView(int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_tab, null);
        TextView tabTextView = view.findViewById(R.id.tabTextView);
        tabTextView.setText(tabTitle.get(position));
        tabTextView.setTextColor(ContextCompat.getColor(context, R.color.white));
        ImageView tabImageView = view.findViewById(R.id.tabImageView);
        tabImageView.setImageResource(tabIconList.get(position));
        return view;
    }

    public View getSelectedTabView(int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_tab, null);
        TextView tabTextView = view.findViewById(R.id.tabTextView);
        tabTextView.setText(tabTitle.get(position));
        tabTextView.setTextColor(ContextCompat.getColor(context, R.color.toolbar_text));
        ImageView tabImageView = view.findViewById(R.id.tabImageView);
        tabImageView.setImageResource(tabIconList.get(position));
        tabImageView.setColorFilter(ContextCompat.getColor(context, R.color.toolbar_text), PorterDuff.Mode.SRC_ATOP);
        return view;
    }

}
