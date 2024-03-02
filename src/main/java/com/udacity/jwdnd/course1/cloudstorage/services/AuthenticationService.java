package com.udacity.jwdnd.course1.cloudstorage.services;





import com.udacity.jwdnd.course1.cloudstorage.models.UserModel;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class AuthenticationService implements AuthenticationProvider {

    private final UserService userService;
    private final HashService hashService;


    public AuthenticationService(UserService userService, HashService hashService) {
        this.userService = userService;
        this.hashService = hashService;
    }






    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("Authentication wird ausgeführt");
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        //System.out.println("Debugging Username" + username + " PAsswort" + password);

        UserModel user = userService.getUser(username);

        if ( user != null){
            String saltString = user.getSalt();
            String hashedPassword = hashService.getHashedValue(password, saltString);
            if (user.getPassword().equals(hashedPassword)){
                System.out.println("User wurde authentifziert");
                return new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
            }
            else throw new AuthenticationCredentialsNotFoundException("Falsches Passwort");
        }
        else throw new AuthenticationCredentialsNotFoundException("User existiert nicht!");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}






