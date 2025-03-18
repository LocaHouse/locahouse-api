package br.com.locahouse.controller;

import br.com.locahouse.controller.doc.ImovelControllerDoc;
import br.com.locahouse.controller.dto.imovel.ImovelBuscaDto;
import br.com.locahouse.controller.dto.imovel.ImovelCadastroAndAtualizacaoDto;
import br.com.locahouse.model.enums.StatusImovelEnum;
import br.com.locahouse.mapper.ImovelMapper;
import br.com.locahouse.model.Imovel;
import br.com.locahouse.service.ImovelService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/imoveis")
public class ImovelController implements ImovelControllerDoc {

    private final ImovelService imovelService;

    @Autowired
    public ImovelController(ImovelService imovelService) {
        this.imovelService = imovelService;
    }

    @Override
    @PostMapping("/cadastrar/{usuarioId}")
    public ResponseEntity<Void> cadastrar(@PathVariable Integer usuarioId, @Valid @RequestBody ImovelCadastroAndAtualizacaoDto dto) {
        Imovel imovel = this.imovelService.cadastrar(usuarioId, ImovelMapper.imovelCadastroAndAtualizacaoDtoToEntity(dto), dto.numeroCep());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().replacePath("api/v1/imoveis/buscar/{id}").buildAndExpand(imovel.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @Override
    @GetMapping("/buscar-disponiveis")
    public Page<ImovelBuscaDto> buscarDisponiveis(Pageable pageable) {
        return this.imovelService.buscar(pageable, null, StatusImovelEnum.DISPONIVEL.getCodigo()).map(ImovelMapper::entityToImovelBuscaDto);
    }

    @Override
    @GetMapping("/buscar-meus/{usuarioId}")
    public Page<ImovelBuscaDto> buscarTodosPorUsuario(Pageable pageable, @PathVariable Integer usuarioId, @RequestParam(required = false) Integer status) {
        return this.imovelService.buscar(pageable, usuarioId, status).map(ImovelMapper::entityToImovelBuscaDto);
    }

    @Override
    @GetMapping("/buscar/{id}")
    public ResponseEntity<ImovelBuscaDto> buscarPeloId(@PathVariable Integer id) {
        return ResponseEntity.ok(ImovelMapper.entityToImovelBuscaDto(this.imovelService.buscarPeloId(id)));
    }

    @Override
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<ImovelBuscaDto> atualizar(@PathVariable Integer id, @Valid @RequestBody ImovelCadastroAndAtualizacaoDto dto) {
        Imovel imovel = this.imovelService.atualizar(id, ImovelMapper.imovelCadastroAndAtualizacaoDtoToEntity(dto), dto.numeroCep());
        return ResponseEntity.ok(ImovelMapper.entityToImovelBuscaDto(imovel));
    }

    @Override
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        this.imovelService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
