package br.com.locahouse.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioPostLoginDto(

        @NotBlank(message = "O e-mail é obrigatório.")
        @Size(message = "O e-mail pode ter no máximo 255 caracteres.", max = 255)
        @Email(message = "O e-mail deve ser válido.")
        String email,

        @NotBlank(message = "A senha é obrigatória.")
        @Size(message = "A senha deve ter entre 8 a 60 caracteres.", min = 8, max = 60)
        String senha
) {
}
