package com.example.aio_project.model;

import com.example.aio_project.R;

import java.util.ArrayList;
import java.util.List;

public class AioRepository {
    private static final List<AioModel> items = new ArrayList<>();

    public static List<AioModel> getItems() {
        return items;
    }

    public static List<AioModel> getLatest() {
        List<AioModel> list = createAioLatestList();
        return list;
    }

    public static List<AioModel> getPopularAddons() {
        List<AioModel> list = createAioPopularList();
        return list;
    }

    public static List<AioModel> getPopularMaps() {
        List<AioModel> list = createAioPopularMapsList();
        return list;
    }

    public static List<AioModel> getMods() {
        List<AioModel> allList = createAllList();
        List<AioModel> modsList = new ArrayList<>();
        for (AioModel item : allList) {
            if (item.getCategory().equals("mods"))
                modsList.add(item);
        }
        return modsList;
    }

    public void getTextures(List<AioModel> items) {
        List<AioModel> allList = createAllList();
        List<AioModel> texturesList = new ArrayList<>();
        for (AioModel item : items) {
            if (item.getCategory().equals("textures"))
                texturesList.add(item);
        }
    }

    public void getMaps(List<AioModel> items) {
        List<AioModel> mapsList = new ArrayList<>();
        for (AioModel item : items) {
            if (item.getCategory().equals("maps"))
                mapsList.add(item);
        }
    }

    public void getSeeds(List<AioModel> items) {
        List<AioModel> seedsList = new ArrayList<>();
        for (AioModel item : items) {
            if (item.getCategory().equals("seeds"))
                seedsList.add(item);
        }
    }

    public void getSkins(List<AioModel> items) {
        List<AioModel> skinsList = new ArrayList<>();
        for (AioModel item : items) {
            if (item.getCategory().equals("skins"))
                skinsList.add(item);
        }
    }

    public static List<AioModel> createAioLatestList(){
        List<AioModel> latestList = new ArrayList<>();

        latestList.add(new AioModel("mods", "Lorem ipsum dolor", "100K", "150K",
                "Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor hvghreiuhv irhfjnhdjc dbhdsbvhb sjdhbvj" +
                        "kdfvkjnvv mmrkbjtrh pgvoekmdc hhreighie rujv gbeuybgbd hbcewbywe uygcueywb cansxyu vbdvhbvr",
                R.drawable.null_image, "12.05.20"));

        latestList.add(new AioModel("textures", "Lorem ipsum dolor", "100K", "150K",
                "Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor hvghreiuhv irhfjnhdjc dbhdsbvhb sjdhbvj" +
                        "kdfvkjnvv mmrkbjtrh pgvoekmdc hhreighie rujv gbeuybgbd hbcewbywe uygcueywb cansxyu vbdvhbvr",
                R.drawable.null_image, "12.05.20"));

        latestList.add(new AioModel("skins", "Lorem ipsum dolor", "100K", "150K",
                "Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor hvghreiuhv irhfjnhdjc dbhdsbvhb sjdhbvj" +
                        "kdfvkjnvv mmrkbjtrh pgvoekmdc hhreighie rujv gbeuybgbd hbcewbywe uygcueywb cansxyu vbdvhbvr",
                R.drawable.null_image, "12.05.20"));

        latestList.add(new AioModel("seeds", "Lorem ipsum dolor", "100K", "150K",
                "Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor hvghreiuhv irhfjnhdjc dbhdsbvhb sjdhbvj" +
                        "kdfvkjnvv mmrkbjtrh pgvoekmdc hhreighie rujv gbeuybgbd hbcewbywe uygcueywb cansxyu vbdvhbvr",
                R.drawable.null_image, "12.05.20"));

        latestList.add(new AioModel("maps", "Lorem ipsum dolor", "100K", "150K",
                "Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor hvghreiuhv irhfjnhdjc dbhdsbvhb sjdhbvj" +
                        "kdfvkjnvv mmrkbjtrh pgvoekmdc hhreighie rujv gbeuybgbd hbcewbywe uygcueywb cansxyu vbdvhbvr",
                R.drawable.null_image, "12.05.20"));

        return latestList;
    }

    public static List<AioModel>  createAioPopularList() {
        List<AioModel> popularModsList = new ArrayList<>();

        popularModsList.add(new AioModel("mods", "Lorem ipsum dolor", "100K", "150K",
                "Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor hvghreiuhv irhfjnhdjc dbhdsbvhb sjdhbvj" +
                        "kdfvkjnvv mmrkbjtrh pgvoekmdc hhreighie rujv gbeuybgbd hbcewbywe uygcueywb cansxyu vbdvhbvr",
                R.drawable.null_image, "12.05.20"));

        popularModsList.add(new AioModel("mods", "Lorem ipsum dolor", "100K", "150K",
                "Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor hvghreiuhv irhfjnhdjc dbhdsbvhb sjdhbvj" +
                        "kdfvkjnvv mmrkbjtrh pgvoekmdc hhreighie rujv gbeuybgbd hbcewbywe uygcueywb cansxyu vbdvhbvr",
                R.drawable.null_image, "12.05.20"));

        popularModsList.add(new AioModel("mods", "Lorem ipsum dolor", "100K", "150K",
                "Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor hvghreiuhv irhfjnhdjc dbhdsbvhb sjdhbvj" +
                        "kdfvkjnvv mmrkbjtrh pgvoekmdc hhreighie rujv gbeuybgbd hbcewbywe uygcueywb cansxyu vbdvhbvr",
                R.drawable.null_image, "12.05.20"));

        popularModsList.add(new AioModel("mods", "Lorem ipsum dolor", "100K", "150K",
                "Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor hvghreiuhv irhfjnhdjc dbhdsbvhb sjdhbvj" +
                        "kdfvkjnvv mmrkbjtrh pgvoekmdc hhreighie rujv gbeuybgbd hbcewbywe uygcueywb cansxyu vbdvhbvr",
                R.drawable.null_image, "12.05.20"));

        popularModsList.add(new AioModel("mods", "Lorem ipsum dolor", "100K", "150K",
                "Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor hvghreiuhv irhfjnhdjc dbhdsbvhb sjdhbvj" +
                        "kdfvkjnvv mmrkbjtrh pgvoekmdc hhreighie rujv gbeuybgbd hbcewbywe uygcueywb cansxyu vbdvhbvr",
                R.drawable.null_image, "12.05.20"));

        popularModsList.add(new AioModel("mods", "Lorem ipsum dolor", "100K", "150K",
                "Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor hvghreiuhv irhfjnhdjc dbhdsbvhb sjdhbvj" +
                        "kdfvkjnvv mmrkbjtrh pgvoekmdc hhreighie rujv gbeuybgbd hbcewbywe uygcueywb cansxyu vbdvhbvr",
                R.drawable.null_image, "12.05.20"));

        popularModsList.add(new AioModel("mods", "Lorem ipsum dolor", "100K", "150K",
                "Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor hvghreiuhv irhfjnhdjc dbhdsbvhb sjdhbvj" +
                        "kdfvkjnvv mmrkbjtrh pgvoekmdc hhreighie rujv gbeuybgbd hbcewbywe uygcueywb cansxyu vbdvhbvr",
                R.drawable.null_image, "12.05.20"));

        return popularModsList;
    }

    public static List<AioModel> createAioPopularMapsList() {
        List<AioModel> popularMapsList = new ArrayList<>();

        popularMapsList.add(new AioModel("maps", "Lorem ipsum dolor", "100K", "150K",
                "Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor hvghreiuhv irhfjnhdjc dbhdsbvhb sjdhbvj" +
                        "kdfvkjnvv mmrkbjtrh pgvoekmdc hhreighie rujv gbeuybgbd hbcewbywe uygcueywb cansxyu vbdvhbvr",
                R.drawable.null_image, "12.05.20"));

        popularMapsList.add(new AioModel("maps", "Lorem ipsum dolor", "100K", "150K",
                "Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor hvghreiuhv irhfjnhdjc dbhdsbvhb sjdhbvj" +
                        "kdfvkjnvv mmrkbjtrh pgvoekmdc hhreighie rujv gbeuybgbd hbcewbywe uygcueywb cansxyu vbdvhbvr",
                R.drawable.null_image, "12.05.20"));

        popularMapsList.add(new AioModel("maps", "Lorem ipsum dolor", "100K", "150K",
                "Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor hvghreiuhv irhfjnhdjc dbhdsbvhb sjdhbvj" +
                        "kdfvkjnvv mmrkbjtrh pgvoekmdc hhreighie rujv gbeuybgbd hbcewbywe uygcueywb cansxyu vbdvhbvr",
                R.drawable.null_image, "12.05.20"));

        popularMapsList.add(new AioModel("maps", "Lorem ipsum dolor", "100K", "150K",
                "Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor hvghreiuhv irhfjnhdjc dbhdsbvhb sjdhbvj" +
                        "kdfvkjnvv mmrkbjtrh pgvoekmdc hhreighie rujv gbeuybgbd hbcewbywe uygcueywb cansxyu vbdvhbvr",
                R.drawable.null_image, "12.05.20"));

        popularMapsList.add(new AioModel("maps", "Lorem ipsum dolor", "100K", "150K",
                "Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor hvghreiuhv irhfjnhdjc dbhdsbvhb sjdhbvj" +
                        "kdfvkjnvv mmrkbjtrh pgvoekmdc hhreighie rujv gbeuybgbd hbcewbywe uygcueywb cansxyu vbdvhbvr",
                R.drawable.null_image, "12.05.20"));

        popularMapsList.add(new AioModel("maps", "Lorem ipsum dolor", "100K", "150K",
                "Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor hvghreiuhv irhfjnhdjc dbhdsbvhb sjdhbvj" +
                        "kdfvkjnvv mmrkbjtrh pgvoekmdc hhreighie rujv gbeuybgbd hbcewbywe uygcueywb cansxyu vbdvhbvr",
                R.drawable.null_image, "12.05.20"));

        popularMapsList.add(new AioModel("maps", "Lorem ipsum dolor", "100K", "150K",
                "Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor Lorem ipsum dolor hvghreiuhv irhfjnhdjc dbhdsbvhb sjdhbvj" +
                        "kdfvkjnvv mmrkbjtrh pgvoekmdc hhreighie rujv gbeuybgbd hbcewbywe uygcueywb cansxyu vbdvhbvr",
                R.drawable.null_image, "12.05.20"));

        return popularMapsList;
    }

    public static List<AioModel> createAllList() {
        //items.clear();
        items.addAll(createAioLatestList());
        items.addAll(createAioPopularList());
        items.addAll(createAioPopularMapsList());
        return items;
    }

/*
    public void getCurrentList() {
        currentListMods.clear();
        currentListTextures.clear();
        currentListMaps.clear();
        currentListSeeds.clear();
        currentListSkins.clear();

        for (AioModel item : items) {
            if (item.getCategory().equals("mods"))
                currentListMods.add(item);
            if (item.getCategory().equals("textures"))
                currentListTextures.add(item);
            if (item.getCategory().equals("maps"))
                currentListMaps.add(item);
            if (item.getCategory().equals("seeds"))
                currentListSeeds.add(item);
            if (item.getCategory().equals("skins"))
                currentListSkins.add(item);
        }
    }*/
}
