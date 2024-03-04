package com.udacity.jwdnd.course1.cloudstorage.mapper;


import com.udacity.jwdnd.course1.cloudstorage.models.NoteModel;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    //CREATE
    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid"+"" +
            "VALUES(#{notetitle}, #{notedescription}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    Integer createNote(NoteModel noteModel);


    //READ

    @Select("SELECT * FROM NOTES WHERE noteid = #{noteid} AND userid = #{userid}")
    NoteModel getNote(String noteid, String userid);

    @Select("SELECT * FROM NOTES WHERE userid = #{userid}")
    List<NoteModel> getAllUserNotes(String userid);

    //UPDATE

    @Update("UPDATE NOTES SET (noteid = #{noteid}, notetitle = #{notetitle}, notedescription = #{notedescription}, userid = #{userid})")
    Integer updateNote(NoteModel noteModel);

    //DELETE
    @Delete("DELETE FROM NOTES WHERE userid= #{userid} AND noteid = #{noteid}")
    Integer deleteNote(String userid, String noteid);


    //TODO Implement Mapper
}
