package com.example.aio_project.utils;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Alexey Matrosov on 29.11.2020.
 */

public class SkinInstallHelper {
    private static final String SKIN_NAME = "custom";

    public static boolean installSkin(File src) {
        try {
            File dest = createFileInApplication();
            copyFile(src, dest);
            return true;
        } catch (Exception ex) {
            Log.e("SkinInstallHelper", "installSkin Error");
            return false;
        }
    }

    // region Helpers

    private static File createFileInApplication() {
        File folder = getPEFolder();
        return new File(String.format("%s/%s.png", folder.getAbsolutePath(), SKIN_NAME));
    }

    private static File getPEFolder() {
        File storageDir = new File(Environment.getExternalStorageDirectory(), "games/com.mojang/minecraftpe");
        if (!storageDir.exists())
            storageDir.mkdirs();

        return storageDir;
    }

    private static void copyFile(File src, File dest) throws IOException {
        try (InputStream in = new FileInputStream(src)) {
            try (OutputStream out = new FileOutputStream(dest)) {
                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            }
        }
    }

    // endregion
}