package com.example.LUPP_API.domain.user;

import java.util.UUID;

public record UserDTO(UUID id, String login, String name, UserRole role, int points) {
    public static UserDTO from(User user) {
        return new UserDTO(user.getId(), user.getLogin(), user.getName(), user.getRole(), user.getPoints());
    }
}
