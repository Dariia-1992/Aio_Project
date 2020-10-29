package com.example.aio_project.model;

import android.text.TextUtils;

import com.example.aio_project.model.interfaces.ILoadError;
import com.example.aio_project.model.interfaces.ILoadSuccess;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexey Matrosov on 29.10.2020.
 */

public class DataRepository {
    private static final String imageDescriptionListName = "mod_fileimage_collection";
    private static final String IMAGE_TYPE_THUMBNAIL = "thumb";

    private static List<ImageDescriptionDTO> imageData = new ArrayList<>();

    public static void loadImages() {
        loadImagesDataAsync(items -> {
            imageData.clear();
            imageData.addAll(items);
        }, message -> {} );
    }

    public static void loadDataAsync(String collection, ILoadSuccess<ModelDTO> successListener, ILoadError errorListener) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(collection)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<ModelDTO> data = new ArrayList<>();
                    for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                        ModelDTO item = document.toObject(ModelDTO.class);
                        if (item != null) {
                            item.setId(document.getId());
                            data.add(item);
                        }
                    }

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

    public static String getThumbnailUrl(String entryId) {
        if (TextUtils.isEmpty(entryId))
            return null;

        for (ImageDescriptionDTO data : imageData) {
            if (data == null || data.getModtypeid() == null || data.getField() == null)
                continue;

            if (entryId.equals(data.getModtypeid()) && data.getField().equals(IMAGE_TYPE_THUMBNAIL))
                return data.getFile();
        }

        return null;
    }
}
