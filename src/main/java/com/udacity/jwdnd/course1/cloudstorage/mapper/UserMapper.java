package com.udacity.jwdnd.course1.cloudstorage.mapper;


import com.udacity.jwdnd.course1.cloudstorage.models.UserModel;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    //CREATE
    @Insert("INSERT INTO USERS (username, salt, password, firstname, lastname)"+
    "VALUES(#{username}, #{salt}, #{password}, #{firstname}, #{lastname})")
    @Options(useGeneratedKeys = true, keyProperty = "userid")
    int insertUser(UserModel user);



    //TODO Check if User or Integer Userid should be used
    //DELETE
    @Delete("DELETE * FROM USERS WHERE username = #{username}")
    void deleteUser(String username);

    //READ
    @Select("SELECT * FROM USERS WHERE username = #{username}")
    UserModel getUser(String username);
    //UPDATE
    @Update("UPDATE USERS"+
    "SET username = #{username}, salt = #{salt}, password=#{password}, firstname = #{firstname}, lastname = #{lastname}"+
            "WHERE userid = #{userid}")
    void updateUser(UserModel user);



}
