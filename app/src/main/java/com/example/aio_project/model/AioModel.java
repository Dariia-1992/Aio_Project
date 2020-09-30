package com.example.aio_project.model;

public class AioModel {
    private String category;
    private String title;
    private String downloads;
    private String views;
    private String description;
    private int image;

    public AioModel(String title, int image) {
        this.title = title;
        this.image = image;
    }

//    public AioModel(String category, String title, String downloads, String views, String description, String thumbnail) {
//        this.category = category;
//        this.title = title;
//        this.downloads = downloads;
//        this.views = views;
//        this.description = description;
//        this.image = thumbnail;
//    }

    public String getCategory() {
        return category;
    }

    public String getTitle() {
        return title;
    }

    public String getDownloads() {
        return downloads;
    }

    public String getViews() {
        return views;
    }

    public String getDescription() {
        return description;
    }

    public int getImage() {
        return image;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDownloads(String downloads) {
        this.downloads = downloads;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
