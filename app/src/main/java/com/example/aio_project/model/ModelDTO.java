package com.example.aio_project.model;

public class ModelDTO {
    private String id;

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

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getCategoryid() { return categoryid; }
    public String getDownloadcount() { return downloadcount; }
    public String getViewcount() { return viewcount; }
    public String getRating() { return rating; }
    public String getDescription() { return description; }
    public String getFileurl() { return fileurl; }
    public String getIsupload() { return isupload; }
    public String getSeed() { return seed; }

    // endregion

    // region Getters

    public void setId(String id) { this.id = id; }

    // endregion
}
