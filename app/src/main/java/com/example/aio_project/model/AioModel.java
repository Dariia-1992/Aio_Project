package com.example.aio_project.model;

import java.util.Date;
import java.util.UUID;

public class AioModel {
    private final String id = UUID.randomUUID().toString();
    private String category;
    private String title;
    private String downloads;
    private String views;
    private String description;
    private int image;
    private String date;

    public AioModel(String category, String title, String downloads, String views, String description, int image, String date) {
        this.category = category;
        this.title = title;
        this.downloads = downloads;
        this.views = views;
        this.description = description;
        this.image = image;
        this.date = date;
    }

    /*    public AioModel(String title, int image, String category) {
        this.title = title;
        this.image = image;
        this.category = category;
    }*/

/*    public AioModel(Parcel in) {
        setTitle(in.readString());
        setCategory(in.readString());
        setImage(in.readInt());
    }*/

//    public AioModel(String category, String title, String downloads, String views, String description, String thumbnail) {
//        this.category = category;
//        this.title = title;
//        this.downloads = downloads;
//        this.views = views;
//        this.description = description;
//        this.image = thumbnail;
//    }

    public String getId() { return id; }
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

    public String  getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    /* @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(getTitle());
        parcel.writeString(getCategory());
        parcel.writeInt(getImage());
    }

    public static final Parcelable.Creator<AioModel> CREATOR = new Parcelable.Creator<AioModel>() {
        @Override
        public AioModel createFromParcel(Parcel parcel) {
            return new AioModel(parcel);
        }

        @Override
        public AioModel[] newArray(int i) {
            return new AioModel[i];
        }
    };*/
}
