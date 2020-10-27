package com.example.aio_project.model;

public class ModelDTO {

    // Common
    private String title;
    private String categoryid;
    private String downloadcount;
    private String viewcount;
    private String rating;

    // Maps, Mods, Seeds, Textures
    private String description;

    // Maps, Mods, Textures
    private String fileurl;
    private String isupload;

    // Seeds
    private String seed;

    // region Getters

    public String getTitle() { return title; }
    public String getCategoryId() { return categoryid; }
    public String getDownloadsCount() { return downloadcount; }
    public String getViewsCount() { return viewcount; }
    public String getRating() { return rating; }
    public String getDescription() { return description; }
    public String getFileUrl() { return fileurl; }
    public String getIsUpload() { return isupload; }
    public String getSeed() { return seed; }

    // endregion
}
