package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.models.FileModel;
import org.apache.ibatis.annotations.*;

import java.io.File;

@Mapper
public interface FileMapper {
    //TODO check sanity

    //CREATE
    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, BLOB)"+
    "VALUES(#{filename}, #{contenttype}, #{filesize}, #{userid}, #{BLOB})")
    Integer createFile(FileModel file);

    //READ
    @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
    FileModel getFile(String fileId);

    //UPDATE
    @Update("UPDATE FILES SET (fileId = #{fileId}, filename = #{filename}, conetenttype = #{contenttype}, filesize = #{filesize}, userid = #{userid}, BLOB = #{BLOB})")
    Integer updateFile(FileModel file);

    //REMOVE
    @Delete("DELETE * FROM FILES WHERE fileId = #{fileId}")
    Integer removeFile(FileModel file);

}
