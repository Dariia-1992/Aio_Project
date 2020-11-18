package com.example.aio_project.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aio_project.MainActivity;
import com.example.aio_project.R;

import androidx.fragment.app.Fragment;

/**
 * Created by Alexey Matrosov on 27.10.2020.
 */

public class BuyVipFragment extends Fragment {
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_buy_vip, container, false);

        TextView priceView = view.findViewById(R.id.priceInfo);
        priceView.setText("USD XXX.00/Week");

        View buyVip = view.findViewById(R.id.vipButtonGet);
        buyVip.setOnClickListener(view -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).buyVip();
            }
        });

        return view;
    }
}
