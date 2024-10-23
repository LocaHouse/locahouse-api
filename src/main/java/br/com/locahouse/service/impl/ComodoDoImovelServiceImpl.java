package br.com.locahouse.service.impl;

import br.com.locahouse.model.ComodoDoImovel;
import br.com.locahouse.repository.ComodoDoImovelRepository;
import br.com.locahouse.service.ComodoDoImovelService;
import org.springframework.beans.factory.annotation.Autowired;

public class ComodoDoImovelServiceImpl implements ComodoDoImovelService {

    private final ComodoDoImovelRepository repository;

    @Autowired
    public ComodoDoImovelServiceImpl(ComodoDoImovelRepository repository) {
        this.repository = repository;
    }

    @Override
    public ComodoDoImovel salvar(ComodoDoImovel comodoDoImovel) {
        return this.repository.save(comodoDoImovel);
    }
}
