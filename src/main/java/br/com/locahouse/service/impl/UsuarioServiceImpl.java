package br.com.locahouse.service.impl;

import br.com.locahouse.exception.UniqueConstraintVioladaException;
import br.com.locahouse.model.Usuario;
import br.com.locahouse.repository.UsuarioRepository;
import br.com.locahouse.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository repository;

    @Autowired
    public UsuarioServiceImpl(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public Usuario cadastrar(Usuario usuario) {
        this.verificarUnicidadeDados(usuario);
        this.verificarMaioridade(usuario.getDataNascimento());
        this.salvar(usuario);
        return usuario;
    }

    @Override
    public List<Usuario> buscarTodos() {
        // TODO criar lógica de busca
        return List.of();
    }

    @Override
    public Usuario buscarPeloId(Integer id) {
        // TODO criar lógica de busca
        return null;
    }

    @Override
    public Usuario atualizar(Integer id, Usuario usuario) {
        // TODO criar lógica de atualização
        return null;
    }

    @Override
    public void deletar(Integer id) {
        // TODO criar lógica de deleção
    }

    private void verificarUnicidadeDados(Usuario usuario) {
        if (this.buscarPeloCpf(usuario.getCpf()).isPresent())
            throw new UniqueConstraintVioladaException("CPF");

        if (this.buscarPeloEmail(usuario.getEmail()).isPresent())
            throw new UniqueConstraintVioladaException("E-mail");

        if (this.buscarPeloTelefone(usuario.getTelefone()).isPresent())
            throw new UniqueConstraintVioladaException("Telefone");
    }

    private Optional<Usuario> buscarPeloCpf(String cpf) {
        return this.repository.findByCpf(cpf);
    }

    private Optional<Usuario> buscarPeloEmail(String email) {
        return this.repository.findByEmail(email);
    }

    private Optional<Usuario> buscarPeloTelefone(String telefone) {
        return this.repository.findByTelefone(telefone);
    }

    private void verificarMaioridade(LocalDate dataNascimento) {
        if (Period.between(dataNascimento, LocalDate.now()).getYears() < 18)
            throw new RuntimeException("É necessário ser maior de 18 anos para prosseguir.");
    }

    private void salvar(Usuario usuario) {
        this.repository.save(usuario);
    }
}
