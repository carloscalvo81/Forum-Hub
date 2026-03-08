package br.com.forumhub.domain.usuario;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DadosAlterarUsuario(
        @NotNull
        Long id,

        String nome,

        @Pattern(regexp = ".{8,}$")
        String senha
) {
}
