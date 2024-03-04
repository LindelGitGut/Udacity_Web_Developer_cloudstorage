package com.udacity.jwdnd.course1.cloudstorage.services;


import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.UserModel;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.NoSuchElementException;

@Service
public class UserService {


    UserMapper userMapper;
    HashService hashService;



    public UserService(UserMapper userMapper, HashService hashService ) {
        this.userMapper = userMapper;
        this.hashService = hashService;
    }


    public Integer createUser(UserModel user) {
        SecureRandom random = new SecureRandom();
        String clearPassword = user.getPassword();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String saltString = Base64.getEncoder().encodeToString(salt);
        String hashedpassword = hashService.getHashedValue(clearPassword, saltString);
        return userMapper.insertUser(new UserModel(user.getUsername(), saltString, hashedpassword, user.getFirstname(), user.getLastname()));
    }

    public boolean isUserAvailable(String user) {
        return this.getUser(user) == null;
    }

    public UserModel getUser(String username) {
        return userMapper.getUser(username);
    }

    //returns current Logged In User


    public UserModel getCurrentUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();



        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            if (username != null) {
                UserModel currentUser = userMapper.getUser(username);
                if (currentUser != null) {
                    return currentUser;
                } else throw new NoSuchElementException("Error getting user from Database");
            } else throw new NoSuchElementException("error getting current Username from authentication");
        } else throw new AuthenticationCredentialsNotFoundException("no logged in User found");

    }

    public void deleteUser(String username) {
        userMapper.deleteUser(username);
    }

    public void changeUser(UserModel user) {
        userMapper.updateUser(user);
    }

}
