package br.com.locahouse.controller.doc;

import br.com.locahouse.controller.dto.comododoimovel.ComodoDoImovelBuscaDto;
import br.com.locahouse.controller.dto.comododoimovel.ComodoDoImovelCadastroAndAtualizacaoDto;
import br.com.locahouse.exception.dto.ExceptionDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Cômodos de imóveis", description = "- Endpoints para gerenciamento de cômodos dos imóveis.")
public interface ComodoDoImovelControllerDoc {

    @Operation(summary = "- Cadastrar.", description = "Realiza o cadastro de um cômodo de um imóvel no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cômodo cadastrado."),
            @ApiResponse(responseCode = "400", description = "Requisição inválida.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class))),
            @ApiResponse(responseCode = "401", description = "Autenticação necessária.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class))),
            @ApiResponse(responseCode = "403", description = "Acesso não autorizado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class))),
            @ApiResponse(responseCode = "409", description = "Tipo de cômodo já cadastrado para o imóvel.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class)))
    })
    ResponseEntity<Void> cadastrar(Integer imovelId, ComodoDoImovelCadastroAndAtualizacaoDto dto);


    @Operation(summary = "- Buscar pelo ID.", description = "Busca um cômodo de um imóvel pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cômodo encontrado."),
            @ApiResponse(responseCode = "401", description = "Autenticação necessária.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class))),
            @ApiResponse(responseCode = "403", description = "Acesso não autorizado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class))),
            @ApiResponse(responseCode = "404", description = "Cômodo não encontrado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class)))
    })
    ResponseEntity<ComodoDoImovelBuscaDto> buscarPeloId(Integer id);


    @Operation(summary = "- Atualizar cadastro.", description = "Atualiza o cadastro de um cômodo de um imóvel.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atualização realizada."),
            @ApiResponse(responseCode = "400", description = "Requisição inválida.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class))),
            @ApiResponse(responseCode = "401", description = "Autenticação necessária.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class))),
            @ApiResponse(responseCode = "403", description = "Acesso não autorizado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class))),
            @ApiResponse(responseCode = "409", description = "Tipo de cômodo já cadastrado para o imóvel.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class)))
    })
    ResponseEntity<ComodoDoImovelBuscaDto> atualizar(Integer id, ComodoDoImovelCadastroAndAtualizacaoDto dto);


    @Operation(summary = "- Deletar cadastro.", description = "Deleta o cadastro de um cômodo de um imóvel.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deleção realizada."),
            @ApiResponse(responseCode = "401", description = "Autenticação necessária.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class))),
            @ApiResponse(responseCode = "403", description = "Acesso não autorizado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class)))
    })
    ResponseEntity<Void> deletar(Integer id);
}
