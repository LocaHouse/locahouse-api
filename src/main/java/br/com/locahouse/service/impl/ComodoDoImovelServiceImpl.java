package br.com.locahouse.service.impl;

import br.com.locahouse.exception.RecursoNaoEcontradoException;
import br.com.locahouse.exception.UniqueConstraintVioladaException;
import br.com.locahouse.model.ComodoDoImovel;
import br.com.locahouse.repository.ComodoDoImovelRepository;
import br.com.locahouse.service.ComodoDoImovelService;
import br.com.locahouse.service.ComodoService;
import br.com.locahouse.service.ImovelService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ComodoDoImovelServiceImpl implements ComodoDoImovelService {

    private final ComodoDoImovelRepository repository;

    private final ComodoService comodoService;

    private final ImovelService imovelService;

    @Autowired
    public ComodoDoImovelServiceImpl(ComodoDoImovelRepository repository, ComodoService comodoService, ImovelService imovelService) {
        this.repository = repository;
        this.comodoService = comodoService;
        this.imovelService = imovelService;
    }

    @Override
    public ComodoDoImovel cadastrar(Integer imovelId, ComodoDoImovel comodoDoImovel) {
        comodoDoImovel.setImovel(this.imovelService.buscarPeloId(imovelId));
        this.salvar(comodoDoImovel);
        return comodoDoImovel;
    }

    @Override
    public ComodoDoImovel buscarPeloId(Integer id) {
        return this.repository.findById(id).orElseThrow(() -> new RecursoNaoEcontradoException("Cômodo"));
    }

    @Override
    public ComodoDoImovel atualizar(Integer id, ComodoDoImovel comodoDoImovel) {
        ComodoDoImovel comodoDoImovelParaAtualizar = this.buscarPeloId(id);
        BeanUtils.copyProperties(comodoDoImovel, comodoDoImovelParaAtualizar, "id", "imovel");
        this.salvar(comodoDoImovelParaAtualizar);
        return comodoDoImovelParaAtualizar;
    }

    @Override
    public void deletar(Integer id) {
        this.repository.delete(this.buscarPeloId(id));
    }

    private void salvar(ComodoDoImovel comodoDoImovel) {
        comodoDoImovel.setComodo(this.comodoService.buscarPeloId(comodoDoImovel.getComodo().getId()));
        this.verificarUnicidadeComodoImovel(comodoDoImovel.getId(), comodoDoImovel.getComodo().getId(), comodoDoImovel.getImovel().getId());
        this.repository.save(comodoDoImovel);
    }

    private void verificarUnicidadeComodoImovel(Integer id, Integer comodoId, Integer imovelId) {
        Optional<ComodoDoImovel> comodoDoImovel = this.repository.findByComodoIdAndImovelId(comodoId, imovelId);
        if (comodoDoImovel.isPresent() && (id == null || !id.equals(comodoDoImovel.get().getId()))) {
            throw new UniqueConstraintVioladaException("Tipo de cômodo");
        }
    }
}
