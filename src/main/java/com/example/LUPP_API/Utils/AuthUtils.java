package com.example.LUPP_API.Utils;

import com.example.LUPP_API.domain.user.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AuthUtils {

    public static User getAuthenticatedUser() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof User)) {
            return null;
        }
        return (User) auth.getPrincipal();
    }

    public static UUID getAuthenticatedUserId() {
        var user = getAuthenticatedUser();
        return user != null ? user.getId() : null;
    }
}
