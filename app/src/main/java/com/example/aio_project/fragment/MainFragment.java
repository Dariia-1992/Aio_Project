package com.example.aio_project.fragment;

import android.content.Intent;
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

import com.example.aio_project.PremiumActivity;
import com.example.aio_project.R;
import com.example.aio_project.adapter.AioAdapter;
import com.example.aio_project.model.AioModel;
import com.example.aio_project.model.AioRepository;
import com.example.aio_project.model.Filter;

import java.util.ArrayList;
import java.util.List;

import static com.example.aio_project.model.Filter.MODS;


public class MainFragment extends Fragment {

/*    public int MODE_MODS = 0;
    public int MODE_TEXTURE = 1;
    public int MODE_MAPS = 2;
    public int MODE_SEEDS = 3;
    public int MODE_SKINS = 4;*/

    private View view;


    //private ImageView mapsImage;
   // private TextView mapsText;
    //private ImageView seedsImage;
    //private TextView seedsText;
    //private ImageView skinsImage;
    //private TextView skinsText;

    /*interface OnFragmentInteractionListener {

        void onFragmentInteraction(List<AioModel> currentList);
    }*/

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        ImageView vip = view.findViewById(R.id.vip);
        vip.setOnClickListener(view1 -> getVip());

        List<AioModel> latestList = AioRepository.getLatest();
        AioAdapter adapter = new AioAdapter(latestList, listener);
        RecyclerView recyclerViewLatest = view.findViewById(R.id.latest_list);
        recyclerViewLatest.setAdapter(adapter);
        recyclerViewLatest.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        List<AioModel> popularModsList = AioRepository.getPopularAddons();
        adapter = new AioAdapter(popularModsList, listener);
        RecyclerView recyclerViewMods = view.findViewById(R.id.popular_mods_list);
        recyclerViewMods.setAdapter(adapter);
        recyclerViewMods.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        List<AioModel> popularMapsList = AioRepository.getPopularMaps();
        adapter = new AioAdapter(popularMapsList, listener);
        RecyclerView recyclerViewMaps = view.findViewById(R.id.popular_maps_list);
        recyclerViewMaps.setAdapter(adapter);
        recyclerViewMaps.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        //modsImage = view.findViewById(R.id.mods_image);
        //modsText = view.findViewById(R.id.mods_text);
        //texturesImage = view.findViewById(R.id.textures_image);
        //texturesText = view.findViewById(R.id.textures_text);
        //mapsImage = view.findViewById(R.id.maps_image);
        //mapsText = view.findViewById(R.id.maps_text);
        //seedsImage = view.findViewById(R.id.seeds_image);
        //seedsText = view.findViewById(R.id.seeds_text);
       // skinsImage = view.findViewById(R.id.skins_image);
        //skinsText = view.findViewById(R.id.skins_text);

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


    private final AioAdapter.OnClickItem listener = id -> {
      View view1 = getView();
      if (view1 == null)
          return;
      Bundle bundle = new Bundle();
      bundle.putString(AioDetailsFragment.ARG_ITEM_ID, id);
      Navigation.findNavController(getView()).navigate(R.id.action_mainFragment_to_aioDetailsFragment, bundle);
    };


    private void modsFilter(boolean isChecked) {
/*    ImageView modsImage = view.findViewById(R.id.mods_image);
    TextView modsText = view.findViewById(R.id.mods_text);

    if (getContext() == null)
        return;
    modsText.setTextColor(ContextCompat.getColor(getContext(),isChecked ? R.color.toolbar_text : R.color.white));
    modsImage.setImageResource(isChecked ?  R.drawable.icon_mods_true : R.drawable.icon_mods);*/
       /* ExploreFragment fragment = new ExploreFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(ExploreFragment.MODE, currentListMods);
        fragment.setArguments(bundle);*/

        Bundle bundle = new Bundle();
        bundle.putSerializable(ExploreFragment.FILTER, MODS);
        Navigation.findNavController(getView()).navigate(R.id.action_mainFragment_to_exploreFragment, bundle);
    }

    private void textureFilters(boolean isChecked) {
        ImageView texturesImage = view.findViewById(R.id.textures_image);
        TextView texturesText = view.findViewById(R.id.textures_text);
        if (getContext() == null)
            return;
        texturesImage.setImageResource(isChecked ? R.drawable.icon_textures_true : R.drawable.icon_textures);
        texturesText.setTextColor(ContextCompat.getColor(getContext(),isChecked ? R.color.toolbar_text : R.color.white));

        //fragmentListener.onFragmentInteraction(currentListTextures);
        Navigation.findNavController(getView()).navigate(R.id.action_mainFragment_to_exploreFragment);
    }

    private void mapsFilter(boolean isChecked) {
        ImageView mapsImage = view.findViewById(R.id.maps_image);
        TextView mapsText = view.findViewById(R.id.maps_text);
        if (getContext() == null)
            return;
        mapsText.setTextColor(ContextCompat.getColor(getContext(),isChecked ? R.color.toolbar_text : R.color.white));
        mapsImage.setImageResource(isChecked ? R.drawable.icon_maps_true : R.drawable.icon_maps);

        //fragmentListener.onFragmentInteraction(currentListMaps);
        Navigation.findNavController(getView()).navigate(R.id.action_mainFragment_to_exploreFragment);
    }

    private void seedsFilter(boolean isChecked) {
        ImageView seedsImage = view.findViewById(R.id.seeds_image);
        TextView seedsText = view.findViewById(R.id.seeds_text);
        if (getContext() == null)
            return;
        seedsText.setTextColor(ContextCompat.getColor(getContext(),isChecked ? R.color.toolbar_text : R.color.white));
        seedsImage.setImageResource(isChecked ? R.drawable.icon_seeds_true : R.drawable.icon_seeds);

        //fragmentListener.onFragmentInteraction(currentListSeeds);
        Navigation.findNavController(getView()).navigate(R.id.action_mainFragment_to_exploreFragment);
    }

    private void skinsFilter(boolean isChecked) {
        ImageView skinsImage = view.findViewById(R.id.skins_image);
        TextView skinsText = view.findViewById(R.id.skins_text);
        if (getContext() == null)
            return;
        skinsText.setTextColor(ContextCompat.getColor(getContext(),isChecked ? R.color.toolbar_text : R.color.white));
        skinsImage.setImageResource(isChecked ? R.drawable.icon_skins_true : R.drawable.icon_skins);

        //fragmentListener.onFragmentInteraction(currentListSkins);
        Navigation.findNavController(getView()).navigate(R.id.action_mainFragment_to_exploreFragment);
    }
}
