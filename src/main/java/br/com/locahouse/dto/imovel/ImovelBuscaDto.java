package br.com.locahouse.dto.imovel;

import br.com.locahouse.dto.comododoimovel.ComodoDoImovelBuscaDto;
import br.com.locahouse.model.Cep;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

public record ImovelBuscaDto(

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
        ImovelBuscaDtoUsuario usuario,

        @JsonProperty("cep")
        Cep cep,

        @JsonProperty("comodos")
        List<ComodoDoImovelBuscaDto> comodos
) {
}
