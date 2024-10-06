package br.com.locahouse.service.impl;

import br.com.locahouse.exception.BusinessException;
import br.com.locahouse.exception.RecursoNaoEcontradoException;
import br.com.locahouse.exception.UsuarioMenorDeIdadeException;
import br.com.locahouse.exception.UniqueConstraintVioladaException;
import br.com.locahouse.model.Usuario;
import br.com.locahouse.repository.UsuarioRepository;
import br.com.locahouse.service.UsuarioService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository repository;

    @Autowired
    private UsuarioServiceImpl(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public Usuario cadastrar(Usuario usuario) {
        this.salvar(usuario);
        return usuario;
    }

    @Override
    public Usuario buscarPeloId(Integer id) {
        return this.repository.findById(id).orElseThrow(() -> new RecursoNaoEcontradoException("Usuário"));
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
