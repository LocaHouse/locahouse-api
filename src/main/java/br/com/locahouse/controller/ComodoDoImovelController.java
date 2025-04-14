package br.com.locahouse.controller;

import br.com.locahouse.controller.doc.ComodoDoImovelControllerDoc;
import br.com.locahouse.controller.dto.comododoimovel.ComodoDoImovelBuscaDto;
import br.com.locahouse.controller.dto.comododoimovel.ComodoDoImovelCadastroAndAtualizacaoDto;
import br.com.locahouse.service.ComodoDoImovelService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static br.com.locahouse.mapper.ComodoDoImovelMapper.*;

@RestController
@RequestMapping("/api/v1/comodos-imoveis")
public class ComodoDoImovelController implements ComodoDoImovelControllerDoc {

    private final ComodoDoImovelService service;

    @Autowired
    public ComodoDoImovelController(ComodoDoImovelService service) {
        this.service = service;
    }

    @Override
    @PostMapping("/cadastrar/{imovelId}")
    public ResponseEntity<Void> cadastrar(@PathVariable Integer imovelId, @Valid @RequestBody ComodoDoImovelCadastroAndAtualizacaoDto dto) {
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().replacePath("api/v1/comodos-imoveis/buscar/{id}").buildAndExpand(this.service.cadastrar(imovelId, comodoDoImovelCadastroAndAtualizacaoDtoToEntity(dto)).getId()).toUri()).build();
    }

    @Override
    @GetMapping("/buscar/{id}")
    public ResponseEntity<ComodoDoImovelBuscaDto> buscarPeloId(@PathVariable Integer id) {
        return ResponseEntity.ok(entityToComodoDoImovelBuscaDto(this.service.buscarPeloId(id)));
    }

    @Override
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<ComodoDoImovelBuscaDto> atualizar(@PathVariable Integer id, @Valid @RequestBody ComodoDoImovelCadastroAndAtualizacaoDto dto) {
        return ResponseEntity.ok(entityToComodoDoImovelBuscaDto(this.service.atualizar(id, comodoDoImovelCadastroAndAtualizacaoDtoToEntity(dto))));
    }

    @Override
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        this.service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
