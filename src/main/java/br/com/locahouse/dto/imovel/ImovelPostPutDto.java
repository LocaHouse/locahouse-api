package br.com.locahouse.dto.imovel;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ImovelPostPutDto(

        @NotNull
        @Min(value = 0, message = MENSAGEM_STATUS_INVALIDO)
        @Max(value = 2, message = MENSAGEM_STATUS_INVALIDO)
        @JsonProperty("status")
        Integer status,

        @NotBlank(message = "A descrição é obrigatória.")
        @Size(message = "A descrição pode ter no máximo 500 caracteres.", max = 500)
        @JsonProperty("descricao")
        String descricao,

        @NotBlank(message = "O número é obrigatório.")
        @Size(message = "O número pode ter no máximo 5 caracteres.", max = 5)
        @JsonProperty("numero")
        String numero,

        @NotBlank(message = "O complemento é obrigatório.")
        @Size(message = "O complemento pode ter no máximo 255 caracteres.", max = 255)
        @JsonProperty("complemento")
        String complemento,

        @NotNull
        @PositiveOrZero(message = "O valor não pode ser negativo.")
        @JsonProperty("valor")
        BigDecimal valor,

        @NotNull
        @PositiveOrZero(message = "O tamanho não pode ser negativo.")
        @JsonProperty("tamanho")
        BigDecimal tamanho,

        @NotNull
        @Size(message = "O CEP deve ter 9 caracteres.", min = 9, max = 9)
        @JsonProperty("numero_cep")
        String numeroCep
) {

    private static final String MENSAGEM_STATUS_INVALIDO = "O status deve ser 0, 1 ou 2.";
}