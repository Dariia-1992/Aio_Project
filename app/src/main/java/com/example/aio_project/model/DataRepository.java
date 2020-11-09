package com.example.aio_project.model;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.example.aio_project.model.interfaces.ILoadError;
import com.example.aio_project.model.interfaces.ILoadSuccess;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;

/**
 * Created by Alexey Matrosov on 29.10.2020.
 */

public class DataRepository {
    private static final String imageDescriptionListName = "mod_fileimage_collection";
    private static final String IMAGE_TYPE_THUMBNAIL = "thumb";
    private static final String IMAGE_TYPE_SCREENSHOT = "screen";
    private static final String IMAGE_TYPE_FILE = "file";

    private static Map<String, List<ModelDTO>> loadedItems = new HashMap<>();
    private static List<ImageDescriptionDTO> imageData = new ArrayList<>();

    public static void loadImages() {
        loadImagesDataAsync(items -> {
            imageData.clear();
            imageData.addAll(items);
        }, message -> {} );
    }

    public static void loadDataAsync(Category category, String collection, ILoadSuccess<ModelDTO> successListener, ILoadError errorListener) {
        if (loadedItems.containsKey(collection) && loadedItems.get(collection) != null) {
            Handler mainHandler = new Handler(Looper.getMainLooper());
            mainHandler.postDelayed(() -> successListener.onSuccess(loadedItems.get(collection)), 100);
            return;
        }

        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(collection)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<ModelDTO> data = new ArrayList<>();
                    for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                        ModelDTO item = document.toObject(ModelDTO.class);
                        if (item != null) {
                            item.setId(document.getId());
                            item.setLocalCategory(category);
                            data.add(item);
                        }
                    }

                    loadedItems.put(collection, data);

                    if (successListener != null)
                        successListener.onSuccess(data);
                })
                .addOnFailureListener(e -> {
                    if (errorListener != null)
                        errorListener.onError(e.getMessage());
                });
    }

    public static void loadImagesDataAsync(ILoadSuccess<ImageDescriptionDTO> successListener, ILoadError errorListener) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(imageDescriptionListName)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<ImageDescriptionDTO> data = queryDocumentSnapshots.toObjects(ImageDescriptionDTO.class);

                    if (successListener != null)
                        successListener.onSuccess(data);
                })
                .addOnFailureListener(e -> {
                    if (errorListener != null)
                        errorListener.onError(e.getMessage());
                });
    }

    public static String getThumbnailUrl(String entryId) { return getUrlFromFileImageCollection(entryId, IMAGE_TYPE_THUMBNAIL); }
    public static String getFileUrl(String entryId) { return getUrlFromFileImageCollection(entryId, IMAGE_TYPE_FILE); }

    @NonNull
    public static List<String> getItemScreenshots(String entryId) {
        if (TextUtils.isEmpty(entryId))
            return new ArrayList<>();

        List<String> urls = new ArrayList<>();
        for (ImageDescriptionDTO data : imageData) {
            if (data == null || data.getModtypeid() == null || data.getField() == null)
                continue;

            if (entryId.equals(data.getModtypeid()) && data.getField().equals(IMAGE_TYPE_SCREENSHOT))
                urls.add(data.getFile());
        }

        return urls;
    }

    public static ModelDTO findById(String id) {
        for (Map.Entry<String, List<ModelDTO>> entry : loadedItems.entrySet()) {
            if (entry == null || entry.getValue() == null)
                continue;

            for (ModelDTO item : entry.getValue()) {
                if (item == null)
                    continue;

                if (item.getId().equals(id))
                    return item;
            }
        }

        return null;
    }

    private static String getUrlFromFileImageCollection(String entryId, String type) {
        if (TextUtils.isEmpty(entryId))
            return null;

        for (ImageDescriptionDTO data : imageData) {
            if (data == null || data.getModtypeid() == null || data.getField() == null)
                continue;

            if (entryId.equals(data.getModtypeid()) && data.getField().equals(type))
                return data.getFile();
        }

        return null;
    }
}
