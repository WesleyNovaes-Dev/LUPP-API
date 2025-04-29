package com.example.LUPP_API.domain.user;

public record RegisterDTO(String login, String password, UserRole role) {
}
