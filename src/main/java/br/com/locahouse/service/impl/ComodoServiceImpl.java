package br.com.locahouse.service.impl;

import br.com.locahouse.exception.RecursoNaoEcontradoException;
import br.com.locahouse.model.Comodo;
import br.com.locahouse.repository.ComodoRepository;
import br.com.locahouse.service.ComodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public final class ComodoServiceImpl implements ComodoService {

    private final ComodoRepository repository;

    @Autowired
    public ComodoServiceImpl(ComodoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Comodo buscarPeloId(Integer id) {
        return this.repository.findById(id).orElseThrow(() -> new RecursoNaoEcontradoException("Cômodo"));
    }
}
