package br.com.locahouse.controller;

import br.com.locahouse.dto.jwt.JwtTokenDto;
import br.com.locahouse.dto.usuario.*;
import br.com.locahouse.mapper.UsuarioMapper;
import br.com.locahouse.model.Usuario;
import br.com.locahouse.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequestMapping("/api/v1/usuarios")
@RestController
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    private UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
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
    public ResponseEntity<JwtTokenDto> fazerLogin(@RequestBody UsuarioPostLoginDto dto) {
        String token = this.usuarioService.fazerLogin(dto.email(), dto.senha());
        return new ResponseEntity<>(new JwtTokenDto(token), HttpStatus.OK);
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
    public ResponseEntity<UsuarioGetDto> cadastrar(@Valid @RequestBody UsuarioPostDto dto) {
        Usuario usuario = this.usuarioService.cadastrar(UsuarioMapper.usuarioPostDtoToEntity(dto));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(location).body(UsuarioMapper.entityToUsuarioGetDto(usuario));
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
    @GetMapping("buscar/{id}")
    public ResponseEntity<UsuarioGetDto> buscarPeloId(@PathVariable Integer id) {
        return ResponseEntity.ok(UsuarioMapper.entityToUsuarioGetDto(this.usuarioService.buscarPeloId(id)));
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
    @PutMapping("atualizar/{id}")
    public ResponseEntity<UsuarioGetDto> atualizar(@PathVariable Integer id, @Valid @RequestBody UsuarioPutDto dto) {
        Usuario usuario = this.usuarioService.atualizar(id, UsuarioMapper.usuarioPutDtoToEntity(dto));
        return ResponseEntity.ok(UsuarioMapper.entityToUsuarioGetDto(usuario));
    }

    @Operation(
            description = "Atualiza o cadastro do usuário."
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
    @DeleteMapping("deletar/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        this.usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            description = "Atualiza a senha do cadastro do usuário."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
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
    @PatchMapping("atualizar-senha/{id}")
    public ResponseEntity<Void> atualizarSenha(@PathVariable Integer id, @Valid @RequestBody UsuarioPatchAtualizacaoSenhaDto dto) {
        this.usuarioService.atualizarSenha(id, dto.senhaAtual(), dto.novaSenha());
        return ResponseEntity.ok().build();
    }
}
