package br.com.locahouse.service.impl;

import br.com.locahouse.model.enums.StatusImovelEnum;
import br.com.locahouse.exception.BusinessException;
import br.com.locahouse.exception.RecursoNaoEcontradoException;
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

@Service
public class ImovelServiceImpl implements ImovelService {

    private final ImovelRepository repository;

    private final UsuarioService usuarioService;

    private final CepService cepService;

    @Autowired
    public ImovelServiceImpl(ImovelRepository repository, UsuarioService usuarioService, CepService cepService) {
        this.repository = repository;
        this.usuarioService = usuarioService;
        this.cepService = cepService;
    }

    @Transactional
    @Override
    public Imovel cadastrar(Integer usuarioId, Imovel imovel, String numeroCep) {
        imovel.setUsuario(this.usuarioService.buscarPeloId(usuarioId));
        return this.salvar(imovel, numeroCep);
    }

    @Override
    public Page<Imovel> buscar(Pageable pageable, Integer usuarioId, Integer status) {
        if (status != null && !StatusImovelEnum.verificarExistencia(status)) {
            throw new BusinessException("Status inválido.", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        return this.repository.buscar(pageable, usuarioId, status);
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
        return this.salvar(imovelParaAtualizar, numeroCep);
    }

    @Override
    public void deletar(Integer id) {
        this.repository.delete(this.buscarPeloId(id));
    }

    private Imovel salvar(Imovel imovel, String numeroCep) {
        if (this.cepService.verificarExistencia(numeroCep)) {
            imovel.setCep(this.cepService.buscarPeloNumero(numeroCep));
        } else {
            imovel.setCep(this.cepService.salvar(numeroCep));
        }
        return this.repository.save(imovel);
    }
}
