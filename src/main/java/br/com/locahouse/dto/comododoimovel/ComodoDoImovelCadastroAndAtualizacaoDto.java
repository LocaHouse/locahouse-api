package br.com.locahouse.dto.comododoimovel;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ComodoDoImovelCadastroAndAtualizacaoDto(

        @NotNull(message = "A quantidade é obrigatória.")
        @Positive(message = "A quantidade deve ser positiva.")
        @JsonProperty("quantidade")
        Integer quantidade,

        @NotNull(message = "O id do comodo é obrigatório.")
        @Positive(message = "O id do comodo deve ser positivo.")
        @JsonProperty("comodo_id")
        Integer comodoId
) {
}
