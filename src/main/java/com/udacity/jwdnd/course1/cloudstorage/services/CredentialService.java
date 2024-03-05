package com.udacity.jwdnd.course1.cloudstorage.services;


import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.CredentialModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {

    UserService userService;
    EncryptionService encryptionService;
    CredentialsMapper credentialsMapper;

    public CredentialService(UserService userService, EncryptionService encryptionService, CredentialsMapper credentialsMapper) {
        this.userService = userService;
        this.encryptionService = encryptionService;
        this.credentialsMapper = credentialsMapper;
    }

    public Integer createCredential(CredentialModel credentialModel){
        //TODO Add Encryption
        credentialModel.setUserid(userService.getCurrentUser().getUserid());
        return credentialsMapper.createCredential(credentialModel);
    }

    public CredentialModel getCredential(String credentialid){
        String userid = userService.getCurrentUser().getUserid().toString();
        return credentialsMapper.getCredential(credentialid,userid);
    }

    public List<CredentialModel> getAllUserCredentials(){
        String userid = userService.getCurrentUser().getUserid().toString();
        return credentialsMapper.getallUserCredentials(userid);
    }

    public Integer changeCredential(CredentialModel credentialModel){
        credentialModel.setUserid(userService.getCurrentUser().getUserid());
        return credentialsMapper.changeCredential(credentialModel);
    }

    public Integer deleteCredential(String credentialid){
        String userid = userService.getCurrentUser().getUserid().toString();
        return credentialsMapper.deleteCredential(userid, credentialid);
    }

}
