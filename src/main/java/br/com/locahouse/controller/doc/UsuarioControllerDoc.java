package br.com.locahouse.controller.doc;

import br.com.locahouse.controller.dto.usuario.*;
import br.com.locahouse.exception.handler.dto.ExceptionDto;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;

@Tag(name = "Usuários", description = "- Endpoints para gerenciamento de usuários.")
public interface UsuarioControllerDoc {

    @Operation(summary = "- Cadastrar.", description = "Realiza o cadastro do usuário no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário cadastrado."),
            @ApiResponse(responseCode = "400", description = "Requisição inválida.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class))),
            @ApiResponse(responseCode = "409", description = "CPF, telefone ou e-mail já cadastrado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class))),
            @ApiResponse(responseCode = "422", description = "Usuário menor de idade.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class)))
    })
    ResponseEntity<UsuarioTokenDto> cadastrar(UsuarioCadastroDto dto);


    @Operation(summary = "- Fazer login.", description = "Realiza o login do usuário no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login realizado."),
            @ApiResponse(responseCode = "401", description = "Usuário inexistente ou senha inválida.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class)))
    })
    ResponseEntity<UsuarioTokenDto> fazerLogin(UsuarioLoginDto dto);


    @Operation(summary = "Buscar pelo ID.", description = "Busca um usuário pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado."),
            @ApiResponse(responseCode = "401", description = "Autenticação necessária.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class))),
            @ApiResponse(responseCode = "403", description = "Acesso não autorizado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class)))
    })
    ResponseEntity<UsuarioBuscaDto> buscarPeloId(Integer id);


    @Operation(summary = "- Atualizar cadastro.", description = "Atualiza o cadastro do usuário.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atualização realizada."),
            @ApiResponse(responseCode = "400", description = "Requisição inválida.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class))),
            @ApiResponse(responseCode = "401", description = "Autenticação necessária.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class))),
            @ApiResponse(responseCode = "403", description = "Acesso não autorizado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class))),
            @ApiResponse(responseCode = "409", description = "CPF, telefone ou e-mail já cadastrado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class))),
            @ApiResponse(responseCode = "422", description = "Usuário menor de idade.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class)))
    })
    ResponseEntity<UsuarioBuscaDto> atualizar(Integer id, UsuarioAtualizacaoDto dto);


    @Operation(summary = "- Atualizar senha.", description = "Atualiza a senha do cadastro do usuário.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Senha atualizada."),
            @ApiResponse(responseCode = "401", description = "Autenticação necessária ou senha atual incorreta.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class))),
            @ApiResponse(responseCode = "403", description = "Acesso não autorizado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class)))
    })
    ResponseEntity<Void> atualizarSenha(Integer id, UsuarioAtualizacaoSenhaDto dto);


    @Operation(summary = "- Deletar cadastro.", description = "Deleta o cadastro do usuário.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deleção realizada."),
            @ApiResponse(responseCode = "401", description = "Autenticação necessária.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class))),
            @ApiResponse(responseCode = "403", description = "Acesso não autorizado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDto.class)))
    })
    ResponseEntity<Void> deletar(Integer id);
}
