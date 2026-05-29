package com.example.collabapp.utils;

import com.example.collabapp.model.dao.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {
    public String getCurrentUserId(){
        Object principal = SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        if(principal instanceof User user){
            return user.getId();
        }
        throw new RuntimeException("No authenticated user");
    }
}
