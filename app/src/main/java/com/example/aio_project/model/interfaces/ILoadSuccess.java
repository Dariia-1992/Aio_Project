package com.example.aio_project.model.interfaces;

import java.util.List;

/**
 * Created by Alexey Matrosov on 29.10.2020.
 */

public interface ILoadSuccess<T> {
    void onSuccess(List<T> items);
}
