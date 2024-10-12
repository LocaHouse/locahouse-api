package br.com.locahouse.controller;

import br.com.locahouse.dto.jwt.JwtTokenDto;
import br.com.locahouse.dto.usuario.*;
import br.com.locahouse.mapper.UsuarioMapper;
import br.com.locahouse.model.Usuario;
import br.com.locahouse.service.UsuarioService;
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

    @PostMapping("/login")
    public ResponseEntity<JwtTokenDto> fazerLogin(@RequestBody UsuarioPostLoginDto dto) {
        String token = this.usuarioService.fazerLogin(dto.email(), dto.senha());
        return new ResponseEntity<>(new JwtTokenDto(token), HttpStatus.OK);
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<UsuarioGetDto> cadastrar(@Valid @RequestBody UsuarioPostDto dto) {
        Usuario usuario = this.usuarioService.cadastrar(UsuarioMapper.usuarioPostDtoToEntity(dto));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(location).body(UsuarioMapper.entityToUsuarioGetDto(usuario));
    }

    @GetMapping("buscar/{id}")
    public ResponseEntity<UsuarioGetDto> buscarPeloId(@PathVariable Integer id) {
        return ResponseEntity.ok(UsuarioMapper.entityToUsuarioGetDto(this.usuarioService.buscarPeloId(id)));
    }

    @PutMapping("atualizar/{id}")
    public ResponseEntity<UsuarioGetDto> atualizar(@PathVariable Integer id, @Valid @RequestBody UsuarioPutDto dto) {
        Usuario usuario = this.usuarioService.atualizar(id, UsuarioMapper.usuarioPutDtoToEntity(dto));
        return ResponseEntity.ok(UsuarioMapper.entityToUsuarioGetDto(usuario));
    }

    @DeleteMapping("deletar/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        this.usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("atualizar-senha/{id}")
    public ResponseEntity<Void> atualizarSenha(@PathVariable Integer id, @Valid @RequestBody UsuarioPatchAtualizacaoSenhaDto dto) {
        this.usuarioService.atualizarSenha(id, dto.senhaAtual(), dto.novaSenha());
        return ResponseEntity.ok().build();
    }
}
