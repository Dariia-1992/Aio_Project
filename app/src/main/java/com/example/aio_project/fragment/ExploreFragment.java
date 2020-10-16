package com.example.aio_project.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.aio_project.MainActivity;
import com.example.aio_project.PremiumActivity;
import com.example.aio_project.R;
import com.example.aio_project.adapter.AioCategoryAdapter;
import com.example.aio_project.model.AioRepository;
import com.example.aio_project.model.Filter;
import com.example.aio_project.model.ModelDTO;
import com.example.aio_project.utils.DownloadHelper;

import java.util.ArrayList;
import java.util.List;

public class ExploreFragment extends Fragment {

    private View view;
    private SwipeRefreshLayout refreshLayout;
    private List<ModelDTO> modsList = new ArrayList<>();
    private List<ModelDTO> texturesList = new ArrayList<>();
    private List<ModelDTO> mapsList = new ArrayList<>();
    private List<ModelDTO> seedsList = new ArrayList<>();
    private List<ModelDTO> skinsList = new ArrayList<>();

    private List<ModelDTO> currentList = new ArrayList<>();

    private AioCategoryAdapter adapter;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_explore, container, false);

        MainActivity activity = (MainActivity) requireActivity();
        ImageView backButton = view.findViewById(R.id.back_icon);
        backButton.setOnClickListener(view1 -> activity.onBackPressed());

        refreshLayout = view.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(this::refreshItems);

        View modsView= view.findViewById(R.id.mods);
        modsView.setOnClickListener(view1 -> getCurrentList(Filter.MODS));
        View textureView = view.findViewById(R.id.textures);
        textureView.setOnClickListener(view1 -> getCurrentList(Filter.TEXTURES));
        View mapsView = view.findViewById(R.id.maps);
        mapsView.setOnClickListener(view1 -> getCurrentList(Filter.MAPS));
        View seedsView = view.findViewById(R.id.seeds);
        seedsView.setOnClickListener(view1 -> getCurrentList(Filter.SEEDS));
        View skinsView = view.findViewById(R.id.skins);
        skinsView.setOnClickListener(view1 ->getCurrentList(Filter.SKINS));

        ImageView vip = view.findViewById(R.id.vip);
        vip.setOnClickListener(view1 -> getVip());

        recyclerView = view.findViewById(R.id.filter_list);
        modsList = AioRepository.getItems();
        adapter = new AioCategoryAdapter(modsList, listener);

        if (AioRepository.getItems().isEmpty())
            refreshItems();
        else
            setCurrent(modsList);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        return view;
    }

    private void getSkinsList() {
        refreshLayout.setRefreshing(true);
        AioRepository.loadSkinsData(() -> {
            refreshLayout.setRefreshing(false);
            setCurrent(skinsList);
        }, error -> {
            refreshLayout.setRefreshing(false);
            Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
        });
    }

    private void refreshItems() {
        refreshLayout.setRefreshing(true);
        AioRepository.loadModsData(() -> {
            refreshLayout.setRefreshing(false);
            setCurrent(modsList);
        }, error -> {
            refreshLayout.setRefreshing(false);
            Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
        });
    }

    private void setCurrent(List<ModelDTO> skins) {
        currentList.clear();
        currentList.addAll(skins);
        adapter.notifyDataSetChanged();
    }


    private void getCurrentList(Filter mode) {

        TextView modsText = view.findViewById(R.id.mods_text);
        TextView texturesText = view.findViewById(R.id.textures_text);
        TextView mapsText = view.findViewById(R.id.maps_text);
        TextView seedsText = view.findViewById(R.id.seeds_text);
        TextView skinsText = view.findViewById(R.id.skins_text);

        ImageView modsImage = view.findViewById(R.id.mods_image);
        ImageView texturesImage = view.findViewById(R.id.textures_image);
        ImageView mapsImage = view.findViewById(R.id.maps_image);
        ImageView seedsImage = view.findViewById(R.id.seeds_image);
        ImageView skinsImage = view.findViewById(R.id.skins_image);

        switch (mode){
            case MODS:
                changeTextColor(true, modsText);
                modsImage.setImageResource(R.drawable.icon_mods_true);
                break;
            case TEXTURES:
                changeTextColor(true, texturesText);
                texturesImage.setImageResource(R.drawable.icon_textures_true);
                break;
            case MAPS:
                changeTextColor(true, mapsText);
                mapsImage.setImageResource(R.drawable.icon_maps_true);
                break;
            case SEEDS:
                changeTextColor(true, seedsText);
                seedsImage.setImageResource(R.drawable.icon_seeds_true);
                break;
            case SKINS:
                changeTextColor(true, skinsText);
                skinsImage.setImageResource(R.drawable.icon_skins_true);
                getSkinsList();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + mode);
        }
    }

    private void changeTextColor(boolean isChecked, TextView textView) {
        if (getContext() == null)
            return;
        textView.setTextColor(ContextCompat.getColor(getContext(), isChecked ? R.color.toolbar_text : R.color.white));
    }

    private void getVip() {
        Intent intent = new Intent(this.getActivity(), PremiumActivity.class);
        startActivity(intent);
    }

    private final AioCategoryAdapter.OnClickItem listener = id -> {
        View view1 = getView();
        if (view1 == null)
            return;
        Bundle bundle = new Bundle();
        bundle.putString(AioDetailsFragment.ARG_ITEM_ID, id);
        Navigation.findNavController(getView()).navigate(R.id.action_exploreFragment_to_aioDetailsFragment, bundle);
    };
}
