package com.example.aio_project.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aio_project.R;
import com.example.aio_project.adapter.AioAdapter;
import com.example.aio_project.model.AioModel;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {

    private View view;
    private List<AioModel> latestList;
    private List <AioModel> popularModsList;
    private List<AioModel> popularMapsList;
    private AioAdapter adapter;

    private ImageView modsImage;
    private TextView modsText;
    private ImageView texturesImage;
    private TextView texturesText;
    private ImageView mapsImage;
    private TextView mapsText;
    private ImageView seedsImage;
    private TextView seedsText;
    private ImageView skinsImage;
    private TextView skinsText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);

        createAioLatestList();
        createAioPopularList();
        createAioPopularMapsList();

        adapter = new AioAdapter(latestList, listener);
        RecyclerView recyclerViewLatest = view.findViewById(R.id.latest_list);
        recyclerViewLatest.setAdapter(adapter);
        recyclerViewLatest.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        adapter = new AioAdapter(popularModsList, listener);
        RecyclerView recyclerViewMods = view.findViewById(R.id.popular_mods_list);
        recyclerViewMods.setAdapter(adapter);
        recyclerViewMods.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        adapter = new AioAdapter(popularMapsList, listener);
        RecyclerView recyclerViewMaps = view.findViewById(R.id.popular_maps_list);
        recyclerViewMaps.setAdapter(adapter);
        recyclerViewMaps.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        modsImage = view.findViewById(R.id.mods_image);
        modsText = view.findViewById(R.id.mods_text);
        texturesImage = view.findViewById(R.id.textures_image);
        texturesText = view.findViewById(R.id.textures_text);
        mapsImage = view.findViewById(R.id.maps_image);
        mapsText = view.findViewById(R.id.maps_text);
        seedsImage = view.findViewById(R.id.seeds_image);
        seedsText = view.findViewById(R.id.seeds_text);
        skinsImage = view.findViewById(R.id.skins_image);
        skinsText = view.findViewById(R.id.skins_text);

        View modsView= view.findViewById(R.id.mods);
        modsView.setOnClickListener(view1 -> modsFilter(true));
        View textureView = view.findViewById(R.id.textures);
        textureView.setOnClickListener(view1 -> textureFilters(true));
        View mapsView = view.findViewById(R.id.maps);
        mapsView.setOnClickListener(view1 -> mapsFilter(true));
        View seedsView = view.findViewById(R.id.seeds);
        seedsView.setOnClickListener(view1 -> seedsFilter(true));
        View skinsView = view.findViewById(R.id.skins);
        skinsView.setOnClickListener(view1 -> skinsFilter(true));

        return view;
    }


    public void createAioLatestList(){
        latestList = new ArrayList<>();
        latestList.add(new AioModel("Lorem ipsum dolor", R.drawable.pic_null));
        latestList.add(new AioModel("Lorem ipsum dolor", R.drawable.pic_null));
        latestList.add(new AioModel("Lorem ipsum dolor", R.drawable.pic_null));
        latestList.add(new AioModel("Lorem ipsum dolor", R.drawable.pic_null));
        latestList.add(new AioModel("Lorem ipsum dolor", R.drawable.pic_null));
        latestList.add(new AioModel("Lorem ipsum dolor", R.drawable.pic_null));
    }

    public void createAioPopularList() {
        popularModsList = new ArrayList<>();
        popularModsList.add(new AioModel("Lorem ipsum dolor", R.drawable.pic_null));
        popularModsList.add(new AioModel("Lorem ipsum dolor", R.drawable.pic_null));
        popularModsList.add(new AioModel("Lorem ipsum dolor", R.drawable.pic_null));
        popularModsList.add(new AioModel("Lorem ipsum dolor", R.drawable.pic_null));
        popularModsList.add(new AioModel("Lorem ipsum dolor", R.drawable.pic_null));
    }

    public void createAioPopularMapsList() {
        popularMapsList = new ArrayList<>();
        popularMapsList.add(new AioModel("Lorem ipsum dolor", R.drawable.pic_null));
        popularMapsList.add(new AioModel("Lorem ipsum dolor", R.drawable.pic_null));
        popularMapsList.add(new AioModel("Lorem ipsum dolor", R.drawable.pic_null));
        popularMapsList.add(new AioModel("Lorem ipsum dolor", R.drawable.pic_null));
        popularMapsList.add(new AioModel("Lorem ipsum dolor", R.drawable.pic_null));
        popularMapsList.add(new AioModel("Lorem ipsum dolor", R.drawable.pic_null));
    }

    private final AioAdapter.OnClickItem listener = id -> {
      View view1 = getView();
      if (view1 == null)
          return;
      Bundle bundle = new Bundle();
      bundle.putString(AioDetailsFragment.ARG_ITEM_ID, id);
        Navigation.findNavController(getView()).navigate(R.id.action_mainFragment_to_aioDetailsFragment, bundle);
    };

    private void modsFilter(boolean isChecked) {
        if (getContext() == null)
            return;
        modsText.setTextColor(ContextCompat.getColor(getContext(),isChecked ? R.color.toolbar_text : R.color.white));
        modsImage.setImageResource(isChecked ?  R.drawable.icon_mods_true : R.drawable.icon_mods);
    }

    private void textureFilters(boolean isChecked) {
        if (getContext() == null)
            return;
        texturesImage.setImageResource(isChecked ? R.drawable.icon_textures_true : R.drawable.icon_textures);
        texturesText.setTextColor(ContextCompat.getColor(getContext(),isChecked ? R.color.toolbar_text : R.color.white));
    }

    private void mapsFilter(boolean isChecked) {
        if (getContext() == null)
            return;
        mapsText.setTextColor(ContextCompat.getColor(getContext(),isChecked ? R.color.toolbar_text : R.color.white));
        mapsImage.setImageResource(isChecked ? R.drawable.icon_maps_true : R.drawable.icon_maps);
    }

    private void seedsFilter(boolean isChecked) {
        if (getContext() == null)
            return;
        seedsText.setTextColor(ContextCompat.getColor(getContext(),isChecked ? R.color.toolbar_text : R.color.white));
        seedsImage.setImageResource(isChecked ? R.drawable.icon_seeds_true : R.drawable.icon_seeds);
    }

    private void skinsFilter(boolean isChecked) {
        if (getContext() == null)
            return;
        skinsText.setTextColor(ContextCompat.getColor(getContext(),isChecked ? R.color.toolbar_text : R.color.white));
        skinsImage.setImageResource(isChecked ? R.drawable.icon_skins_true : R.drawable.icon_skins);
    }
}
