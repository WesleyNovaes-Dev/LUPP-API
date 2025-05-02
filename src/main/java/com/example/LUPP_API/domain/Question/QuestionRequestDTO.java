package com.example.LUPP_API.domain.Question;

import com.example.LUPP_API.domain.alternative.AlternativeRequestDTO;
import jakarta.validation.constraints.*;
import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionRequestDTO {
    @NotBlank
    private String description;

    @NotNull
    private Double value;

    @NotEmpty
    private List<AlternativeRequestDTO> alternatives;
}