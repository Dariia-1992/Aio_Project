package com.example.aio_project.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
}
