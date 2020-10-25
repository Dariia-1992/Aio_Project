package com.example.aio_project.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.example.aio_project.model.ImageCollectionList;
import com.example.aio_project.model.ModelDTO;

import java.util.ArrayList;
import java.util.List;

public class ExploreFragment extends Fragment {

    private View view;
    private SwipeRefreshLayout refreshLayout;

    private List<ImageCollectionList> imageList = new ArrayList<>();
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
        view = inflater.inflate(R.layout.fragment_base, container, false);

        MainActivity activity = (MainActivity) requireActivity();
        //ImageView backButton = view.findViewById(R.id.back_icon);
        //backButton.setOnClickListener(view1 -> activity.onBackPressed());

        refreshLayout = view.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(this::getMods);

        recyclerView = view.findViewById(R.id.filter_list);
        modsList = AioRepository.getMods();
        adapter = new AioCategoryAdapter(modsList, listener);

        if (AioRepository.getMods().isEmpty())
            getMods();
        else
            setCurrent(modsList);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        return view;
    }

    private void getImage() {
        AioRepository.loadImageCollection(() -> {
            List<ImageCollectionList> images = new ArrayList<>();
            images.addAll(imageList);
        }, error -> {
            Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
        });
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

    private void getTextures() {
        AioRepository.loadTexturesData(() -> {
            //Log.e("TAG", "loadData: FFFFFFFFFFFFFFFFFF");
            setCurrent(texturesList);
        }, error -> {
            Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
        });
    }

    private void getMods() {
        refreshLayout.setRefreshing(true);
        AioRepository.loadModsData(() -> {
            refreshLayout.setRefreshing(false);
            Log.e("TAG", "loadData: FFFFFFFFFFFFFFFFFF");
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

    private final AioCategoryAdapter.OnClickItem listener = id -> {
        View view1 = getView();
        if (view1 == null)
            return;
        Bundle bundle = new Bundle();
        bundle.putString(AioDetailsFragment.ARG_ITEM_ID, id);
        Navigation.findNavController(getView()).navigate(R.id.action_exploreFragment_to_aioDetailsFragment, bundle);
    };
}
