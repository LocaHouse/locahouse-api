package br.com.locahouse.controller.dto.usuario;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.List;

public record UsuarioBuscaDto(

        @JsonProperty("id")
        Integer id,

        @JsonProperty("cpf")
        String cpf,

        @JsonProperty("nome")
        String nome,

        @JsonProperty("data_nascimento")
        LocalDate dataNascimento,

        @JsonProperty("telefone")
        String telefone,

        @JsonProperty("email")
        String email,

        @JsonProperty("imoveis")
        List<UsuarioBuscaDtoImovel> imoveis
) {
}
