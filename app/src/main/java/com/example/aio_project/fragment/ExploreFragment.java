package com.example.aio_project.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aio_project.PremiumActivity;
import com.example.aio_project.R;
import com.example.aio_project.adapter.AioModsAdapter;
import com.example.aio_project.model.AioModel;
import com.example.aio_project.model.AioRepository;
import com.example.aio_project.model.Filter;

import java.util.ArrayList;
import java.util.List;

public class ExploreFragment extends Fragment {
    public static final String FILTER = "filter";
    private View view;
    private List<AioModel> filterList = new ArrayList<>();
    private Filter mode;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_explore, container, false);

        if (getArguments() != null) {
            mode = (Filter) getArguments().getSerializable(FILTER);
        }

        ImageView vip = view.findViewById(R.id.vip);
        vip.setOnClickListener(view1 -> getVip());

        recyclerView = view.findViewById(R.id.filter_list);
        getCurrentList();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        return view;
    }

    private void getCurrentList() {
        switch (mode){
            case MODS:
                filterList = AioRepository.getMods();
                AioModsAdapter adapter = new AioModsAdapter(filterList, listener);
                recyclerView.setAdapter(adapter);
                break;
            case TEXTURES:
                break;
            case MAPS:
                break;
            case SEEDS:
                break;
            case SKINS:
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + mode);
        }
    }

    private void getVip() {
        Intent intent = new Intent(this.getActivity(), PremiumActivity.class);
        startActivity(intent);
    }

    private final AioModsAdapter.OnClickItem listener = id -> {
        View view1 = getView();
        if (view1 == null)
            return;
        Bundle bundle = new Bundle();
        bundle.putString(AioDetailsFragment.ARG_ITEM_ID, id);
        Navigation.findNavController(getView()).navigate(R.id.action_exploreFragment_to_aioDetailsFragment, bundle);
    };
}
