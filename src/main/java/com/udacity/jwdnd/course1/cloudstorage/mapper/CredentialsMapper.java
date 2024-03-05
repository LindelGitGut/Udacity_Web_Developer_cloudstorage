package com.udacity.jwdnd.course1.cloudstorage.mapper;


import com.udacity.jwdnd.course1.cloudstorage.models.CredentialModel;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialsMapper {

/*
    Integer credentialid;
    String url;
    String username;
    String key;
    String password;
    Integer userid;

    */

    // Create
    @Insert("INSERT INTO CREDENTIALS(url,username,key,password,userid) VALUES(#{url},#{username},#{key},#{password},#{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    Integer createCredential(CredentialModel credentialModel);


    //Read
    @Select("SELECT * FROM CREDENTIALS where userid = #{userid} AND credentialid = #{credentialid}")
    CredentialModel getCredential(String credentialid, String userid);

    @Select("SELECT * FROM CREDENTIALS where userid = #{userid}")
    List<CredentialModel> getallUserCredentials(String userid);
    //UPDATE
    @Update("UPDATE CREDENTIALS SET credentialid = #{credentialid} ,url=#{url},username = #{username},key = #{key},password = #{password},userid = #{userid}" +
            "WHERE userid = #{userid} AND credentialid = #{credentialid}")
    Integer changeCredential(CredentialModel credentialModel);
    //DELETE
    @Delete("DELETE FROM CREDENTIALS WHERE userid = #{userid} AND credentialid = #{credentialid}")
    Integer deleteCredential(String userid, String credetialid);

}
