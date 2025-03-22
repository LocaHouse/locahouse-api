package br.com.locahouse.mapper;

import br.com.locahouse.controller.dto.usuario.UsuarioBuscaDto;
import br.com.locahouse.controller.dto.usuario.UsuarioBuscaDtoImovel;
import br.com.locahouse.controller.dto.usuario.UsuarioCadastroDto;
import br.com.locahouse.controller.dto.usuario.UsuarioAtualizacaoDto;
import br.com.locahouse.model.Usuario;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

public final class UsuarioMapper {

    private UsuarioMapper() {
    }

    public static Usuario usuarioCadastroDtoToEntity(UsuarioCadastroDto dto) {
        Usuario usuario = new Usuario();
        usuario.setCpf(dto.cpf());
        usuario.setNome(dto.nome());
        usuario.setDataNascimento(dto.dataNascimento());
        usuario.setTelefone(dto.telefone());
        usuario.setEmail(dto.email());
        usuario.setSenha(dto.senha());
        return usuario;
    }

    public static Usuario usuarioAtualizacaoDtoToEntity(UsuarioAtualizacaoDto dto) {
        Usuario usuario = new Usuario();
        usuario.setCpf(dto.cpf());
        usuario.setNome(dto.nome());
        usuario.setDataNascimento(dto.dataNascimento());
        usuario.setTelefone(dto.telefone());
        usuario.setEmail(dto.email());
        return usuario;
    }

    public static UsuarioBuscaDto entityToUsuarioBuscaDto(Usuario usuario) {
        return new UsuarioBuscaDto(
                usuario.getId(),
                usuario.getCpf(),
                usuario.getNome(),
                usuario.getDataNascimento(),
                usuario.getTelefone(),
                usuario.getEmail(),
                Optional.ofNullable(usuario.getImoveis()).orElse(Collections.emptyList()).stream().map(
                        imovel -> new UsuarioBuscaDtoImovel(
                                imovel.getId(),
                                imovel.getStatus(),
                                imovel.getDescricao(),
                                imovel.getNumero(),
                                imovel.getComplemento(),
                                imovel.getValor(),
                                imovel.getTamanho(),
                                imovel.getCep(),
                                Optional.ofNullable(imovel.getComodos()).orElse(Collections.emptyList()).stream().map(ComodoDoImovelMapper::entityToComodoDoImovelBuscaDto).toList()
                        )
                ).collect(Collectors.toList())
        );
    }
}
