package com.example.aio_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.aio_project.adapter.AioCategoryAdapter;
import com.example.aio_project.adapter.SampleFragmentPagerAdapter;
import com.example.aio_project.fragment.AioDetailsFragment;
import com.example.aio_project.fragment.BaseFragment;
import com.example.aio_project.model.AioRepository;
import com.example.aio_project.model.ModelDTO;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private int[] tabIcons = {
            R.drawable.icon_mods,
            R.drawable.icon_textures,
            R.drawable.icon_maps,
            R.drawable.icon_seeds,
            R.drawable.icon_skins
    };
    private TabLayout tabLayout;
    private SampleFragmentPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView vip = findViewById(R.id.vip);
        vip.setOnClickListener(view -> getVip());
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        ViewPager viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tabLayout);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        highLightCurrentTab(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                highLightCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void highLightCurrentTab(int position) {
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            assert tab != null;
            tab.setCustomView(null);
            tab.setCustomView(adapter.getTabView(i));
        }
        TabLayout.Tab tab = tabLayout.getTabAt(position);
        assert tab != null;
        tab.setCustomView(null);
        tab.setCustomView(adapter.getSelectedTabView(position));
    }


    private void setupViewPager(ViewPager viewPager) {
        adapter = new SampleFragmentPagerAdapter(getSupportFragmentManager(), 1, this);
        adapter.addFragment(new BaseFragment(), "Mods", tabIcons[0]);
        adapter.addFragment(new BaseFragment(), "Textures",tabIcons[1]);
        adapter.addFragment(new BaseFragment(), "Maps", tabIcons[2]);
        adapter.addFragment(new BaseFragment(), "Seeds", tabIcons[3]);
        adapter.addFragment(new BaseFragment(), "Skins", tabIcons[4]);
        viewPager.setAdapter(adapter);
    }

    private void getVip() {
        Intent intent = new Intent(this, PremiumActivity.class);
        startActivity(intent);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
    }
}