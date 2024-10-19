package br.com.locahouse.controller;

import br.com.locahouse.dto.imovel.ImovelGetDto;
import br.com.locahouse.dto.imovel.ImovelPostPutDto;
import br.com.locahouse.mapper.ImovelMapper;
import br.com.locahouse.model.Imovel;
import br.com.locahouse.service.ImovelService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequestMapping("/api/v1/imoveis")
@RestController
public class ImovelController {

    private final ImovelService imovelService;

    @Autowired
    public ImovelController(ImovelService imovelService) {
        this.imovelService = imovelService;
    }

    @PostMapping("/cadastrar/{usuarioId}")
    public ResponseEntity<Void> cadastrar(@PathVariable Integer usuarioId, @Valid @RequestBody ImovelPostPutDto dto) {
        Imovel imovel = this.imovelService.cadastrar(usuarioId, ImovelMapper.imovelPostDtoToEntity(dto), dto.numeroCep());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().replacePath("api/v1/imoveis/buscar/{id}").buildAndExpand(imovel.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<ImovelGetDto> buscarPeloId(@PathVariable Integer id) {
        return ResponseEntity.ok(ImovelMapper.entityToImovelGetDto(this.imovelService.buscarPeloId(id)));
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<ImovelGetDto> atualizar(@PathVariable Integer id, @Valid @RequestBody ImovelPostPutDto dto) {
        Imovel imovel = this.imovelService.atualizar(id, ImovelMapper.imovelPostDtoToEntity(dto), dto.numeroCep());
        return ResponseEntity.ok(ImovelMapper.entityToImovelGetDto(imovel));
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        this.imovelService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
