package br.com.forumhub.domain.usuario;

public record DadosDetalhamentoUsuario(
        Long id,
        String nome,
        String email
) {
    // ******* CRIA DETALHES COM BASE NO USUÁRIO *******
    public DadosDetalhamentoUsuario(Usuario dados){
        this(dados.getId(), dados.getNome(), dados.getEmail());
    }
}
