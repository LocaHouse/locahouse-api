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
    public ImagemDoImovel cadastrar(Integer imovelId, ImagemDoImovel imagemDoImovel, MultipartFile imagem) {
        imagemDoImovel.setImovel(imovelService.buscarPeloId(imovelId));
        this.salvar(imagemDoImovel, imagem);
        return imagemDoImovel;
    }

    void salvar(ImagemDoImovel imagemDoImovel, MultipartFile imagem) {
        imagemDoImovel.setCaminho(this.armazenarImagemNoDisco(imagem));
        this.repository.save(imagemDoImovel);
    }

    private String armazenarImagemNoDisco(MultipartFile imagem) {
        try {
            Path caminho = Paths.get("c:/users/gabri/desktop/");
            if (!Files.exists(caminho))
                Files.createDirectories(caminho);

            caminho = caminho.resolve(UUID.randomUUID() + "-" + imagem.getOriginalFilename());
            imagem.transferTo(caminho.toFile());

            return caminho.toString();
        } catch (IOException e) {
            throw new BusinessException("Erro ao fazer o upload da imagem.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
