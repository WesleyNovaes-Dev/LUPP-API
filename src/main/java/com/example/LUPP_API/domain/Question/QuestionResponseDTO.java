package com.example.LUPP_API.domain.Question;

import com.example.LUPP_API.domain.alternative.AlternativeResponseDTO;
import lombok.*;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionResponseDTO {
    private UUID id;
    private String description;
    private Double value;
    private List<AlternativeResponseDTO> alternatives;
}