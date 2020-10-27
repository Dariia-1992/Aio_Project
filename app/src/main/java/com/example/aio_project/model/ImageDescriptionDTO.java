package com.example.aio_project.model;

public class ImageDescriptionDTO {
    private String field;
    private String file;
    private String filename;
    private String modtype;
    private String modetypeid;

    // region Getters

    public String getField() { return field; }
    public String getFile() { return file; }
    public String getFileName() { return filename; }
    public String getEntryType() { return modtype; }
    public String getEntryId() { return modetypeid; }

    // endregion
}
