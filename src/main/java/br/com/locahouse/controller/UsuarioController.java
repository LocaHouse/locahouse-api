package br.com.locahouse.controller;

import br.com.locahouse.controller.doc.UsuarioControllerDoc;
import br.com.locahouse.controller.dto.usuario.*;
import br.com.locahouse.model.Usuario;
import br.com.locahouse.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static br.com.locahouse.mapper.UsuarioMapper.*;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController implements UsuarioControllerDoc {

    private final UsuarioService service;

    @Autowired
    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @Override
    @PostMapping("/cadastrar")
    public ResponseEntity<UsuarioTokenDto> cadastrar(@Valid @RequestBody UsuarioCadastroDto dto) {
        Usuario usuario = this.service.cadastrar(usuarioCadastroDtoToEntity(dto));
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(usuario.getId()).toUri()).body(new UsuarioTokenDto(usuario.getId(), this.service.fazerLogin(dto.email(), dto.senha())));
    }

    @Override
    @PostMapping("/login")
    public ResponseEntity<UsuarioTokenDto> fazerLogin(@RequestBody UsuarioLoginDto dto) {
        return ResponseEntity.ok(new UsuarioTokenDto(this.service.buscarPeloEmail(dto.email()).getId(), this.service.fazerLogin(dto.email(), dto.senha())));
    }

    @Override
    @GetMapping("/buscar/{id}")
    public ResponseEntity<UsuarioBuscaDto> buscarPeloId(@PathVariable Integer id) {
        return ResponseEntity.ok(entityToUsuarioBuscaDto(this.service.buscarPeloId(id)));
    }

    @Override
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<UsuarioBuscaDto> atualizar(@PathVariable Integer id, @Valid @RequestBody UsuarioAtualizacaoDto dto) {
        return ResponseEntity.ok(entityToUsuarioBuscaDto(this.service.atualizar(id, usuarioAtualizacaoDtoToEntity(dto))));
    }

    @Override
    @PatchMapping("/atualizar-senha/{id}")
    public ResponseEntity<Void> atualizarSenha(@PathVariable Integer id, @Valid @RequestBody UsuarioAtualizacaoSenhaDto dto) {
        this.service.atualizarSenha(id, dto.senhaAtual(), dto.novaSenha());
        return ResponseEntity.noContent().build();
    }

    @Override
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        this.service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
