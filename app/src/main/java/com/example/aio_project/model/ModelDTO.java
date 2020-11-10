package com.example.aio_project.model;

import com.google.firebase.Timestamp;

public class ModelDTO {
    private String id;

    // Common
    private String title;
    private String categoryid;
    private String downloadcount;
    private String viewcount;
    private String rating;
    private Timestamp timestamp;

    // Maps, Mods, Seeds, Textures
    private String description;

    // Maps, Mods, Textures
    private String fileurl;
    private String isupload;

    // Seeds
    private String seed;

    // Local data
    private Category localCategory;
    private String collectionName;

    // region Getters

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getCategoryid() { return categoryid; }
    public String getDownloadcount() { return downloadcount; }
    public String getViewcount() { return viewcount; }
    public String getRating() { return rating; }
    public Timestamp getTimestamp() { return timestamp; }
    public String getDescription() { return description; }
    public String getFileurl() { return fileurl; }
    public String getIsupload() { return isupload; }
    public String getSeed() { return seed; }
    public Category getLocalCategory() { return localCategory; }
    public String getCollectionName() { return collectionName; }

    // endregion

    // region Getters

    public void setId(String id) { this.id = id; }
    public void setLocalCategory(Category localCategory) { this.localCategory = localCategory; }
    public void setCollectionName(String collectionName) { this.collectionName = collectionName; }

    public void setDownloadcount(String value) { this.downloadcount = value; }
    public void setViewcount(String value) { this.viewcount = value; }
    // endregion
}
