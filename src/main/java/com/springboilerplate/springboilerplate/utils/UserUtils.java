package com.springboilerplate.springboilerplate.utils;

import com.springboilerplate.springboilerplate.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
public class UserUtils {

    public static User getLoggedInUser(){
        Authentication authentication =	SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() ||authentication.getPrincipal().equals("anonymousUser")) {
            return null;
        }
        return ((User)authentication.getPrincipal());
    }
}