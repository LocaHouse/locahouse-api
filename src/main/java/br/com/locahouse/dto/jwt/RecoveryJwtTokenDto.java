package br.com.locahouse.dto.jwt;

import jakarta.validation.constraints.NotBlank;

public record RecoveryJwtTokenDto(

        @NotBlank
        String token
) {
}
