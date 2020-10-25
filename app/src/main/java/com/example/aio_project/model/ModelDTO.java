package com.example.aio_project.model;

public class ModelDTO {

    private String modeTypeId;
    private String title;
    private String downloadcount;
    private int rating;
    //private  timestamp;
    private String viewcount;

    private String description;
    private String fileurl;
    private String isupload;

    private String seed;

    public String getModeTypeId() {
        return modeTypeId;
    }

    public String getTitle() {
        return title;
    }

    public String getDownloadcount() {
        return downloadcount;
    }

    public int getRating() {
        return rating;
    }

/*    public String getTimestamp() {
        return timestamp;
    }*/

    public String getViewcount() {
        return viewcount;
    }

    public String getDescription() {
        return description;
    }

    public String getFileurl() {
        return fileurl;
    }

    public String isIsupload() {
        return isupload;
    }

    public String getSeed() {
        return seed;
    }
}
