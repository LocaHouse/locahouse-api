package br.com.locahouse.dto.usuario;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record UsuarioPutDto(

        @NotNull(message = "O ID é obrigatório.")
        @Positive(message = "O ID deve ser positivo.")
        Integer id,

        @NotBlank(message = "O CPF é obrigatório.")
        @Size(message = "O CPF deve ter 11 números.", min = 11, max = 11)
        String cpf,

        @NotBlank(message = "O nome é obrigatório.")
        @Size(message = "O nome pode ter no máximo 255 caracteres.", max = 255)
        String nome,

        @NotNull(message = "A data de nascimento é obrigatória.")
        @PastOrPresent(message = "A data de nascimento não pode ser superior à atual.")
        @JsonProperty("data_nascimento")
        LocalDate dataNascimento,

        @NotBlank(message = "O e-mail é obrigatório.")
        @Size(message = "O e-mail pode ter no máximo 255 caracteres.", max = 255)
        @Email(message = "O e-mail deve ser válido.")
        String email,

        @NotBlank(message = "O telefone é obrigatório.")
        @Size(message = "O telefone deve ter 11 números.", min = 11, max = 11)
        String telefone
) {
}
