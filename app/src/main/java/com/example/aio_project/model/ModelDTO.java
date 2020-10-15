package com.example.aio_project.model;

public class ModelDTO {

    private String title;
    private String downloadCount;
    private int rating;
    private String timeStamp;
    private String viewCount;

    private String description;
    private String fileUrl;
    private boolean isUpload;

    private String seed;


   /* public ModelDTO(String title, String downloadCount, int rating, String timeStamp, String viewCount) {
        this.title = title;
        this.downloadCount = downloadCount;
        this.rating = rating;
        this.timeStamp = timeStamp;
        this.viewCount = viewCount;
    }

    public ModelDTO(String title, String downloadCount, int rating, String timeStamp, String viewCount, String description, String fileUrl, boolean isUpload) {
        this.title = title;
        this.downloadCount = downloadCount;
        this.rating = rating;
        this.timeStamp = timeStamp;
        this.viewCount = viewCount;
        this.description = description;
        this.fileUrl = fileUrl;
        this.isUpload = isUpload;
    }

    public ModelDTO(String title, String downloadCount, int rating, String timeStamp, String viewCount, String description, String seed) {
        this.title = title;
        this.downloadCount = downloadCount;
        this.rating = rating;
        this.timeStamp = timeStamp;
        this.viewCount = viewCount;
        this.description = description;
        this.seed = seed;
    }*/

    public String getTitle() {
        return title;
    }

    public String getDownloadCount() {
        return downloadCount;
    }

    public int getRating() {
        return rating;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public String getViewCount() {
        return viewCount;
    }

    public String getDescription() {
        return description;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public boolean isUpload() {
        return isUpload;
    }

    public String getSeed() {
        return seed;
    }
}
