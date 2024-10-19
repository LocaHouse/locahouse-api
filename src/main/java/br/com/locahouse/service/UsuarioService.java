package br.com.locahouse.service;

import br.com.locahouse.model.Usuario;

public interface UsuarioService extends CrudService<Usuario, Integer> {

    Usuario cadastrar(Usuario usuario);

    String fazerLogin(String email, String senha);

    Usuario atualizar(Integer id, Usuario usuario);

    void atualizarSenha(Integer id, String senhaAtual, String novaSenha);
}
