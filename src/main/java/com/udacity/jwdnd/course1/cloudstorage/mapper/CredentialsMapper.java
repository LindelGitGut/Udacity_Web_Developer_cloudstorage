package com.udacity.jwdnd.course1.cloudstorage.mapper;


import com.udacity.jwdnd.course1.cloudstorage.models.CredentialModel;
import org.apache.ibatis.annotations.*;

@Mapper
public interface CredentialsMapper {

    // Create
    @Insert("INSERT INTO CREDENTIALS() VALUES()")
    @Options(useGeneratedKeys = true)
    Integer createCredentials(CredentialModel credentialModel);


    //Read
    //@Select()

    //UPDATE
    //@Update()

    //DELETE

    //@Delete("DELETE FROM CREDENTIALS WHERE")

}
