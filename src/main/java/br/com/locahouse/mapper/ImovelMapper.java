package br.com.locahouse.mapper;

import br.com.locahouse.dto.imovel.ImovelPostDto;
import br.com.locahouse.enums.StatusImovelEnum;
import br.com.locahouse.model.Cep;
import br.com.locahouse.model.Imovel;

public final class ImovelMapper {

    private ImovelMapper() {
    }

    public static Imovel imovelPostDtoToEntity(ImovelPostDto dto) {
        Cep cep = new Cep();
        cep.setNumero(dto.numeroCep());

        Imovel imovel = new Imovel();
        imovel.setStatus(StatusImovelEnum.bucarEnumPeloCodigo(dto.status()));
        imovel.setDescricao(dto.descricao());
        imovel.setNumero(dto.numero());
        imovel.setComplemento(dto.complemento());
        imovel.setValor(dto.valor());
        imovel.setTamanho(dto.tamanho());
        imovel.setCep(cep);
        return imovel;
    }
}
