package com.example.aio_project.model;

public class MapsDTO {
    private String description;
    private String fileUrl;
    private boolean isUpload;
    private int rating;
    private String timeStamp;
    private String viewCount;
    private String downloadCount;

    public String getDescription() {
        return description;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public boolean isUpload() {
        return isUpload;
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

    public String getDownloadCount() {
        return downloadCount;
    }
}
