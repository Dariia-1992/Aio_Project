package com.example.aio_project.model;

public class ModsDTO {
    private String title;
    private String description;
    private String downloadCount;
    private String fileUrl;
    private boolean isUpload;
    private int rating;
    private String timeStamp;
    private String viewCount;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDownloadCount() {
        return downloadCount;
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
}
