package com.example.aio_project.utils;

import android.util.Log;

import com.example.aio_project.model.CategoryDTO;
import com.example.aio_project.model.MapsDTO;
import com.example.aio_project.model.ModsDTO;
import com.example.aio_project.model.SeedsDTO;
import com.example.aio_project.model.SkinsDTO;
import com.example.aio_project.model.TexturesDTO;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class DownloadHelper {
    public interface IDataLoaded {
        void onLoaded();
    }

    public interface IDataLoadedError {
        void onError(String error);
    }

    private static final List<CategoryDTO> categoryList = new ArrayList<>();
    private static final List<MapsDTO> mapsList = new ArrayList<>();
    private static final List<ModsDTO> modsList = new ArrayList<>();
    private static final List<SeedsDTO> seedsList = new ArrayList<>();
    private static final List<SkinsDTO> skinsList = new ArrayList<>();
    private static final List<TexturesDTO> texturesList = new ArrayList<>();

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

    public static void loadMapsData(IDataLoaded success, IDataLoadedError error) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection("mod_maps")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    Log.e("TAG", "loadDat2a: " + queryDocumentSnapshots.size());

                    mapsList.clear();
                    List<MapsDTO> allCategory = queryDocumentSnapshots.toObjects(MapsDTO.class);
                    for (MapsDTO item : allCategory) {
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

    public static void loadModsData(IDataLoaded success, IDataLoadedError error) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection("mod_mods")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    Log.e("TAG", "loadDat2a: " + queryDocumentSnapshots.size());

                    modsList.clear();
                    List<ModsDTO> allCategory = queryDocumentSnapshots.toObjects(ModsDTO.class);
                    for (ModsDTO item : allCategory) {
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

    public static void loadSeedsData(IDataLoaded success, IDataLoadedError error) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection("mod_seeds")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    Log.e("TAG", "loadDat2a: " + queryDocumentSnapshots.size());

                    seedsList.clear();
                    List<SeedsDTO> allCategory = queryDocumentSnapshots.toObjects(SeedsDTO.class);
                    for (SeedsDTO item : allCategory) {
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
                    List<SkinsDTO> allCategory = queryDocumentSnapshots.toObjects(SkinsDTO.class);
                    for (SkinsDTO item : allCategory) {
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

    public static void loadTexturesData(IDataLoaded success, IDataLoadedError error) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection("mod_textures")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    Log.e("TAG", "loadDat2a: " + queryDocumentSnapshots.size());

                    texturesList.clear();
                    List<TexturesDTO> allCategory = queryDocumentSnapshots.toObjects(TexturesDTO.class);
                    for (TexturesDTO item : allCategory) {
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
}
