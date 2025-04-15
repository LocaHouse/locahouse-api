package br.com.locahouse.service.impl;

import br.com.locahouse.exception.RecursoNaoEcontradoException;
import br.com.locahouse.model.ImagemDoImovel;
import br.com.locahouse.repository.ImagemDoImovelRepository;
import br.com.locahouse.service.ArmazenamentoImagemService;
import br.com.locahouse.service.ImagemDoImovelService;
import br.com.locahouse.service.ImovelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Transactional
@Service
public class ImagemDoImovelServiceImpl implements ImagemDoImovelService {

    private final ImagemDoImovelRepository repository;

    private final ImovelService imovelService;

    private final ArmazenamentoImagemService armazenamentoImagemService;

    @Autowired
    public ImagemDoImovelServiceImpl(ImagemDoImovelRepository repository, ImovelService imovelService, ArmazenamentoImagemService armazenamentoImagemService) {
        this.repository = repository;
        this.imovelService = imovelService;
        this.armazenamentoImagemService = armazenamentoImagemService;
    }

    @Override
    public ImagemDoImovel cadastrar(Integer imovelId, MultipartFile imagem) {
        ImagemDoImovel imagemDoImovel = new ImagemDoImovel();
        imagemDoImovel.setImovel(this.imovelService.buscarPeloId(imovelId));
        imagemDoImovel.setSequencia(this.repository.obterSequenciaDisponivel(imovelId));
        imagemDoImovel.setCaminho(this.armazenamentoImagemService.gerarCaminho(imagem));
        this.repository.save(imagemDoImovel);
        this.armazenamentoImagemService.gravarNoDisco(imagem, imagemDoImovel.getCaminho());
        return imagemDoImovel;
    }

    @Override
    public ImagemDoImovel buscarPeloId(Integer id) {
        return this.repository.findById(id).orElseThrow(() -> new RecursoNaoEcontradoException("Imagem"));
    }

    @Override
    public void deletar(Integer id) {
        ImagemDoImovel imagemDoImovel = this.buscarPeloId(id);
        this.repository.delete(imagemDoImovel);
        this.armazenamentoImagemService.apagarDoDIsco(imagemDoImovel.getCaminho());
    }
}
