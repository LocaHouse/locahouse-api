package br.com.locahouse.dto.imovel;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ImovelGetUsuarioDto(

        @JsonProperty("id")
        Integer id,

        @JsonProperty("nome")
        String nome,

        @JsonProperty("telefone")
        String telefone,

        @JsonProperty("email")
        String email
) {
}
