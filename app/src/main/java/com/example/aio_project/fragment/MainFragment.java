package com.example.aio_project.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.aio_project.PremiumActivity;
import com.example.aio_project.R;
import com.example.aio_project.adapter.AioAdapter;
import com.example.aio_project.model.AioModel;
import com.example.aio_project.model.AioRepository;
import com.example.aio_project.model.CategoryDTO;
import com.example.aio_project.model.MapsDTO;
import com.example.aio_project.model.ModsDTO;
import com.example.aio_project.utils.DownloadHelper;

import java.util.ArrayList;
import java.util.List;

import static com.example.aio_project.model.Filter.MAPS;
import static com.example.aio_project.model.Filter.MODS;
import static com.example.aio_project.model.Filter.SEEDS;
import static com.example.aio_project.model.Filter.SKINS;
import static com.example.aio_project.model.Filter.TEXTURES;


public class MainFragment extends Fragment {
    private View view;
    private SwipeRefreshLayout refreshLayout;
    private AioAdapter adapter;

    private List<AioModel> fulAioList = new ArrayList<>();
    private List<CategoryDTO> categoryList = new ArrayList<>();
    private List<MapsDTO> mapsList = new ArrayList<>();
    private List<ModsDTO> modsList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        ImageView vip = view.findViewById(R.id.vip);
        vip.setOnClickListener(view1 -> getVip());

        refreshLayout = view.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(this::refreshItems);

       // List<AioModel> latestList = AioRepository.getLatest();
        adapter = new AioAdapter(fulAioList, listener);
        RecyclerView recyclerViewLatest = view.findViewById(R.id.latest_list);
        recyclerViewLatest.setAdapter(adapter);
        recyclerViewLatest.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        /*List<AioModel> popularModsList = AioRepository.getPopularAddons();
        adapter = new AioAdapter(popularModsList, listener);
        RecyclerView recyclerViewMods = view.findViewById(R.id.popular_mods_list);
        recyclerViewMods.setAdapter(adapter);
        recyclerViewMods.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        List<AioModel> popularMapsList = AioRepository.getPopularMaps();
        adapter = new AioAdapter(popularMapsList, listener);
        RecyclerView recyclerViewMaps = view.findViewById(R.id.popular_maps_list);
        recyclerViewMaps.setAdapter(adapter);
        recyclerViewMaps.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));*/

        View modsView= view.findViewById(R.id.mods);
        modsView.setOnClickListener(view1 -> modsFilter());
        View textureView = view.findViewById(R.id.textures);
        textureView.setOnClickListener(view1 -> textureFilters());
        View mapsView = view.findViewById(R.id.maps);
        mapsView.setOnClickListener(view1 -> mapsFilter());
        View seedsView = view.findViewById(R.id.seeds);
        seedsView.setOnClickListener(view1 -> seedsFilter());
        View skinsView = view.findViewById(R.id.skins);
        skinsView.setOnClickListener(view1 -> skinsFilter());

/*        if (DownloadHelper.getCategoryList().isEmpty())
            refreshItems();
        else setCurrentCategory();*/

        setCurrentCategory();


        return view;
    }

/*    private void setCurrent(List<AioModel> skins) {
        currentList.clear();
        currentList.addAll(skins);
        RecyclerView recyclerViewLatest = view.findViewById(R.id.latest_list);
        recyclerViewLatest.setAdapter(adapter);
        recyclerViewLatest.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        //adapter.notifyDataSetChanged();
    }*/

    private void getVip() {
        Intent intent = new Intent(this.getActivity(), PremiumActivity.class);
        startActivity(intent);
    }

    private void refreshItems() {
        refreshLayout.setRefreshing(true);
        DownloadHelper.loadMapsData(() -> {
            refreshLayout.setRefreshing(false);
            //setCurrentCategory(fulAioList);
        }, error -> {
            refreshLayout.setRefreshing(false);
            Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
        });
    }

    private void setCurrentCategory() {
        categoryList.clear();
        DownloadHelper.loadCategory(()-> {

        }, error -> {
            Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
        });
        //adapter.notifyDataSetChanged();
    }

    private final AioAdapter.OnClickItem listener = id -> {
      View view1 = getView();
      if (view1 == null)
          return;
      Bundle bundle = new Bundle();
      bundle.putString(AioDetailsFragment.ARG_ITEM_ID, id);
      Navigation.findNavController(getView()).navigate(R.id.action_mainFragment_to_aioDetailsFragment, bundle);
    };

    private void modsFilter() {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ExploreFragment.FILTER, MODS);
        Navigation.findNavController(getView()).navigate(R.id.action_mainFragment_to_exploreFragment, bundle);
    }

    private void textureFilters() {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ExploreFragment.FILTER, TEXTURES);
        Navigation.findNavController(getView()).navigate(R.id.action_mainFragment_to_exploreFragment, bundle);
    }

    private void mapsFilter() {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ExploreFragment.FILTER, MAPS);
        Navigation.findNavController(getView()).navigate(R.id.action_mainFragment_to_exploreFragment, bundle);
    }

    private void seedsFilter() {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ExploreFragment.FILTER, SEEDS);
        Navigation.findNavController(getView()).navigate(R.id.action_mainFragment_to_exploreFragment, bundle);
    }

    private void skinsFilter() {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ExploreFragment.FILTER, SKINS);
        Navigation.findNavController(getView()).navigate(R.id.action_mainFragment_to_exploreFragment, bundle);
    }
}
