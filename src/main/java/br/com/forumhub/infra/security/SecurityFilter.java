package br.com.forumhub.infra.security;

import br.com.forumhub.domain.usuario.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// REALIZA O FILTRO UMA VEZ, POR REQUISIÇÃO
@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // ******* PARA CADA REQUISIÇÃO FAÇA ESTE FILTRO *******
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        System.out.println("LOG: Filtro executado para: " + request.getRequestURI());
        System.out.println("LOG: Header Authorization: " + request.getHeader("Authorization"));

        // Recupera o token, a partir da requisição
        var tokenJwt = recuperarToken(request);

        // SE houver token, faça:
        if (tokenJwt!= null) {
            // Valida este token e recupera seu usuário
            var subject = tokenService.validarToken(tokenJwt);

            // Como o Email é o login do usuário, procura este
            // login (considerando que nosso token obrigatoriamente
            // necessita haver um, para ser válido)
            var usuario = usuarioRepository.findByEmailAndAtivoTrue(subject);

            // Como não há necessidade de credenciais neste projeto, criamos
            // uma autenticação com credenciais de valor nulo
            var authentication = new UsernamePasswordAuthenticationToken(
                    usuario, null, usuario.getAuthorities());

            // Altera o contexto para 'autenticado'
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // Se não, o Spring Security valida se é uma requisição de tipo
        // a não precisar de autenticação (ex.: POST /login)


        // Chama o próximo filtro da requisição
        filterChain.doFilter(request, response);

    }


    // ******* RECUPERAR TOKEN DA REQUISIÇÃO *******
    private String recuperarToken(HttpServletRequest http){
        // Recupera o token do cabeçalho de authorization
        var cabecalhoDeAutorizacao = http.getHeader("Authorization");

        // SE for diferente de null, retorna o String removendo o "Bearer "
        // do token
        if (cabecalhoDeAutorizacao != null) {
            return cabecalhoDeAutorizacao.replace("Bearer ", "");
        }

        // Se não houver nada, retorna nulo
        return null;
    }
}
