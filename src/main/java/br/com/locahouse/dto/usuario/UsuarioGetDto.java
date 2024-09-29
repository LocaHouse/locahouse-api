package br.com.locahouse.dto.usuario;

import br.com.locahouse.model.Imovel;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.List;

public record UsuarioGetDto(

        @JsonProperty("id")
        Integer id,

        @JsonProperty("cpf")
        String cpf,

        @JsonProperty("nome")
        String nome,

        @JsonProperty("data_nascimento")
        LocalDate dataNascimento,

        @JsonProperty("email")
        String email,

        @JsonProperty("telefone")
        String telefone,

        @JsonProperty("imoveis")
        List<Imovel> imoveis
) {
}
