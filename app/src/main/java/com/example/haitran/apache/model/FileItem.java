package com.example.haitran.apache.model;

/**
 * Created by Hai Tran on 10/2/2016.
 */

public class FileItem {
    private String name;
    private String pathFile;
    private String date;
    private boolean isFile;
    private boolean isDirectory;

    public FileItem(String name, String pathFile, String date,boolean isFile,boolean isDirectory) {
        this.name = name;
        this.pathFile = pathFile;
        this.date = date;
        this.isFile=isFile;
        this.isDirectory=isDirectory;
    }

    public String getName() {
        return name;
    }

    public String getPathFile() {
        return pathFile;
    }

    public String getDate() {
        return date;
    }

    public boolean isFile() {
        return isFile;
    }

    public boolean isDirectory() {
        return isDirectory;
    }

    public void setName(String name) {
        this.name = name;
    }
}
