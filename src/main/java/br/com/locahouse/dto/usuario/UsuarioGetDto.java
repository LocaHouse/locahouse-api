package br.com.locahouse.dto.usuario;

import br.com.locahouse.model.Imovel;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.List;

public record UsuarioGetDto(

        Integer id,

        String cpf,

        String nome,

        @JsonProperty("data_nascimento")
        LocalDate dataNascimento,

        String telefone,

        String email,

        List<Imovel> imoveis
) {
}
