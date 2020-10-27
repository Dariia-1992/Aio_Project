package com.example.aio_project;

import com.example.aio_project.model.Category;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

/**
 * Created by Alexey Matrosov on 27.10.2020.
 */

public class TabInfo {
    public final @NonNull String name;
    public final @DrawableRes int iconId;
    public final @DrawableRes int selectedIconId;
    public final Category category;
    public final @NonNull String serverName;

    public TabInfo(@NonNull String name, @DrawableRes int iconId, @DrawableRes int selectedIconId, Category category, @NonNull String serverName) {
        this.name = name;
        this.iconId = iconId;
        this.selectedIconId = selectedIconId;
        this.category = category;
        this.serverName = serverName;
    }
}
