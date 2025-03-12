package br.com.locahouse.controller;

import br.com.locahouse.controller.dto.token.TokenDto;
import br.com.locahouse.controller.dto.usuario.*;
import br.com.locahouse.mapper.UsuarioMapper;
import br.com.locahouse.model.Usuario;
import br.com.locahouse.service.UsuarioService;
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

@Tag(name = "Usuários", description = "- Endpoints para gerenciamento de usuários.")
@RequestMapping("/api/v1/usuarios")
@RestController
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Operation(
            description = "Realiza o cadastro do usuário no sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Usuário cadastrado."
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Requisição inválida."
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "CPF, telefone ou e-mail já cadastrados."
            ),
            @ApiResponse(
                    responseCode = "422",
                    description = "Usuário menor de idade."
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor."
            )
    })
    @PostMapping("/cadastrar")
    public ResponseEntity<TokenDto> cadastrar(@Valid @RequestBody UsuarioCadastroDto dto) {
        Usuario usuario = this.usuarioService.cadastrar(UsuarioMapper.usuarioCadastroDtoToEntity(dto));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(location).body(new TokenDto(usuario.getId(), this.usuarioService.fazerLogin(dto.email(), dto.senha())));
    }

    @Operation(
            description = "Realiza o login do usuário no sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Login realizado."
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Usuário inexistente ou senha inválida."
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor."
            )
    })
    @PostMapping("/login")
    public ResponseEntity<TokenDto> fazerLogin(@RequestBody UsuarioLoginDto dto) {
        String token = this.usuarioService.fazerLogin(dto.email(), dto.senha());
        return ResponseEntity.ok(new TokenDto(this.usuarioService.buscarPeloEmail(dto.email()).getId(), token));
    }

    @Operation(
            description = "Busca o usuário pelo ID."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuário encontrado."
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
                    description = "Usuário não encontrado."
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor."
            )
    })
    @GetMapping("/buscar/{id}")
    public ResponseEntity<UsuarioBuscaDto> buscarPeloId(@PathVariable Integer id) {
        return ResponseEntity.ok(UsuarioMapper.entityToUsuarioBuscaDto(this.usuarioService.buscarPeloId(id)));
    }

    @Operation(
            description = "Atualiza o cadastro do usuário."
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
                    description = "CPF, telefone ou e-mail já cadastrados."
            ),
            @ApiResponse(
                    responseCode = "422",
                    description = "Usuário menor de idade."
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor."
            )
    })
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<UsuarioBuscaDto> atualizar(@PathVariable Integer id, @Valid @RequestBody UsuarioAtualizacaoDto dto) {
        Usuario usuario = this.usuarioService.atualizar(id, UsuarioMapper.usuarioAtualizacaoDtoToEntity(dto));
        return ResponseEntity.ok(UsuarioMapper.entityToUsuarioBuscaDto(usuario));
    }

    @Operation(
            description = "Atualiza a senha do cadastro do usuário."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Senha atualizada."
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Autenticação necessária ou senha atual incorreta."
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
    @PatchMapping("/atualizar-senha/{id}")
    public ResponseEntity<Void> atualizarSenha(@PathVariable Integer id, @Valid @RequestBody UsuarioAtualizacaoSenhaDto dto) {
        this.usuarioService.atualizarSenha(id, dto.senhaAtual(), dto.novaSenha());
        return ResponseEntity.noContent().build();
    }

    @Operation(
            description = "Deleta o cadastro do usuário."
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
                    responseCode = "404",
                    description = "Usuário não encontrado."
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor."
            )
    })
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        this.usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
