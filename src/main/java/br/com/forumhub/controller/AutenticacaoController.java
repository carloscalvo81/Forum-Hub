package br.com.forumhub.controller;

import br.com.forumhub.domain.usuario.DadosLoginUsuario;
import br.com.forumhub.domain.usuario.Usuario;
import br.com.forumhub.domain.usuario.UsuarioRepository;
import br.com.forumhub.infra.security.DadosToken;
import br.com.forumhub.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    // ******* LOGIN DE USUÁRIO CADASTRADO *******
    @PostMapping
    public ResponseEntity login(@RequestBody @Valid DadosLoginUsuario dados) {

        System.out.println("LOG: ENTROU NO LOGIN CONTROLLER");

        var tokenAutenticacao = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        var autenticacao = manager.authenticate(tokenAutenticacao);
        var tokenJWT = tokenService.gerarToken((Usuario) autenticacao.getPrincipal());
        return ResponseEntity.ok(new DadosToken(tokenJWT));
    }

}