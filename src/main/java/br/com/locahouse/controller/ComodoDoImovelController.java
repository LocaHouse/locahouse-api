package br.com.locahouse.controller;

import br.com.locahouse.controller.doc.ComodoDoImovelControllerDoc;
import br.com.locahouse.controller.dto.comododoimovel.ComodoDoImovelBuscaDto;
import br.com.locahouse.controller.dto.comododoimovel.ComodoDoImovelCadastroAndAtualizacaoDto;
import br.com.locahouse.mapper.ComodoDoImovelMapper;
import br.com.locahouse.model.ComodoDoImovel;
import br.com.locahouse.service.ComodoDoImovelService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/comodos-imoveis")
public final class ComodoDoImovelController implements ComodoDoImovelControllerDoc {

    private final ComodoDoImovelService comodoDoImovelService;

    @Autowired
    public ComodoDoImovelController(ComodoDoImovelService comodoDoImovelService) {
        this.comodoDoImovelService = comodoDoImovelService;
    }

    @Override
    @PostMapping("/cadastrar/{imovelId}")
    public ResponseEntity<Void> cadastrar(@PathVariable Integer imovelId, @Valid @RequestBody ComodoDoImovelCadastroAndAtualizacaoDto dto) {
        ComodoDoImovel comodoDoImovel = this.comodoDoImovelService.cadastrar(imovelId, ComodoDoImovelMapper.comodoDoImovelCadastroAndAtualizacaoDtoToEntity(dto));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().replacePath("api/v1/comodos-imoveis/buscar/{id}").buildAndExpand(comodoDoImovel.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @Override
    @GetMapping("/buscar/{id}")
    public ResponseEntity<ComodoDoImovelBuscaDto> buscarPeloId(@PathVariable Integer id) {
        return ResponseEntity.ok(ComodoDoImovelMapper.entityToComodoDoImovelBuscaDto(this.comodoDoImovelService.buscarPeloId(id)));
    }

    @Override
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<ComodoDoImovelBuscaDto> atualizar(@PathVariable Integer id, @Valid @RequestBody ComodoDoImovelCadastroAndAtualizacaoDto dto) {
        return ResponseEntity.ok(ComodoDoImovelMapper.entityToComodoDoImovelBuscaDto(this.comodoDoImovelService.atualizar(id, ComodoDoImovelMapper.comodoDoImovelCadastroAndAtualizacaoDtoToEntity(dto))));
    }

    @Override
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        this.comodoDoImovelService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
