package br.com.locahouse.controller.dto.imagemdoimovel;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record ImagemDoImovelCadastroAndAtualizacaoDto(

        @NotNull(message = "A sequência é obrigatória.")
        @PositiveOrZero(message = "A sequência não pode ser negativa.")
        @JsonProperty("sequencia")
        Integer sequencia,

        @NotBlank(message = "A descrição é obrigatória.")
        @JsonProperty("descricao")
        String descricao,

        @NotBlank(message = "O caminho é obrigatório.")
        @JsonProperty("caminho")
        String caminho
) {
}
