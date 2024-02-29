package com.udacity.jwdnd.course1.cloudstorage.services;


import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.UserModel;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {

    UserMapper userMapper;
    HashService hashService;
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }


    public void createUser(UserModel user){
        SecureRandom random = new SecureRandom();
        String clearPassword = user.getPassword();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String saltString = Base64.getEncoder().encodeToString(salt);
        String hashedpassword = hashService.getHashedValue(clearPassword, saltString);
        userMapper.insertUser(new UserModel(user.getUsername(),saltString,hashedpassword,user.getFirstname(),user.getLastname()));
    }

    public boolean isUserAvailable(String user){
       return this.getUser(user) == null;
    }

    public UserModel getUser(String username){
        return userMapper.getUser(username);
    }

    public void deleteUser(String username){
        userMapper.deleteUser(username);
    }
}
