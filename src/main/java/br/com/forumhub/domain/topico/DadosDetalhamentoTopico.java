package br.com.forumhub.domain.topico;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record DadosDetalhamentoTopico(
        Long id,
        String titulo,
        String mensagem,
        LocalDateTime dataDeCriacao,
        String status,
        String autor,
        Long idAutor,
        String curso,
        Long idCurso
) {
    public DadosDetalhamentoTopico(Topico dados){
        this(
                dados.getId(), dados.getTitulo(), dados.getMensagem(),
                dados.getDataCriacao(), dados.getStatus(),
                dados.getAutor().getNome(), dados.getAutor().getId(),
                dados.getCurso().getNome(), dados.getCurso().getId()
        );
    }
}
