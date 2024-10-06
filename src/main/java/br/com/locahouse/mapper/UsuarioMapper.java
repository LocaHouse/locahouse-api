package br.com.locahouse.mapper;

import br.com.locahouse.dto.usuario.UsuarioGetDto;
import br.com.locahouse.dto.usuario.UsuarioPostDto;
import br.com.locahouse.dto.usuario.UsuarioPutDto;
import br.com.locahouse.model.Usuario;

import java.util.Collections;
import java.util.Optional;

public final class UsuarioMapper {

    private UsuarioMapper() {
    }

    public static Usuario usuarioPostDtoToEntity(UsuarioPostDto dto) {
        Usuario usuario = new Usuario();
        usuario.setCpf(dto.cpf());
        usuario.setSenha(dto.senha());
        usuario.setNome(dto.nome());
        usuario.setDataNascimento(dto.dataNascimento());
        usuario.setEmail(dto.email());
        usuario.setTelefone(dto.telefone());
        return usuario;
    }

    public static Usuario usuarioPutDtoToEntity(UsuarioPutDto dto) {
        Usuario usuario = new Usuario();
        usuario.setId(dto.id());
        usuario.setCpf(dto.cpf());
        usuario.setNome(dto.nome());
        usuario.setDataNascimento(dto.dataNascimento());
        usuario.setEmail(dto.email());
        usuario.setTelefone(dto.telefone());
        return usuario;
    }

    public static UsuarioGetDto entityToUsuarioGetDto(Usuario usuario) {
        return new UsuarioGetDto(
                usuario.getId(),
                usuario.getCpf(),
                usuario.getNome(),
                usuario.getDataNascimento(),
                usuario.getEmail(),
                usuario.getTelefone(),
                Optional.ofNullable(usuario.getImoveis()).orElse(Collections.emptyList())
        );
    }
}
