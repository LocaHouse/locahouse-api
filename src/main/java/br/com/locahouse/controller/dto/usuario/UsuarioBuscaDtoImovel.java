package br.com.locahouse.controller.dto.usuario;

import br.com.locahouse.controller.dto.comododoimovel.ComodoDoImovelBuscaDto;
import br.com.locahouse.model.Cep;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

public record UsuarioBuscaDtoImovel(

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

        @JsonProperty("cep")
        Cep cep,

        @JsonProperty("comodos")
        List<ComodoDoImovelBuscaDto> comodos
) {
}
