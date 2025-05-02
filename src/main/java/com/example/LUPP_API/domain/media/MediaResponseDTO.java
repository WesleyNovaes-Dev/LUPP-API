package com.example.LUPP_API.domain.media;


import com.example.LUPP_API.domain.Question.QuestionResponseDTO;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MediaResponseDTO {
    private UUID id;
    private String title;
    private String description;
    private String category;
    private String mediaUrl;
    private LocalDateTime creationDate;
    private MediaType type;
    private List<QuestionResponseDTO> questions;
}