package com.example.aio_project.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import androidx.annotation.NonNull;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * Created by Alexey Matrosov on 03.11.2020.
 */

public class ClipboardHelper {
    public static void copy(@NonNull Context context, String value) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
        if (clipboard == null || value == null)
            return;

        ClipData clip = ClipData.newPlainText("", value);
        clipboard.setPrimaryClip(clip);
    }
}
