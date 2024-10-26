package br.com.locahouse.dto.usuario;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record UsuarioCadastroDto(

        @NotBlank(message = "O CPF é obrigatório.")
        @Size(message = "O CPF deve ter 14 caracteres.", min = 14, max = 14)
        String cpf,

        @NotBlank(message = "O nome é obrigatório.")
        @Size(message = "O nome pode ter no máximo 255 caracteres.", max = 255)
        String nome,

        @NotNull(message = "A data de nascimento é obrigatória.")
        @PastOrPresent(message = "A data de nascimento não pode ser superior à atual.")
        @JsonProperty("data_nascimento")
        LocalDate dataNascimento,

        @NotBlank(message = "O telefone é obrigatório.")
        @Size(message = "O telefone deve ter 15 caracteres.", min = 15, max = 15)
        String telefone,

        @NotBlank(message = "O e-mail é obrigatório.")
        @Size(message = "O e-mail pode ter no máximo 255 caracteres.", max = 255)
        @Email(message = "O e-mail deve ser válido.")
        String email,

        @NotBlank(message = "A senha é obrigatória.")
        @Size(message = "A senha deve ter entre 8 a 60 caracteres.", min = 8, max = 60)
        String senha
) {
}
