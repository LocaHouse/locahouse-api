package br.com.locahouse.controller;

import br.com.locahouse.controller.doc.ImovelControllerDoc;
import br.com.locahouse.controller.dto.imovel.ImovelBuscaDto;
import br.com.locahouse.controller.dto.imovel.ImovelCadastroAndAtualizacaoDto;
import br.com.locahouse.mapper.ImovelMapper;
import br.com.locahouse.model.enums.StatusImovelEnum;
import br.com.locahouse.service.ImovelService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static br.com.locahouse.mapper.ImovelMapper.*;

@RestController
@RequestMapping("/api/v1/imoveis")
public class ImovelController implements ImovelControllerDoc {

    private final ImovelService service;

    @Autowired
    public ImovelController(ImovelService service) {
        this.service = service;
    }

    @Override
    @PostMapping("/cadastrar/{usuarioId}")
    public ResponseEntity<Void> cadastrar(@PathVariable Integer usuarioId, @Valid @RequestBody ImovelCadastroAndAtualizacaoDto dto) {
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(this.service.cadastrar(usuarioId, imovelCadastroAndAtualizacaoDtoToEntity(dto), dto.numeroCep()).getId()).toUri()).build();
    }

    @Override
    @GetMapping("/buscar-disponiveis")
    public Page<ImovelBuscaDto> buscarDisponiveis(Pageable pageable) {
        return this.service.buscar(pageable, null, StatusImovelEnum.DISPONIVEL.getCodigo()).map(ImovelMapper::entityToImovelBuscaDto);
    }

    @Override
    @GetMapping("/buscar-meus/{usuarioId}")
    public Page<ImovelBuscaDto> buscarTodosPorUsuario(Pageable pageable, @PathVariable Integer usuarioId, @RequestParam(required = false) Integer status) {
        return this.service.buscar(pageable, usuarioId, status).map(ImovelMapper::entityToImovelBuscaDto);
    }

    @Override
    @GetMapping("/buscar/{id}")
    public ResponseEntity<ImovelBuscaDto> buscarPeloId(@PathVariable Integer id) {
        return ResponseEntity.ok(entityToImovelBuscaDto(this.service.buscarPeloId(id)));
    }

    @Override
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<ImovelBuscaDto> atualizar(@PathVariable Integer id, @Valid @RequestBody ImovelCadastroAndAtualizacaoDto dto) {
        return ResponseEntity.ok(entityToImovelBuscaDto(this.service.atualizar(id, imovelCadastroAndAtualizacaoDtoToEntity(dto), dto.numeroCep())));
    }

    @Override
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        this.service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
