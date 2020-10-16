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


    private static final List<CategoryDTO> categoryList = new ArrayList<>();

    private static final List<ModelDTO> mapsList = new ArrayList<>();
    private static final List<ModelDTO> modsList = new ArrayList<>();
    private static final List<ModelDTO> seedsList = new ArrayList<>();
    private static final List<ModelDTO> skinsList = new ArrayList<>();
    private static final List<ModelDTO> texturesList = new ArrayList<>();


    public static List<ModelDTO> getItems() { return modsList; }
    public static List<CategoryDTO> getCategoryList() { return categoryList; }

    public static void loadCategory(IDataLoaded success, IDataLoadedError error) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection("mod_category")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    Log.e("TAG", "loadDat2a: " + queryDocumentSnapshots.size());

                    categoryList.clear();
                    List<CategoryDTO> allCategory = queryDocumentSnapshots.toObjects(CategoryDTO.class);
                    for (CategoryDTO item : allCategory) {
                        Log.e("TAG", "loadData: 11111111111111");
                        categoryList.add(item);
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

    public static void loadModsData(IDataLoaded success, IDataLoadedError error) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection("mod_mods")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    Log.e("TAG", "loadDat2a: " + queryDocumentSnapshots.size());

                    modsList.clear();
                    List<ModelDTO> allCategory = queryDocumentSnapshots.toObjects(ModelDTO.class);
                    for (ModelDTO item : allCategory) {
                        Log.e("TAG", "loadData: 11111111111111");
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
                    Log.e("TAG", "loadDat2a: " + queryDocumentSnapshots.size());

                    texturesList.clear();
                    List<ModelDTO> allCategory = queryDocumentSnapshots.toObjects(ModelDTO.class);
                    for (ModelDTO item : allCategory) {
                        Log.e("TAG", "loadData: 11111111111111");
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
                    Log.e("TAG", "loadDat2a: " + queryDocumentSnapshots.size());

                    mapsList.clear();
                    List<ModelDTO> allCategory = queryDocumentSnapshots.toObjects(ModelDTO.class);
                    for (ModelDTO item : allCategory) {
                        Log.e("TAG", "loadData: 11111111111111");
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
                    Log.e("TAG", "loadDat2a: " + queryDocumentSnapshots.size());

                    seedsList.clear();
                    List<ModelDTO> allCategory = queryDocumentSnapshots.toObjects(ModelDTO.class);
                    for (ModelDTO item : allCategory) {
                        Log.e("TAG", "loadData: 11111111111111");
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
                    Log.e("TAG", "loadDat2a: " + queryDocumentSnapshots.size());

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

    public static String getThumbnailUrl(String name) {
        String correctName = name.replaceFirst("/", "%2F");
        return "https://firebasestorage.googleapis.com/v0/b/modify-fiv.appspot.com/o/" + correctName + "?alt=media";
    }
}
