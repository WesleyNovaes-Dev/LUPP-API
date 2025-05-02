package com.example.LUPP_API.domain.media;


import com.example.LUPP_API.domain.Question.QuestionRequestDTO;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MediaRequestDTO {
    @NotBlank
    private String title;

    private String description;

    @NotBlank
    private String category;

    private String mediaUrl; // será preenchido após upload

    @NotNull
    private MediaType type; // Apenas POST ou QUESTION

    private List<QuestionRequestDTO> questions;
}
