package br.com.locahouse.dto.imovel;

import br.com.locahouse.model.Cep;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record ImovelGetDto(

        @JsonProperty("id")
        Integer id,

        @JsonProperty("status")
        Integer status,

        @JsonProperty("descricao")
        String descricao,

        @JsonProperty("numero")
        String numero,

        @JsonProperty("complemento")
        String complemento,

        @JsonProperty("valor")
        BigDecimal valor,

        @JsonProperty("tamanho")
        BigDecimal tamanho,

        @JsonProperty("usuario")
        ImovelGetUsuarioDto usuario,

        @JsonProperty("cep")
        Cep cep
) {
}
