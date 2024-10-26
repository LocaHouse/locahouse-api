package br.com.locahouse.dto.comododoimovel;

import br.com.locahouse.model.Comodo;
import com.fasterxml.jackson.annotation.JsonProperty;

public record ComodoDoImovelBuscaDto(

        @JsonProperty("id")
        Integer id,

        @JsonProperty("quantidade")
        Integer quantidade,

        @JsonProperty("comodo")
        Comodo comodo
) {
}
