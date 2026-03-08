package br.com.forumhub.domain.topico;

public record DadosListagemTopico(
        String titulo,
        String mensagem,
        String dataCriacao,
        String status,
        String autor,
        String curso
) {

    // ******* CRIA UMA LISTAGEM *******
    public DadosListagemTopico(Topico dados){
        this(
                dados.getTitulo(),
                dados.getMensagem(),
                dados.getDataCriacao().toString(),
                dados.getStatus(),
                dados.getAutor().getNome(),
                dados.getCurso().getNome()
        );
    }

}
