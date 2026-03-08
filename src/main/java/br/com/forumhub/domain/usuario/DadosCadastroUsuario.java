package br.com.forumhub.domain.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DadosCadastroUsuario(
        @NotBlank
        String nome,
        @NotBlank @Email
        String email,
        //Minimo de 8 dígitos quaisquer
        @NotBlank @Pattern(regexp = ".{8,}$")
        String senha
) {
}
