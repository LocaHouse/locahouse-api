package br.com.locahouse.service.impl;

import br.com.locahouse.exception.RecursoNaoEcontradoException;
import br.com.locahouse.model.Cep;
import br.com.locahouse.repository.CepRepository;
import br.com.locahouse.service.CepService;
import br.com.locahouse.service.integration.viacep.ViaCepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CepServiceImpl implements CepService {

    private final CepRepository repository;

    private final ViaCepService viaCepService;

    @Autowired
    public CepServiceImpl(CepRepository repository, ViaCepService viaCepService) {
        this.repository = repository;
        this.viaCepService = viaCepService;
    }

    @Override
    public Cep salvar(String numero) {
        return this.repository.save(this.viaCepService.consultar(numero));
    }

    @Override
    public Cep buscarPeloNumero(String numero) {
        return this.repository.findByNumero(numero).orElseThrow(() -> new RecursoNaoEcontradoException("CEP"));
    }

    @Override
    public boolean verificarExistencia(String numero) {
        return this.repository.findByNumero(numero).isPresent();
    }
}
