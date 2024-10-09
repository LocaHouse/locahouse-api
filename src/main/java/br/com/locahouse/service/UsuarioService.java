package br.com.locahouse.service;

import br.com.locahouse.model.Usuario;

public interface UsuarioService extends CrudService<Usuario, Integer> {

    String autenticarUsuario(String email, String senha);

    Usuario buscarPeloEmail(String email);
}
