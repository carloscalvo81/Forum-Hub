package br.com.forumhub.controller;

import br.com.forumhub.domain.ValidacaoException;
import br.com.forumhub.domain.curso.*;
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
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoRepository repository;

    // ******* CRIAR CURSO *******
    @PostMapping
    @Transactional
    public ResponseEntity criarCurso(
            @RequestBody @Valid DadosCadastroCurso dados
            , UriComponentsBuilder uriBuilder
    ) {
        // Recupera as informações do body do curso, e o salva
        var curso = new Curso(dados);
        repository.save(curso);

        // Cria um caminho para visualiação do curso
        var uri = uriBuilder.path("/cursos/{id}").buildAndExpand(curso.getId()).toUri();

        // Retorna código 204 com o caminho para visualiação e os detalhes do curso
        return ResponseEntity.created(uri).body(new DadosDetalhamentoCurso(curso));
    }

    // ******* DETALHAR CURSO *******
    @GetMapping("/{id}")
    public ResponseEntity detalharCurso(@PathVariable Long id) {

        // Pega a referência do id, e retorna um código 200 com os detalhes do curso
        var curso = repository.getReferenceById(id);

        return ResponseEntity.ok(new DadosDetalhamentoCurso(curso));
    }

    // ******* LISTAR CURSOS *******
    @GetMapping
    public ResponseEntity<Page<DadosListagemCurso>> listarCurso(
            @PageableDefault(sort = {"nome"}, size = 10, page = 0) Pageable page) {

        // Com base no padrão da lista, cria uma lista de DadosListagemCurso
        // com base nos cursos existentes na aplicação
        var listaCursos = repository.findAll(page).map(DadosListagemCurso::new);

        // Retorna código ok 200 + lista dos cursos cadastrados no sistema
        return ResponseEntity.ok(listaCursos);
    }

    // ******* ALTERAR CURSO *******
    @PutMapping
    @Transactional
    public ResponseEntity alterarCurso(@RequestBody @Valid DadosAlterarCurso dados) {

        Long id = dados.id();
        var cursoRef = repository.findById(id);
        if (cursoRef.isEmpty()) {
            throw new ValidacaoException("Curso inexistente no banco de dados");
        }
        var curso = cursoRef.get();
        curso.atualizar(dados);
        return ResponseEntity.ok(new DadosDetalhamentoCurso(curso));
    }
}