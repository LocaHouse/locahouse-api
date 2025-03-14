package br.com.locahouse.controller;

import br.com.locahouse.controller.dto.comododoimovel.ComodoDoImovelBuscaDto;
import br.com.locahouse.controller.dto.comododoimovel.ComodoDoImovelCadastroAndAtualizacaoDto;
import br.com.locahouse.mapper.ComodoDoImovelMapper;
import br.com.locahouse.model.ComodoDoImovel;
import br.com.locahouse.service.ComodoDoImovelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Tag(name = "Cômodos de imóveis", description = "- Endpoints para gerenciamento de cômodos dos imóveis.")
@RequestMapping("/api/v1/comodos-imoveis")
@RestController
public class ComodoDoImovelController {

    private final ComodoDoImovelService comodoDoImovelService;

    @Autowired
    public ComodoDoImovelController(ComodoDoImovelService comodoDoImovelService) {
        this.comodoDoImovelService = comodoDoImovelService;
    }

    @Operation(
            description = "Realiza o cadastro de um cômodo de um imóvel no sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Cômodo cadastrado."
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Requisição inválida."
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Autenticação necessária."
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acesso não autorizado."
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Tipo de cômodo já cadastrado para o imóvel."
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor."
            )
    })
    @PostMapping("/cadastrar/{imovelId}")
    public ResponseEntity<Void> cadastrar(@PathVariable Integer imovelId, @Valid @RequestBody ComodoDoImovelCadastroAndAtualizacaoDto dto) {
        ComodoDoImovel comodoDoImovel = this.comodoDoImovelService.cadastrar(imovelId, ComodoDoImovelMapper.comodoDoImovelCadastroAndAtualizacaoDtoToEntity(dto));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().replacePath("api/v1/comodos-imoveis/buscar/{id}").buildAndExpand(comodoDoImovel.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @Operation(
            description = "Busca um cômodo de um imóvel pelo ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Cômodo encontrado."
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Autenticação necessária."
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acesso não autorizado."
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Cômodo não encontrado."
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor."
            )
    })
    @GetMapping("/buscar/{id}")
    public ResponseEntity<ComodoDoImovelBuscaDto> buscarPeloId(@PathVariable Integer id) {
        return ResponseEntity.ok(ComodoDoImovelMapper.entityToComodoDoImovelBuscaDto(this.comodoDoImovelService.buscarPeloId(id)));
    }

    @Operation(
            description = "Atualiza o cadastro de um cômodo de um imóvel."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Atualização realizada."
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Requisição inválida."
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Autenticação necessária."
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acesso não autorizado."
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Tipo de cômodo já cadastrado para o imóvel."
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor."
            )
    })
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<ComodoDoImovelBuscaDto> atualizar(@PathVariable Integer id, @Valid @RequestBody ComodoDoImovelCadastroAndAtualizacaoDto dto) {
        return ResponseEntity.ok(ComodoDoImovelMapper.entityToComodoDoImovelBuscaDto(this.comodoDoImovelService.atualizar(id, ComodoDoImovelMapper.comodoDoImovelCadastroAndAtualizacaoDtoToEntity(dto))));
    }

    @Operation(
            description = "Deleta o cadastro de um cômodo de um imóvel."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Deleção realizada."
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Autenticação necessária."
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acesso não autorizado."
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor."
            )
    })
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        this.comodoDoImovelService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
