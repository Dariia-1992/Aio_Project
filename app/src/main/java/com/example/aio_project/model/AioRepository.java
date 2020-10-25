package com.example.aio_project.model;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class AioRepository {
    public interface IDataLoaded {
        void onLoaded();
    }

    public interface IDataLoadedError {
        void onError(String error);
    }

    private static final List<String> categoryList = new ArrayList<>();
    private static final List<ImageCollectionList> imageRes = new ArrayList<>();

    private static final List<ModelDTO> mapsList = new ArrayList<>();
    private static final List<ModelDTO> modsList = new ArrayList<>();
    private static final List<ModelDTO> seedsList = new ArrayList<>();
    private static final List<ModelDTO> skinsList = new ArrayList<>();
    private static final List<ModelDTO> texturesList = new ArrayList<>();

    public static List<ModelDTO> getMods() { return modsList; }
    public static List<ModelDTO> getTextures() { return texturesList; }

    public static List<String> getCategoryList() {
        categoryList.add("Mods");
        categoryList.add("Textures");
        categoryList.add("Maps");
        categoryList.add("Seeds");
        categoryList.add("Skins");
        return categoryList;
    }

     public static void loadModsData(IDataLoaded success, IDataLoadedError error) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection("mod_mods")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    modsList.clear();
                    List<ModelDTO> allCategory = queryDocumentSnapshots.toObjects(ModelDTO.class);
                    for (ModelDTO item : allCategory) {
                        modsList.add(item);
/*                        if(BuildConfig.showedCategory.equalsIgnoreCase(item.getCategory()))
                            categoryList.add(item);*/
                    }

                    if (success != null)
                        success.onLoaded();
                })

                .addOnFailureListener(e -> {
                    if (error != null)
                        error.onError(e.getMessage());
                });
    }

    public static void loadTexturesData(IDataLoaded success, IDataLoadedError error) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection("mod_textures")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    texturesList.clear();
                    List<ModelDTO> allCategory = queryDocumentSnapshots.toObjects(ModelDTO.class);
                    for (ModelDTO item : allCategory) {
                        texturesList.add(item);
/*                        if(BuildConfig.showedCategory.equalsIgnoreCase(item.getCategory()))
                            categoryList.add(item);*/
                    }

                    if (success != null)
                        success.onLoaded();
                })

                .addOnFailureListener(e -> {
                    if (error != null)
                        error.onError(e.getMessage());
                });
    }

    public static void loadMapsData(IDataLoaded success, IDataLoadedError error) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection("mod_maps")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    mapsList.clear();
                    List<ModelDTO> allCategory = queryDocumentSnapshots.toObjects(ModelDTO.class);
                    for (ModelDTO item : allCategory) {
                        mapsList.add(item);
/*                        if(BuildConfig.showedCategory.equalsIgnoreCase(item.getCategory()))
                            categoryList.add(item);*/
                    }

                    if (success != null)
                        success.onLoaded();
                })

                .addOnFailureListener(e -> {
                    if (error != null)
                        error.onError(e.getMessage());
                });
    }

    public static void loadSeedsData(IDataLoaded success, IDataLoadedError error) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection("mod_seeds")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    seedsList.clear();
                    List<ModelDTO> allCategory = queryDocumentSnapshots.toObjects(ModelDTO.class);
                    for (ModelDTO item : allCategory) {
                        seedsList.add(item);
/*                        if(BuildConfig.showedCategory.equalsIgnoreCase(item.getCategory()))
                            categoryList.add(item);*/
                    }

                    if (success != null)
                        success.onLoaded();
                })

                .addOnFailureListener(e -> {
                    if (error != null)
                        error.onError(e.getMessage());
                });
    }

    public static void loadSkinsData(IDataLoaded success, IDataLoadedError error) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection("mod_skins")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    skinsList.clear();
                    List<ModelDTO> allCategory = queryDocumentSnapshots.toObjects(ModelDTO.class);
                    for (ModelDTO item : allCategory) {
                        Log.e("TAG", "loadData: 11111111111111");
                        skinsList.add(item);
/*                        if(BuildConfig.showedCategory.equalsIgnoreCase(item.getCategory()))
                            categoryList.add(item);*/
                    }

                    if (success != null)
                        success.onLoaded();
                })

                .addOnFailureListener(e -> {
                    if (error != null)
                        error.onError(e.getMessage());
                });
    }

    public static void loadImageCollection(IDataLoaded success, IDataLoadedError error) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection("mod_fileimage_collection")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    Log.e("TAG", "loadDat2a: " + queryDocumentSnapshots.size());

                    imageRes.clear();
                    List<ImageCollectionList> allCategory = queryDocumentSnapshots.toObjects(ImageCollectionList.class);
                    for (ImageCollectionList item : allCategory) {
                       // Log.e("TAG", "loadData: 11111111111111");
                        imageRes.add(item);
/*                        if(BuildConfig.showedCategory.equalsIgnoreCase(item.getCategory()))
                            categoryList.add(item);*/
                    }

                    if (success != null)
                        success.onLoaded();
                })

                .addOnFailureListener(e -> {
                    if (error != null)
                        error.onError(e.getMessage());
                });
    }

/*    public static ModelDTO getItemById(String itemId) {
        for (ModelDTO info : items) {
            if (info.getId().equals(itemId))
                return info;
        }
        return null;
    }*/

    public static String getThumbnailUrl(String name) {

        String correctName = name.replaceFirst("/", "%2F");
        return "https://firebasestorage.googleapis.com/v0/b/modify-fiv.appspot.com/o/" + correctName + "?alt=media";
    }
}
