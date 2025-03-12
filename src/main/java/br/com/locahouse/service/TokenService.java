package br.com.locahouse.service;

import br.com.locahouse.security.userdetails.UserDetailsImpl;

public interface TokenService {

    String gerarToken(UserDetailsImpl user);

    String buscarSubject(String token);
}
