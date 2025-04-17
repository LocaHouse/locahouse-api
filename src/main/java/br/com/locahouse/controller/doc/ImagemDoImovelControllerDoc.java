package br.com.locahouse.controller.doc;

import br.com.locahouse.exception.handler.dto.ExceptionDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Imagens de imóveis", description = "- Endpoints para gerenciamento de imagens dos imóveis.")
public interface ImagemDoImovelControllerDoc {


    @Operation(summary = "- Fazer Upload.", description = "Realiza o upload de uma imagem de um imóvel no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Imagem cadastrada."),
            @ApiResponse(responseCode = "400", description = "Requisição inválida.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class))),
            @ApiResponse(responseCode = "401", description = "Autenticação necessária.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class))),
            @ApiResponse(responseCode = "403", description = "Acesso não autorizado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class)))
    })
    ResponseEntity<Void> cadastrar(Integer imovelId, MultipartFile imagem);

    @Operation(summary = "- Remover.", description = "Remove uma imagem de um imóvel do sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Remoção realizada."),
            @ApiResponse(responseCode = "401", description = "Autenticação necessária.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class))),
            @ApiResponse(responseCode = "403", description = "Acesso não autorizado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class)))
    })
    ResponseEntity<Void> deletar(Integer id);
}
