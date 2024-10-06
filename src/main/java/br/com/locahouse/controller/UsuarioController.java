package br.com.locahouse.controller;

import br.com.locahouse.dto.usuario.UsuarioGetDto;
import br.com.locahouse.dto.usuario.UsuarioPostDto;
import br.com.locahouse.dto.usuario.UsuarioPutDto;
import br.com.locahouse.mapper.UsuarioMapper;
import br.com.locahouse.model.Usuario;
import br.com.locahouse.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping
    public ResponseEntity<UsuarioGetDto> cadastrar(@Valid @RequestBody UsuarioPostDto dto) {
        Usuario usuario = usuarioService.cadastrar(UsuarioMapper.usuarioPostDtoToEntity(dto));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(location).body(UsuarioMapper.entityToUsuarioGetDto(usuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioGetDto> atualizar(@PathVariable Integer id, @Valid @RequestBody UsuarioPutDto dto) {
        Usuario usuario = usuarioService.atualizar(id, UsuarioMapper.usuarioPutDtoToEntity(dto));
        return ResponseEntity.ok(UsuarioMapper.entityToUsuarioGetDto(usuario));
    }
}
