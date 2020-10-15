package com.example.aio_project.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
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
import com.example.aio_project.model.AioModel;
import com.example.aio_project.model.AioRepository;
import com.example.aio_project.model.Filter;
import com.example.aio_project.model.ModelDTO;
import com.example.aio_project.model.ModsDTO;
import com.example.aio_project.utils.DownloadHelper;

import java.util.ArrayList;
import java.util.List;

public class ExploreFragment extends Fragment {
    public static final String FILTER = "filter";

    private View view;
    private SwipeRefreshLayout refreshLayout;
    private List<ModelDTO> modsList = new ArrayList<>();
    private List<ModelDTO> currentList = new ArrayList<>();
    private Filter mode;
    private AioCategoryAdapter adapter;
    private RecyclerView recyclerView;

    private TextView filterTitle;
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
        view = inflater.inflate(R.layout.fragment_explore, container, false);
/*        if (getArguments() != null) {
            mode = (Filter) getArguments().getSerializable(FILTER);
        }*/

        MainActivity activity = (MainActivity) requireActivity();
        ImageView backButton = view.findViewById(R.id.back_icon);
        backButton.setOnClickListener(view1 -> activity.onBackPressed());

        refreshLayout = view.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(this::refreshItems);

        filterTitle = view.findViewById(R.id.title);
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
        modsView.setOnClickListener(view1 -> onResume());
        View textureView = view.findViewById(R.id.textures);
        textureView.setOnClickListener(view1 -> onResume());
        View mapsView = view.findViewById(R.id.maps);
        mapsView.setOnClickListener(view1 -> onResume());
        View seedsView = view.findViewById(R.id.seeds);
        seedsView.setOnClickListener(view1 -> onResume());
        View skinsView = view.findViewById(R.id.skins);
        skinsView.setOnClickListener(view1 -> onResume());

        ImageView vip = view.findViewById(R.id.vip);
        vip.setOnClickListener(view1 -> getVip());
        modsList = DownloadHelper.getItems();
        adapter = new AioCategoryAdapter(modsList, listener);

        if (DownloadHelper.getItems().isEmpty())
            refreshItems();
        else
            setCurrent(modsList);

        recyclerView = view.findViewById(R.id.filter_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        return view;
    }

    private void refreshItems() {
        refreshLayout.setRefreshing(true);
        DownloadHelper.loadModsData(() -> {
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

   /* private void getCurrentList() {
        switch (mode){
            case MODS:
                filterList = AioRepository.getMods();
                adapter = new AioCategoryAdapter(filterList, listener);
                recyclerView.setAdapter(adapter);
                filterTitle.setText(R.string.mods_title);
                modsText.setTextColor(ContextCompat.getColor(getContext(), R.color.toolbar_text));
                modsImage.setImageResource(R.drawable.icon_mods_true);
                break;
            case TEXTURES:
                filterList = AioRepository.getTextures();
                adapter = new AioCategoryAdapter(filterList, listener);
                recyclerView.setAdapter(adapter);
                filterTitle.setText(R.string.tab_textures);
                texturesImage.setImageResource(R.drawable.icon_textures_true);
                texturesText.setTextColor(ContextCompat.getColor(getContext(),R.color.toolbar_text));
                break;
            case MAPS:
                filterList = AioRepository.getMaps();
                adapter = new AioCategoryAdapter(filterList, listener);
                recyclerView.setAdapter(adapter);
                filterTitle.setText(R.string.tab_maps);
                mapsText.setTextColor(ContextCompat.getColor(getContext(), R.color.toolbar_text));
                mapsImage.setImageResource(R.drawable.icon_maps_true);
                break;
            case SEEDS:
                filterList = AioRepository.getSeeds();
                adapter = new AioCategoryAdapter(filterList, listener);
                recyclerView.setAdapter(adapter);
                filterTitle.setText(R.string.tab_seeds);
                seedsText.setTextColor(ContextCompat.getColor(getContext(), R.color.toolbar_text));
                seedsImage.setImageResource(R.drawable.icon_seeds_true);
                break;
            case SKINS:
                filterList = AioRepository.getSkins();
                adapter = new AioCategoryAdapter(filterList, listener);
                recyclerView.setAdapter(adapter);
                filterTitle.setText(R.string.tab_skins);
                skinsText.setTextColor(ContextCompat.getColor(getContext(), R.color.toolbar_text));
                skinsImage.setImageResource(R.drawable.icon_skins_true);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + mode);
        }
    }*/

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
        //getCurrentList();
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
