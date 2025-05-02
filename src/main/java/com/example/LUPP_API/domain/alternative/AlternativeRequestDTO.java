package com.example.LUPP_API.domain.alternative;


import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlternativeRequestDTO {
    @NotBlank
    private String text;

    @NotNull
    private Boolean correct;
}