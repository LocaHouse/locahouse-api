package br.com.locahouse.service.impl;

import br.com.locahouse.exception.BusinessException;
import br.com.locahouse.model.ImagemDoImovel;
import br.com.locahouse.repository.ImagemDoImovelRepository;
import br.com.locahouse.service.ImagemDoImovelService;
import br.com.locahouse.service.ImovelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ImagemDoImovelServiceImpl implements ImagemDoImovelService {

    private final ImagemDoImovelRepository repository;

    private final ImovelService imovelService;

    @Autowired
    public ImagemDoImovelServiceImpl(ImagemDoImovelRepository repository, ImovelService imovelService) {
        this.repository = repository;
        this.imovelService = imovelService;
    }

    @Override
    public ImagemDoImovel cadastrar(Integer imovelId, MultipartFile imagem) {
        ImagemDoImovel imagemDoImovel = new ImagemDoImovel();
        imagemDoImovel.setImovel(this.imovelService.buscarPeloId(imovelId));
        imagemDoImovel.setSequencia(this.repository.buscarProximaSequenciaDisponivel(imovelId));
        imagemDoImovel.setCaminho(this.gravarImagemNoDisco(imagem));

        try {
            return this.repository.save(imagemDoImovel);
        } catch (Exception e) {
            this.apagarImagemDoDisco(imagemDoImovel.getCaminho());
            throw new RuntimeException(e.getMessage());
        }
    }

    private String gravarImagemNoDisco(MultipartFile imagem) {
        try {
            Path caminho = Paths.get(System.getenv("CAMINHO_IMAGENS_IMOVEIS"));
            if (!Files.exists(caminho))
                Files.createDirectories(caminho);

            caminho = caminho.resolve(UUID.randomUUID() + "-" + imagem.getOriginalFilename());
            imagem.transferTo(caminho.toFile());

            return caminho.toString();
        } catch (IOException e) {
            throw new BusinessException("Erro ao fazer o upload da imagem.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void apagarImagemDoDisco(String caminho) {
        try {
            Path path = Path.of(caminho);
            if (Files.exists(path))
                Files.delete(path);
        } catch (IOException e) {
            throw new BusinessException("Erro ao fazer a remoção da imagem.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
