package br.com.locahouse.controller;

import br.com.locahouse.controller.dto.imagemdoimovel.ImagemDoImovelCadastroAndAtualizacaoDto;
import br.com.locahouse.model.ImagemDoImovel;
import br.com.locahouse.service.ImagemDoImovelService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static br.com.locahouse.mapper.ImagemDoImovelMapper.*;

@RestController
@RequestMapping("/api/v1/imagens-imoveis")
public class ImagemDoImovelController {

    private final ImagemDoImovelService service;

    @Autowired
    public ImagemDoImovelController(ImagemDoImovelService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> cadastrar(@PathVariable Integer imovelId, @Valid @RequestBody ImagemDoImovelCadastroAndAtualizacaoDto dto, MultipartFile imagem) {
        ImagemDoImovel imagemDoImovel = this.service.cadastrar(imovelId, imagemDoImovelCadastroAndAtualizacaoToEntity(dto), imagem);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().replacePath("api/v1/imagens-imoveis/buscar/{id}").buildAndExpand(imagemDoImovel.getId()).toUri();
        return ResponseEntity.created(location).build();
    }
}
