package br.com.locahouse.controller.dto.usuario;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioAtualizacaoSenhaDto(

        @NotBlank(message = "A senha atual é obrigatória.")
        @Size(message = "A senha atual deve ter entre 8 a 60 caracteres.", min = 8, max = 60)
        @JsonProperty(value = "senha_atual")
        String senhaAtual,

        @NotBlank(message = "A nova senha é obrigatória.")
        @Size(message = "A nova senha deve ter entre 8 a 60 caracteres.", min = 8, max = 60)
        @JsonProperty(value = "nova_senha")
        String novaSenha
) {
}
