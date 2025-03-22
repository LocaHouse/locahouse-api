package br.com.locahouse.controller.dto.comododoimovel;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record ComodoDoImovelCadastroAndAtualizacaoDto(

        @NotNull(message = "A quantidade é obrigatória.")
        @PositiveOrZero(message = "Quantidade inválida.")
        @JsonProperty("quantidade")
        Integer quantidade,

        @NotNull(message = "O id do comodo é obrigatório.")
        @Positive(message = "O id do comodo deve ser positivo.")
        @JsonProperty("comodo_id")
        Integer comodoId
) {
}
