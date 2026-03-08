package br.com.forumhub.domain.curso;

public record DadosDetalhamentoCurso(
        Long id,
        String nome,
        Categoria categoria
) {
    public DadosDetalhamentoCurso(Curso dados){
        this(dados.getId(), dados.getNome(), dados.getCategoria());
    }
}
