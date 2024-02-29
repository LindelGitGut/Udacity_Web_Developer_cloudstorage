package com.udacity.jwdnd.course1.cloudstorage.models;

import java.io.File;

public class FileModel {

    //TODO check if custom Constructor is needed

    Integer fileId;
    String filename;
    String contenttype;
    String filesize;
    Integer userid;


    //TODO Check if File is the Righ Datatype corresponding to BloB (see schema.sql)
    File BLOB;


    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getContenttype() {
        return contenttype;
    }

    public void setContenttype(String contenttype) {
        this.contenttype = contenttype;
    }

    public String getFilesize() {
        return filesize;
    }

    public void setFilesize(String filesize) {
        this.filesize = filesize;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public File getBLOB() {
        return BLOB;
    }

    public void setBLOB(File BLOB) {
        this.BLOB = BLOB;
    }
}
