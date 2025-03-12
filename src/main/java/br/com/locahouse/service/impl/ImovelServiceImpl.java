package br.com.locahouse.service.impl;

import br.com.locahouse.model.enums.StatusImovelEnum;
import br.com.locahouse.exception.BusinessException;
import br.com.locahouse.exception.RecursoNaoEcontradoException;
import br.com.locahouse.integration.ViaCepService;
import br.com.locahouse.model.Imovel;
import br.com.locahouse.repository.ImovelRepository;
import br.com.locahouse.service.*;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
public class ImovelServiceImpl implements ImovelService {

    private final ImovelRepository repository;

    private final UsuarioService usuarioService;

    private final CepService cepService;

    private final ViaCepService viaCepService;

    @Autowired
    public ImovelServiceImpl(ImovelRepository repository, UsuarioService usuarioService, CepService cepService, ViaCepService viaCepService) {
        this.repository = repository;
        this.usuarioService = usuarioService;
        this.cepService = cepService;
        this.viaCepService = viaCepService;
    }

    @Transactional
    @Override
    public Imovel cadastrar(Integer usuarioId, Imovel imovel, String numeroCep) {
        imovel.setUsuario(this.usuarioService.buscarPeloId(usuarioId));
        this.salvar(imovel, numeroCep);
        return imovel;
    }

    @Override
    public Page<Imovel> buscar(Pageable pageable, Integer id, Integer status) {
        if (status != null && !StatusImovelEnum.verificarExistencia(status))
            throw new BusinessException("Status inválido.", HttpStatus.UNPROCESSABLE_ENTITY);

        return this.repository.buscar(pageable, id, status);
    }

    @Override
    public Imovel buscarPeloId(Integer id) {
        return this.repository.findById(id).orElseThrow(() -> new RecursoNaoEcontradoException("Imóvel"));
    }

    @Transactional
    @Override
    public Imovel atualizar(Integer id, Imovel imovel, String numeroCep) {
        Imovel imovelParaAtualizar = this.buscarPeloId(id);
        BeanUtils.copyProperties(imovel, imovelParaAtualizar, "id", "usuario");
        this.salvar(imovelParaAtualizar, numeroCep);
        return imovelParaAtualizar;
    }

    @Override
    public void deletar(Integer id) {
        this.repository.delete(this.buscarPeloId(id));
    }

    private void salvar(Imovel imovel, String numeroCep) {
        if (this.cepService.verificarExistencia(numeroCep))
            imovel.setCep(this.cepService.buscarPeloNumero(numeroCep));
        else {
            try {
                imovel.setCep(this.cepService.salvar(this.viaCepService.consultar(numeroCep)));
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        this.repository.save(imovel);
    }
}
