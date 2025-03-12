package br.com.locahouse.mapper;

import br.com.locahouse.dto.imovel.ImovelBuscaDto;
import br.com.locahouse.dto.imovel.ImovelBuscaDtoUsuario;
import br.com.locahouse.dto.imovel.ImovelCadastroAndAtualizacaoDto;
import br.com.locahouse.model.enums.StatusImovelEnum;
import br.com.locahouse.model.Imovel;

import java.util.Collections;
import java.util.Optional;

public final class ImovelMapper {

    private ImovelMapper() {
    }

    public static Imovel imovelCadastroAndAtualizacaoDtoToEntity(ImovelCadastroAndAtualizacaoDto dto) {
        Imovel imovel = new Imovel();
        imovel.setStatus(StatusImovelEnum.bucarEnumPeloCodigo(dto.status()));
        imovel.setDescricao(dto.descricao());
        imovel.setNumero(dto.numero());
        imovel.setComplemento(dto.complemento());
        imovel.setValor(dto.valor());
        imovel.setTamanho(dto.tamanho());
        return imovel;
    }

    public static ImovelBuscaDto entityToImovelBuscaDto(Imovel imovel) {
        return new ImovelBuscaDto(
                imovel.getId(),
                imovel.getStatus(),
                imovel.getDescricao(),
                imovel.getNumero(),
                imovel.getComplemento(),
                imovel.getValor(),
                imovel.getTamanho(),
                new ImovelBuscaDtoUsuario(
                        imovel.getUsuario().getId(),
                        imovel.getUsuario().getNome(),
                        imovel.getUsuario().getTelefone(),
                        imovel.getUsuario().getEmail()
                ),
                imovel.getCep(),
                Optional.ofNullable(imovel.getComodos()).orElse(Collections.emptyList()).stream().map(ComodoDoImovelMapper::entityToComodoDoImovelBuscaDto).toList()
        );
    }
}
