package br.com.locahouse.security.userdetails;

import br.com.locahouse.exception.BusinessException;
import br.com.locahouse.model.Usuario;
import br.com.locahouse.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final UsuarioRepository repository;

    @Autowired
    private UserDetailServiceImpl(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = this.repository.findByEmail(username).orElseThrow(
                () -> new BusinessException("E-mail e/ou senha inválidos.", HttpStatus.UNAUTHORIZED)
        );
        return new UserDetailsImpl(usuario);
    }
}
