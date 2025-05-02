package com.example.LUPP_API.domain.alternative;

import lombok.*;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlternativeResponseDTO {
    private UUID id;
    private String text;
    private Boolean correct;
}