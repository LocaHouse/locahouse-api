package br.com.locahouse.service.impl;

import br.com.locahouse.exception.BusinessException;
import br.com.locahouse.exception.RecursoNaoEcontradoException;
import br.com.locahouse.exception.UsuarioMenorDeIdadeException;
import br.com.locahouse.exception.UniqueConstraintVioladaException;
import br.com.locahouse.model.Usuario;
import br.com.locahouse.repository.UsuarioRepository;
import br.com.locahouse.security.authentication.JwtTokenService;
import br.com.locahouse.security.config.SecurityConfiguration;
import br.com.locahouse.security.userdetails.UserDetailsImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements br.com.locahouse.service.UsuarioService {

    private final UsuarioRepository repository;

    private final AuthenticationManager authenticationManager;

    private final JwtTokenService jwtTokenService;

    private final SecurityConfiguration securityConfiguration;

    @Autowired
    private UsuarioServiceImpl(UsuarioRepository repository, AuthenticationManager authenticationManager, JwtTokenService jwtTokenService, SecurityConfiguration securityConfiguration) {
        this.repository = repository;
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
        this.securityConfiguration = securityConfiguration;
    }

    public String autenticarUsuario(String email, String senha) {
        // Cria um objeto de autenticação com o email e a senha do usuário
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(email, senha);

        // Autentica o usuário com as credenciais fornecidas
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        // Obtém o objeto UserDetails do usuário autenticado
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // Retorna um token JWT para o usuário autenticado
        return jwtTokenService.generateToken(userDetails);
    }

    @Override
    public Usuario cadastrar(Usuario usuario) {
        usuario.setSenha(securityConfiguration.passwordEncoder().encode(usuario.getSenha()));
        this.salvar(usuario);
        return usuario;
    }

    @Override
    public Usuario buscarPeloId(Integer id) {
        return this.repository.findById(id).orElseThrow(() -> new RecursoNaoEcontradoException("Usuário"));
    }

    @Override
    public Usuario buscarPeloEmail(String email) {
        return this.repository.findByEmail(email).orElseThrow(() -> new RecursoNaoEcontradoException("Usuário"));
    }

    @Override
    public Usuario atualizar(Integer id, Usuario usuario) {
        if (!id.equals(usuario.getId()))
            throw new BusinessException("Os identificadores devem ser iguais.", HttpStatus.CONFLICT);
        Usuario usuarioParaAtualizar = this.buscarPeloId(id);
        BeanUtils.copyProperties(usuario, usuarioParaAtualizar, "id", "senha");
        this.salvar(usuarioParaAtualizar);
        return usuarioParaAtualizar;
    }

    @Override
    public void deletar(Integer id) {
        this.repository.delete(this.buscarPeloId(id));
    }

    private void salvar(Usuario usuario) {
        this.verificarUnicidadeCpf(usuario.getId(), usuario.getCpf());
        this.verificarUnicidadeEmail(usuario.getId(), usuario.getEmail());
        this.verificarUnicidadeTelefone(usuario.getId(), usuario.getTelefone());
        this.verificarMaioridade(usuario.getDataNascimento());
        this.repository.save(usuario);
    }

    private void verificarUnicidadeCpf(Integer id, String cpf) {
        Optional<Usuario> usuario = this.repository.findByCpf(cpf);
        if (usuario.isPresent() && (id == null || !id.equals(usuario.get().getId())))
            throw new UniqueConstraintVioladaException("CPF");
    }

    private void verificarUnicidadeEmail(Integer id, String email) {
        Optional<Usuario> usuario = this.repository.findByEmail(email);
        if (usuario.isPresent() && (id == null || !id.equals(usuario.get().getId())))
            throw new UniqueConstraintVioladaException("E-mail");
    }

    private void verificarUnicidadeTelefone(Integer id, String telefone) {
        Optional<Usuario> usuario = this.repository.findByTelefone(telefone);
        if (usuario.isPresent() && (id == null || !id.equals(usuario.get().getId())))
            throw new UniqueConstraintVioladaException("Telefone");
    }

    private void verificarMaioridade(LocalDate dataNascimento) {
        if (Period.between(dataNascimento, LocalDate.now()).getYears() < 18)
            throw new UsuarioMenorDeIdadeException();
    }
}
