package com.example.aio_project.utils;

import android.app.DownloadManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;

import com.example.aio_project.model.Category;
import com.example.aio_project.model.DataRepository;
import com.example.aio_project.model.ModelDTO;

import java.io.File;
import java.util.List;

public class DownloadHelper {
    public enum DownloadingState {
        NotDownloaded,
        Downloaded,
        Downloading
    }

    public static String getDownloadUrl(ModelDTO entry) {
        if (entry.getLocalCategory() == Category.SKIN) {
            List<String> screens = DataRepository.getItemScreenshots(entry.getId());
            return screens.isEmpty() ? null : screens.get(0);
        } else {
           return entry.getFileurl();
        }
    }

    public static long downloadFile(Context context, String fileUrl) {
        if (TextUtils.isEmpty(fileUrl))
            return 0;

        Uri uri = Uri.parse(fileUrl);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, uri.getLastPathSegment());

        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        if (downloadManager == null)
            return 0;

        return downloadManager.enqueue(request);
    }

    public static DownloadingState getDownloadingStatus(Context context, ModelDTO entry) {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        if (downloadManager == null)
            return DownloadingState.NotDownloaded;

        long downloadId = LocalStorage.getIdForModInfo(context, entry);
        if (downloadId == 0)
            return DownloadingState.NotDownloaded;

        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(downloadId);

        // Check if file is downloaded and still exists
        Cursor cursor = downloadManager.query(query);
        if (cursor.moveToFirst()) {
            int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));

            if (status == DownloadManager.STATUS_SUCCESSFUL) {
                String localFile = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                if (localFile != null) {
                    File file = new File(Uri.parse(localFile).getPath());
                    if (file.isFile()) {
                        cursor.close();
                        return DownloadingState.Downloaded;
                    }
                }
            } else if (status == DownloadManager.STATUS_RUNNING
                    || status == DownloadManager.STATUS_PAUSED
                    || status == DownloadManager.STATUS_PENDING) {
                cursor.close();
                return DownloadingState.Downloading;
            }
        }

        cursor.close();
        return DownloadingState.NotDownloaded;
    }
}
