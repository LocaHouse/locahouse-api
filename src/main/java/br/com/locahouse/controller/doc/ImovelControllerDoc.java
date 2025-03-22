package br.com.locahouse.controller.doc;

import br.com.locahouse.controller.dto.imovel.ImovelBuscaDto;
import br.com.locahouse.controller.dto.imovel.ImovelCadastroAndAtualizacaoDto;
import br.com.locahouse.exception.dto.ExceptionDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

@Tag(name = "Imóveis", description = "- Endpoints para gerenciamento de imóveis.")
public interface ImovelControllerDoc {

    @Operation(summary = "- Cadastrar.", description = "Realiza o cadastro de um imóvel no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Imóvel cadastrado."),
            @ApiResponse(responseCode = "400", description = "Requisição inválida.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class))),
            @ApiResponse(responseCode = "401", description = "Autenticação necessária.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class))),
            @ApiResponse(responseCode = "403", description = "Acesso não autorizado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class))),
            @ApiResponse(responseCode = "422", description = "CEP não encontrado base de dados do ViaCEP.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class)))
    })
    ResponseEntity<Void> cadastrar(Integer usuarioId, ImovelCadastroAndAtualizacaoDto dto);


    @Operation(summary = "- Buscar disponíveis.", description = "Busca todos os imóveis disponíveis.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class)))
    })
    Page<ImovelBuscaDto> buscarDisponiveis(Pageable pageable);


    @Operation(summary = "- Buscar meus.", description = "Busca todos os imóveis de um usuário.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso."),
            @ApiResponse(responseCode = "422", description = "Status inválido.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class)))
    })
    Page<ImovelBuscaDto> buscarTodosPorUsuario(Pageable pageable, Integer usuarioId, Integer status);


    @Operation(summary = "- Buscar pelo ID.", description = "Busca um imóvel pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Imóvel encontrado."),
            @ApiResponse(responseCode = "401", description = "Autenticação necessária.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class))),
            @ApiResponse(responseCode = "403", description = "Acesso não autorizado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class))),
            @ApiResponse(responseCode = "404", description = "Imóvel não encontrado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class)))
    })
    ResponseEntity<ImovelBuscaDto> buscarPeloId(Integer id);


    @Operation(summary = "- Atualizar o cadastro.", description = "Atualiza o cadastro de um imóvel.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atualização realizada."),
            @ApiResponse(responseCode = "400", description = "Requisição inválida.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class))),
            @ApiResponse(responseCode = "401", description = "Autenticação necessária.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class))),
            @ApiResponse(responseCode = "403", description = "Acesso não autorizado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class))),
            @ApiResponse(responseCode = "422", description = "CEP não encontrado base de dados do ViaCEP.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class)))
    })
    ResponseEntity<ImovelBuscaDto> atualizar(Integer id, ImovelCadastroAndAtualizacaoDto dto);


    @Operation(summary = "- Deletar o cadastro.", description = "Deleta o cadastro de um imóvel.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deleção realizada."),
            @ApiResponse(responseCode = "401", description = "Autenticação necessária.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class))),
            @ApiResponse(responseCode = "403", description = "Acesso não autorizado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class)))
    })
    ResponseEntity<Void> deletar(Integer id);
}
