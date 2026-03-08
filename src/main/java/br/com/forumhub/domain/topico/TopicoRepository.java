package br.com.forumhub.domain.topico;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

    // ******* VERIFICA SE EXISTE MENSAGEM E TITULO IGUAL NO BANCO DE DADOS *******
    boolean existsByMensagemAndTitulo(String mensagem, String titulo);
}
