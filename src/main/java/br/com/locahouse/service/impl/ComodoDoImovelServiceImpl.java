/*package br.com.locahouse.service.impl;

import br.com.locahouse.exception.RecursoNaoEcontradoException;
import br.com.locahouse.model.ComodoDoImovel;
import br.com.locahouse.repository.ComodoDoImovelRepository;
import br.com.locahouse.service.ComodoDoImovelService;
import br.com.locahouse.service.ComodoService;
import org.springframework.beans.factory.annotation.Autowired;

public class ComodoDoImovelServiceImpl implements ComodoDoImovelService {

    private final ComodoDoImovelRepository repository;

    private final ComodoService comodoService;

    @Autowired
    public ComodoDoImovelServiceImpl(ComodoDoImovelRepository repository, ComodoService comodoService) {
        this.repository = repository;
        this.comodoService = comodoService;
    }

    @Override
    public ComodoDoImovel salvar(ComodoDoImovel comodoDoImovel) {
        //comodoDoImovel.setImovel("");
        comodoDoImovel.setComodo(this.comodoService.buscarPeloId(comodoDoImovel.getId()));

        if (!comodoService.verificarExistencia(comodoDoImovel.getId()))
            throw new RecursoNaoEcontradoException("Cômodo");

        return this.repository.save(comodoDoImovel);
    }
}
*/