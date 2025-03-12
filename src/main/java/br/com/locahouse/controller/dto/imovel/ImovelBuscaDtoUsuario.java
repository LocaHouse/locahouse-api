package br.com.locahouse.controller.dto.imovel;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ImovelBuscaDtoUsuario(

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
