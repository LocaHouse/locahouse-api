package br.com.locahouse.service;

import br.com.locahouse.model.Usuario;

public interface UsuarioService extends CrudService<Usuario, Integer> {

    String fazerLogin(String email, String senha);

    void atualizarSenha(Integer id, String senhaAtual, String novaSenha);
}
