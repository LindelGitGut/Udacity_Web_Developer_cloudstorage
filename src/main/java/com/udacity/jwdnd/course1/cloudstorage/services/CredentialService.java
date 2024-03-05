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
        credentialModel.setKey(encryptionService.generateKey());
        credentialModel.setUserid(userService.getCurrentUser().getUserid());
        credentialModel.setPassword(encryptionService.encryptValue(credentialModel.getPassword(),credentialModel.getKey()));
        return credentialsMapper.createCredential(credentialModel);
    }

    public CredentialModel getCredential(String credentialid){
        String userid = userService.getCurrentUser().getUserid().toString();
        CredentialModel cryptCred = credentialsMapper.getCredential(credentialid,userid);
        cryptCred.setPassword(encryptionService.decryptValue(cryptCred.getPassword(),cryptCred.getKey()));
        return cryptCred;
    }

    public List<CredentialModel> getAllUserCredentials(){

        String userid = userService.getCurrentUser().getUserid().toString();
        List<CredentialModel> allCreds = credentialsMapper.getallUserCredentials(userid);

        for (CredentialModel currentcred: allCreds
             ) {
            currentcred.setPassword(encryptionService.decryptValue(currentcred.getPassword(),currentcred.getKey()));
        }

        return allCreds;
    }

    public Integer changeCredential(CredentialModel credentialModel){
        credentialModel.setKey(encryptionService.generateKey());
        credentialModel.setPassword(encryptionService.encryptValue(credentialModel.getPassword(),credentialModel.getKey()));
        credentialModel.setUserid(userService.getCurrentUser().getUserid());
        return credentialsMapper.changeCredential(credentialModel);
    }

    public Integer deleteCredential(String credentialid){
        String userid = userService.getCurrentUser().getUserid().toString();
        return credentialsMapper.deleteCredential(userid, credentialid);
    }

}
