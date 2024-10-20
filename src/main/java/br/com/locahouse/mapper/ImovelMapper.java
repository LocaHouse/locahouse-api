package br.com.locahouse.mapper;

import br.com.locahouse.dto.imovel.ImovelGetDto;
import br.com.locahouse.dto.imovel.ImovelGetUsuarioDto;
import br.com.locahouse.dto.imovel.ImovelPostPutDto;
import br.com.locahouse.enums.StatusImovelEnum;
import br.com.locahouse.model.Imovel;

public final class ImovelMapper {

    private ImovelMapper() {
    }

    public static Imovel imovelPostDtoToEntity(ImovelPostPutDto dto) {
        Imovel imovel = new Imovel();
        imovel.setStatus(StatusImovelEnum.bucarEnumPeloCodigo(dto.status()));
        imovel.setDescricao(dto.descricao());
        imovel.setNumero(dto.numero());
        imovel.setComplemento(dto.complemento());
        imovel.setValor(dto.valor());
        imovel.setTamanho(dto.tamanho());
        return imovel;
    }

    public static ImovelGetDto entityToImovelGetDto(Imovel imovel) {
        return new ImovelGetDto(
                imovel.getId(),
                imovel.getStatus(),
                imovel.getDescricao(),
                imovel.getNumero(),
                imovel.getComplemento(),
                imovel.getValor(),
                imovel.getTamanho(),
                new ImovelGetUsuarioDto(
                        imovel.getUsuario().getId(),
                        imovel.getUsuario().getNome(),
                        imovel.getUsuario().getTelefone(),
                        imovel.getUsuario().getEmail()
                ),
                imovel.getCep()
        );
    }
}
