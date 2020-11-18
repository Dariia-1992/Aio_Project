package com.example.aio_project.fragment;

import com.example.aio_project.model.ModelDTO;

/**
 * Created by Alexey Matrosov on 29.10.2020.
 */

public interface IMainFragment {
    void goToDetails(ModelDTO item);
    void goToVip();
    String getSearchStr();
    void initAdBanner();
}
