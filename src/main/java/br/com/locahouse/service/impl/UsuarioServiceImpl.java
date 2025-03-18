package br.com.locahouse.service.impl;

import br.com.locahouse.exception.BusinessException;
import br.com.locahouse.exception.RecursoNaoEcontradoException;
import br.com.locahouse.exception.UniqueConstraintVioladaException;
import br.com.locahouse.model.Usuario;
import br.com.locahouse.repository.UsuarioRepository;
import br.com.locahouse.security.config.SecurityConfiguration;
import br.com.locahouse.security.userdetails.UserDetailsImpl;
import br.com.locahouse.service.TokenService;
import br.com.locahouse.service.UsuarioService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

@Service
public final class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository repository;

    private final AuthenticationManager authenticationManager;

    private final TokenService tokenService;

    private final SecurityConfiguration securityConfiguration;

    @Autowired
    public UsuarioServiceImpl(UsuarioRepository repository, AuthenticationManager authenticationManager, TokenService tokenService, SecurityConfiguration securityConfiguration) {
        this.repository = repository;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.securityConfiguration = securityConfiguration;
    }

    @Override
    public Usuario cadastrar(Usuario usuario) {
        usuario.setSenha(securityConfiguration.passwordEncoder().encode(usuario.getSenha()));
        this.salvar(usuario);
        return usuario;
    }

    @Override
    public String fazerLogin(String email, String senha) {
        Usuario usuario = this.repository.findByEmail(email).orElseThrow(() -> new BadCredentialsException("Usuário inexistente ou senha inválida"));

        // Cria um objeto de autenticação com o id e a senha do usuário
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(String.valueOf(usuario.getId()), senha);

        // Autentica o usuário com as credenciais fornecidas
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        // Obtém o objeto UserDetails do usuário autenticado
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // Retorna um token JWT para o usuário autenticado
        return tokenService.gerarToken(userDetails);
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
        Usuario usuarioParaAtualizar = this.buscarPeloId(id);
        BeanUtils.copyProperties(usuario, usuarioParaAtualizar, "id", "senha");
        this.salvar(usuarioParaAtualizar);
        return usuarioParaAtualizar;
    }

    @Override
    public void atualizarSenha(Integer id, String senhaAtual, String novaSenha) {
        Usuario usuario = this.buscarPeloId(id);
        if (!securityConfiguration.passwordEncoder().matches(senhaAtual, usuario.getSenha()))
            throw new BusinessException("Senha atual incorreta.", HttpStatus.UNAUTHORIZED);
        usuario.setSenha(securityConfiguration.passwordEncoder().encode(novaSenha));
        this.salvar(usuario);
    }

    @Override
    public void deletar(Integer id) {
        this.repository.delete(this.buscarPeloId(id));
    }

    private void salvar(Usuario usuario) {
        this.verificarUnicidadeCpf(usuario.getId(), usuario.getCpf());
        this.verificarUnicidadeTelefone(usuario.getId(), usuario.getTelefone());
        this.verificarUnicidadeEmail(usuario.getId(), usuario.getEmail());
        this.verificarMaioridade(usuario.getDataNascimento());
        this.repository.save(usuario);
    }

    private void verificarUnicidadeCpf(Integer id, String cpf) {
        Optional<Usuario> usuario = this.repository.findByCpf(cpf);
        if (usuario.isPresent() && (id == null || !id.equals(usuario.get().getId())))
            throw new UniqueConstraintVioladaException("CPF");
    }

    private void verificarUnicidadeTelefone(Integer id, String telefone) {
        Optional<Usuario> usuario = this.repository.findByTelefone(telefone);
        if (usuario.isPresent() && (id == null || !id.equals(usuario.get().getId())))
            throw new UniqueConstraintVioladaException("Telefone");
    }

    private void verificarUnicidadeEmail(Integer id, String email) {
        Optional<Usuario> usuario = this.repository.findByEmail(email);
        if (usuario.isPresent() && (id == null || !id.equals(usuario.get().getId())))
            throw new UniqueConstraintVioladaException("E-mail");
    }

    private void verificarMaioridade(LocalDate dataNascimento) {
        if (Period.between(dataNascimento, LocalDate.now()).getYears() < 18)
            throw new BusinessException("É necessário ser maior de 18 anos para prosseguir.", HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
