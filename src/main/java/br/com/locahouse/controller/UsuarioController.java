package br.com.locahouse.controller;

import br.com.locahouse.controller.doc.UsuarioControllerDoc;
import br.com.locahouse.controller.dto.usuario.*;
import br.com.locahouse.mapper.UsuarioMapper;
import br.com.locahouse.model.Usuario;
import br.com.locahouse.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController implements UsuarioControllerDoc {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    @PostMapping("/cadastrar")
    public ResponseEntity<UsuarioTokenDto> cadastrar(@Valid @RequestBody UsuarioCadastroDto dto) {
        Usuario usuario = this.usuarioService.cadastrar(UsuarioMapper.usuarioCadastroDtoToEntity(dto));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(location).body(new UsuarioTokenDto(usuario.getId(), this.usuarioService.fazerLogin(dto.email(), dto.senha())));
    }

    @Override
    @PostMapping("/login")
    public ResponseEntity<UsuarioTokenDto> fazerLogin(@RequestBody UsuarioLoginDto dto) {
        String token = this.usuarioService.fazerLogin(dto.email(), dto.senha());
        return ResponseEntity.ok(new UsuarioTokenDto(this.usuarioService.buscarPeloEmail(dto.email()).getId(), token));
    }

    @Override
    @GetMapping("/buscar/{id}")
    public ResponseEntity<UsuarioBuscaDto> buscarPeloId(@PathVariable Integer id) {
        return ResponseEntity.ok(UsuarioMapper.entityToUsuarioBuscaDto(this.usuarioService.buscarPeloId(id)));
    }

    @Override
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<UsuarioBuscaDto> atualizar(@PathVariable Integer id, @Valid @RequestBody UsuarioAtualizacaoDto dto) {
        Usuario usuario = this.usuarioService.atualizar(id, UsuarioMapper.usuarioAtualizacaoDtoToEntity(dto));
        return ResponseEntity.ok(UsuarioMapper.entityToUsuarioBuscaDto(usuario));
    }

    @Override
    @PatchMapping("/atualizar-senha/{id}")
    public ResponseEntity<Void> atualizarSenha(@PathVariable Integer id, @Valid @RequestBody UsuarioAtualizacaoSenhaDto dto) {
        this.usuarioService.atualizarSenha(id, dto.senhaAtual(), dto.novaSenha());
        return ResponseEntity.noContent().build();
    }

    @Override
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        this.usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
