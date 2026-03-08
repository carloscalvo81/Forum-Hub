package br.com.forumhub.controller;

import br.com.forumhub.domain.ValidacaoException;
import br.com.forumhub.domain.usuario.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    // ******* CADASTRAR USUÁRIO NO BANCO DE DADOS *******
    @PostMapping
    @Transactional
    public ResponseEntity cadastrarUsuario(
            @RequestBody @Valid DadosCadastroUsuario dados,
            UriComponentsBuilder uriBuilder){

        if (repository.existsByEmail(dados.email())) {
            throw new ValidacaoException("Email já cadastrado na aplicação.");
        }

        var usuario = new Usuario(dados);

        repository.save(usuario);

        var uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoUsuario(usuario));
    }

    // ******* DETALHAR USUÁRIO *******
    @GetMapping("/{id}")
    public ResponseEntity detalharUsuario(@PathVariable Long id){

        var usuario = repository.getReferenceById(id);

        return ResponseEntity.ok(new DadosDetalhamentoUsuario(usuario));
    }

    // ******* DELETAR USUÁRIO *******
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deletarUsuario(@PathVariable Long id){

        var usuario = repository.getReferenceById(id);
        usuario.deletar();

         return ResponseEntity.noContent().build();
    }

    // ******* ALTERAR USUÁRIO *******
    @PutMapping
    @Transactional
    public ResponseEntity alterarUsuario(@RequestBody @Valid DadosAlterarUsuario dados) {

        Long id = dados.id();

        if (!repository.existsUsuarioByIdAndAtivoTrue(id)) {
            throw new ValidacaoException("O usuário não existe no sistema.");
        }

        var usuario = repository.getReferenceById(id);
        usuario.atualizar(dados);

        return ResponseEntity.ok(new DadosDetalhamentoUsuario(usuario));
    }
}
