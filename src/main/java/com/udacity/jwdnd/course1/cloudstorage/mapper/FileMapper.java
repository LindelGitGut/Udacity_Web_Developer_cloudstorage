package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.models.FileModel;
import org.apache.ibatis.annotations.*;

import java.io.File;
import java.util.List;

@Mapper
public interface FileMapper {

    //CREATE
    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata)"+
    "VALUES(#{filename}, #{contenttype}, #{filesize}, #{userid}, #{filedata})")
    @Options(useGeneratedKeys = true,keyProperty = "fileId")
    Integer createFile(FileModel file);


    //READ
    @Select("SELECT * FROM FILES WHERE fileId = #{fileId} AND userid = #{userid}")
    FileModel getFile(String fileId, String userid);

    @Select("SELECT * FROM FILES WHERE userid = #{userid}")
    List<FileModel> getAllUserFiles(String userid);


    @Select("SELECT * FROM FILES WHERE filename = #{filename} AND userid = #{userid}")
    FileModel getFileByName(String filename, String userid);

    //UPDATE
    @Update("UPDATE FILES SET (fileId = #{fileId}, filename = #{filename}, conetenttype = #{contenttype}, filesize = #{filesize}, userid = #{userID}, filedata = #{filedata})")
    Integer updateFile(FileModel file, String userID);

    //REMOVE
    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    Integer removeFile(String fileId);



}
