package br.com.locahouse.mapper;

import br.com.locahouse.dto.imovel.ImovelPostPutComodoDoImovelDto;
import br.com.locahouse.model.Comodo;
import br.com.locahouse.model.ComodoDoImovel;

public class ComodoDoImovelMapper {

    public static ComodoDoImovel imovelPostPutComodoDoImovelDtoToEntity(ImovelPostPutComodoDoImovelDto dto) {
        ComodoDoImovel comodo = new ComodoDoImovel();
        comodo.setQuantidade(dto.quantidade());
        comodo.setComodo(new Comodo().setId(dto.comodoId()));
        return comodo;
    }
}
