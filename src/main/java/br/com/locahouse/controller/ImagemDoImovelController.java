package br.com.locahouse.controller;

import br.com.locahouse.model.ImagemDoImovel;
import br.com.locahouse.service.ImagemDoImovelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/imagens-imoveis")
public class ImagemDoImovelController {

    private final ImagemDoImovelService service;

    @Autowired
    public ImagemDoImovelController(ImagemDoImovelService service) {
        this.service = service;
    }

    @PostMapping("/upload/{imovelId}")
    public ResponseEntity<Void> cadastrar(@PathVariable Integer imovelId, @RequestParam("imagem") MultipartFile imagem) {
        ImagemDoImovel imagemDoImovel = this.service.cadastrar(imovelId, imagem);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().replacePath("api/v1/imagens-imoveis/buscar/{id}").buildAndExpand(imagemDoImovel.getId()).toUri();
        return ResponseEntity.created(location).build();
    }
}
