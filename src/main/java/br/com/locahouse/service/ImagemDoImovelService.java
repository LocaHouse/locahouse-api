package br.com.locahouse.service;

import br.com.locahouse.model.ImagemDoImovel;
import org.springframework.web.multipart.MultipartFile;

public interface ImagemDoImovelService {

    ImagemDoImovel cadastrar(Integer imovelId, ImagemDoImovel imagemDoImovel, MultipartFile imagem);
}
