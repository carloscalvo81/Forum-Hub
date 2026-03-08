package br.com.forumhub.controller;

import br.com.forumhub.domain.ValidacaoException;
import br.com.forumhub.domain.topico.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private CadastrarTopico cadastroTopico;

    @Autowired
    private TopicoRepository repository;

    // ******* POST DE CADASTRO DE TÓPICOS *******
    @PostMapping
    @Transactional
    public ResponseEntity cadastrarTopico(
            @RequestBody @Valid DadosCadastroTopico dados,
            UriComponentsBuilder uriBuilder) {
        var topico = cadastroTopico.cadastrar(dados);
        var uri = uriBuilder.path("/topicos/{id}")
                .buildAndExpand(topico.id()).toUri();
        return ResponseEntity.created(uri).body(topico);
    }

    // ******* LISTAGEM DE TODOS OS TÓPICOS *******
    @GetMapping
    public ResponseEntity<Page<DadosListagemTopico>> listarTopicos(
            @PageableDefault(size = 10, sort = {"titulo"}, page = 0) Pageable page) {

        var paginacao = repository.findAll(page).map(DadosListagemTopico::new);
        return ResponseEntity.ok(paginacao);
    }

    // ******* DETALHAMENTO DE TÓPICOS *******
    @GetMapping("/{id}")
    public ResponseEntity detalharTopico(@PathVariable Long id) {

        if (!repository.existsById(id)) {
            throw new ValidacaoException("Id de tópico inexistente.");
        }

        var topico = repository.getReferenceById(id);

        return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));
    }

    // ******* ATUALIZAR TÓPICOS *******
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity atualizarTopico(
            @PathVariable Long id,
            @RequestBody DadosAtualizacaoTopico dados) {

        var topico = repository.findById(id)
                .orElseThrow(() ->
                        new ValidacaoException("Tópico inexistente no banco de dados.")
                );

        topico.atualizar(dados);

        return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));
    }

    // ******* DELETAR TÓPICOS *******
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deletarTopico(@PathVariable Long id) {

        if (!repository.existsById(id)) {
            throw new ValidacaoException("Nenhum tópico existente com este id no banco de dados");
        }

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
