package br.com.locahouse.mapper;

import br.com.locahouse.dto.comododoimovel.ComodoDoImovelBuscaDto;
import br.com.locahouse.dto.comododoimovel.ComodoDoImovelCadastroAndAtualizacaoDto;
import br.com.locahouse.model.Comodo;
import br.com.locahouse.model.ComodoDoImovel;

public class ComodoDoImovelMapper {

    public static ComodoDoImovel comodoDoImovelCadastroAndAtualizacaoDtoDtoToEntity(ComodoDoImovelCadastroAndAtualizacaoDto dto) {
        ComodoDoImovel comodoDoImovel = new ComodoDoImovel();
        comodoDoImovel.setQuantidade(dto.quantidade());
        comodoDoImovel.setComodo(new Comodo());
        comodoDoImovel.getComodo().setId(dto.comodoId());
        return comodoDoImovel;
    }

    public static ComodoDoImovelBuscaDto entityToComodoDoImovelBuscaDto(ComodoDoImovel comodoDoImovel) {
        return new ComodoDoImovelBuscaDto(comodoDoImovel.getId(), comodoDoImovel.getQuantidade(), comodoDoImovel.getComodo());
    }
}
