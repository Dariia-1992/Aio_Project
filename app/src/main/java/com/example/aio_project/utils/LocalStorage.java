package com.example.aio_project.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.aio_project.fragment.TabContentFragment;
import com.example.aio_project.model.ModelDTO;

/**
 * Created by Alexey Matrosov on 03.11.2020.
 */

public class LocalStorage {
    private static final String PREFERENCES_FILE = "preferences_file";
    private static final String KEY_DONT_SHOW_AGAIN = "dont_show_again";

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

    public static void setNeverShowRateDialogAgain(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putBoolean(KEY_DONT_SHOW_AGAIN, true);
        editor.apply();
    }

    public static boolean isShowRateDialogAgain(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        return !preferences.getBoolean(KEY_DONT_SHOW_AGAIN, false);
    }

    public static void saveSortForCategory(Context context, String category, TabContentFragment.SortType type) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putInt(category, type.ordinal());
        editor.apply();
    }

    public static TabContentFragment.SortType readSortForCategory(Context context, String category) {
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);

        int index = preferences.getInt(category, TabContentFragment.SortType.Newest.ordinal());
        if (index >= TabContentFragment.SortType.values().length || index < 0)
            index = TabContentFragment.SortType.Newest.ordinal();

        return TabContentFragment.SortType.values()[index];
    }
}
