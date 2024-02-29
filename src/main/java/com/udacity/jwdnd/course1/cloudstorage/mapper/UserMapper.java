package com.udacity.jwdnd.course1.cloudstorage.mapper;


import com.udacity.jwdnd.course1.cloudstorage.models.UserModel;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {


    @Insert("INSERT INTO USERS (username, salt, password, firstname, lastname)"+
    "VALUES(#{username}, #{salt}, #{password}, #{firstname}, #{lastname})")
    @Options(useGeneratedKeys = true, keyProperty = "userid")
    int insertUser(UserModel user);



    //TODO Check if User or Integer Userid should be used

    @Delete("DELETE FROM USERS WHERE username = #{username}")
    void deleteUser(String username);

    @Select("SELECT FROM USERS WHERE username = #{username}")
    UserModel getUser(String username);



}
