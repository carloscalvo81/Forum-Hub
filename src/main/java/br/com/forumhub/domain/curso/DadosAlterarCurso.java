package br.com.forumhub.domain.curso;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosAlterarCurso(
        @NotNull
        Long id,

        @NotBlank
        String nome
) {
}
