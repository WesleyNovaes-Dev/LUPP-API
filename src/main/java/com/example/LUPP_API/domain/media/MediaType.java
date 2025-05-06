package com.example.LUPP_API.domain.media;

public enum MediaType {
    POST,
    QUESTION;

    // Se for necessário, você pode adicionar um método para fazer a conversão
    public static MediaType fromString(String type) {
        return MediaType.valueOf(type.toUpperCase());
    }
}
