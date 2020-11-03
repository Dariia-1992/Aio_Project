package com.example.aio_project.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.aio_project.model.ModelDTO;

/**
 * Created by Alexey Matrosov on 03.11.2020.
 */

public class LocalStorage {
    private static final String PREFERENCES_FILE = "preferences_file";

    public static long getIdForModInfo(Context context, ModelDTO entry) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        return preferences.getLong(entry.getId(), 0);
    }

    public static void saveDownloadId(Context context, ModelDTO entry, long id) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putLong(entry.getId(), id);
        editor.apply();
    }
}
