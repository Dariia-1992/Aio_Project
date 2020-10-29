package com.example.aio_project.model;

import com.example.aio_project.model.interfaces.ILoadError;
import com.example.aio_project.model.interfaces.ILoadSuccess;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

/**
 * Created by Alexey Matrosov on 29.10.2020.
 */

public class DataRepository {
    private static final String imageDescriptionListName = "mod_fileimage_collection";

    public static void loadDataAsync(String collection, ILoadSuccess<ModelDTO> successListener, ILoadError errorListener) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(collection)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<ModelDTO> data = queryDocumentSnapshots.toObjects(ModelDTO.class);

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
}
