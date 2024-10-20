package br.com.locahouse.service;

import br.com.locahouse.model.Usuario;

public interface UsuarioService {

    Usuario cadastrar(Usuario usuario);

    String fazerLogin(String email, String senha);

    Usuario buscarPeloId(Integer id);

    Usuario atualizar(Integer id, Usuario usuario);

    void atualizarSenha(Integer id, String senhaAtual, String novaSenha);

    void deletar(Integer id);
}
