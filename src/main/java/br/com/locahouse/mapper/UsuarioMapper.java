package br.com.locahouse.mapper;

import br.com.locahouse.dto.usuario.UsuarioGetDto;
import br.com.locahouse.dto.usuario.UsuarioGetImovelDto;
import br.com.locahouse.dto.usuario.UsuarioPostDto;
import br.com.locahouse.dto.usuario.UsuarioPutDto;
import br.com.locahouse.model.Usuario;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

public final class UsuarioMapper {

    private UsuarioMapper() {
    }

    public static Usuario usuarioPostDtoToEntity(UsuarioPostDto dto) {
        Usuario usuario = new Usuario();
        usuario.setCpf(dto.cpf());
        usuario.setNome(dto.nome());
        usuario.setDataNascimento(dto.dataNascimento());
        usuario.setTelefone(dto.telefone());
        usuario.setEmail(dto.email());
        usuario.setSenha(dto.senha());
        return usuario;
    }

    public static Usuario usuarioPutDtoToEntity(UsuarioPutDto dto) {
        Usuario usuario = new Usuario();
        usuario.setCpf(dto.cpf());
        usuario.setNome(dto.nome());
        usuario.setDataNascimento(dto.dataNascimento());
        usuario.setTelefone(dto.telefone());
        usuario.setEmail(dto.email());
        return usuario;
    }

    public static UsuarioGetDto entityToUsuarioGetDto(Usuario usuario) {
        return new UsuarioGetDto(
                usuario.getId(),
                usuario.getCpf(),
                usuario.getNome(),
                usuario.getDataNascimento(),
                usuario.getTelefone(),
                usuario.getEmail(),
                Optional.ofNullable(usuario.getImoveis()).orElse(Collections.emptyList()).stream().map(
                        imovel -> new UsuarioGetImovelDto(
                                imovel.getId(),
                                imovel.getStatus(),
                                imovel.getDescricao(),
                                imovel.getNumero(),
                                imovel.getComplemento(),
                                imovel.getValor(),
                                imovel.getTamanho(),
                                imovel.getCep()
                        )
                ).collect(Collectors.toList())
        );
    }
}
