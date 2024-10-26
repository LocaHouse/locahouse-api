package br.com.locahouse.controller;

import br.com.locahouse.dto.imovel.ImovelBuscaDto;
import br.com.locahouse.dto.imovel.ImovelCadastroAndAtualizacaoDto;
import br.com.locahouse.enums.StatusImovelEnum;
import br.com.locahouse.mapper.ImovelMapper;
import br.com.locahouse.model.Imovel;
import br.com.locahouse.service.ImovelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Tag(name = "Imóveis", description = "- Endpoints para gerenciamento de imóveis.")
@RequestMapping("/api/v1/imoveis")
@RestController
public class ImovelController {

    private final ImovelService imovelService;

    @Autowired
    public ImovelController(ImovelService imovelService) {
        this.imovelService = imovelService;
    }

    @Operation(
            description = "Realiza o cadastro de um imóvel no sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Imóvel cadastrado."
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
                    responseCode = "422",
                    description = "CEP não encontrado base de dados do ViaCEP."
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor."
            )
    })
    @PostMapping("/cadastrar/{usuarioId}")
    public ResponseEntity<Void> cadastrar(@PathVariable Integer usuarioId, @Valid @RequestBody ImovelCadastroAndAtualizacaoDto dto) {
        Imovel imovel = this.imovelService.cadastrar(usuarioId, ImovelMapper.imovelCadastroAndAtualizacaoDtoToEntity(dto), dto.numeroCep());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().replacePath("api/v1/imoveis/buscar/{id}").buildAndExpand(imovel.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @Operation(
            description = "Busca todos os imóveis disponíveis."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Sucesso."
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor."
            )
    })
    @GetMapping("/buscar-disponiveis")
    public Page<ImovelBuscaDto> buscarDisponiveis(Pageable pageable) {
        return this.buscarImoveis(pageable, null, StatusImovelEnum.DISPONIVEL.getCodigo());
    }

    @Operation(
            description = "Busca os imóveis do usuário."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Sucesso."
            ),
            @ApiResponse(
                    responseCode = "422",
                    description = "Status inválido."
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor."
            )
    })
    @GetMapping("/buscar-meus/{idUsuario}")
    public Page<ImovelBuscaDto> buscarTodosPorUsuario(Pageable pageable, @PathVariable Integer idUsuario, @RequestParam(required = false) Integer status) {
        return this.buscarImoveis(pageable, idUsuario, status);
    }

    @Operation(
            description = "Busca um imóvel pelo ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Imóvel encontrado."
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
                    description = "Imóvel não encontrado."
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor."
            )
    })
    @GetMapping("/buscar/{id}")
    public ResponseEntity<ImovelBuscaDto> buscarPeloId(@PathVariable Integer id) {
        return ResponseEntity.ok(ImovelMapper.entityToImovelBuscaDto(this.imovelService.buscarPeloId(id)));
    }

    @Operation(
            description = "Atualiza o cadastro de um imóvel."
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
                    responseCode = "422",
                    description = "CEP não encontrado base de dados do ViaCEP."
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor."
            )
    })
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<ImovelBuscaDto> atualizar(@PathVariable Integer id, @Valid @RequestBody ImovelCadastroAndAtualizacaoDto dto) {
        Imovel imovel = this.imovelService.atualizar(id, ImovelMapper.imovelCadastroAndAtualizacaoDtoToEntity(dto), dto.numeroCep());
        return ResponseEntity.ok(ImovelMapper.entityToImovelBuscaDto(imovel));
    }

    @Operation(
            description = "Deleta o cadastro de um imóvel."
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
        this.imovelService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    private Page<ImovelBuscaDto> buscarImoveis(Pageable pageable, Integer idUsuario, Integer status) {
        return this.imovelService.buscar(pageable, idUsuario, status).map(ImovelMapper::entityToImovelBuscaDto);
    }
}
