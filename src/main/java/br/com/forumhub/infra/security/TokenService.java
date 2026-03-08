package br.com.forumhub.infra.security;

import br.com.forumhub.domain.ValidacaoException;
import br.com.forumhub.domain.usuario.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    // CHAVE SECRETA
    @Value("${api.security.token.jwt}")
    private String secret;

    // ******* GERA TOKEN JWT *******
    public String gerarToken(Usuario usuario){

        // Declaração do token
        String token;

        // Geração do token
        try {
            Algorithm algoritmo = Algorithm.HMAC256(secret);
            token = JWT.create()
                    .withIssuer("API Forum Hub")
                    .withSubject(usuario.getEmail())
                    .withClaim("id", usuario.getId())
                    .withExpiresAt(gerarExpiracao())
                    .sign(algoritmo);

        } catch (JWTCreationException exception){
            throw new ValidacaoException("Erro na criação do token JWT");
        }

        return token;
    }

    // ******* VALIDA E RETORNA O USUÁRIO DO TOKEN *******
    public String validarToken(String tokenJwt){
        try {
            // Cria o algoritmo JWT secreto
            Algorithm algoritmo = Algorithm.HMAC256(secret);

            // Retorna o subject do token recebido, se for válido
            return JWT.require(algoritmo)
                    .withIssuer("API Forum Hub")
                    .build()
                    .verify(tokenJwt)
                    .getSubject();

            // Caso for inválido:
        } catch (JWTVerificationException exception){
            throw new ValidacaoException("Token JWT inválido ou expirado.");
        }
    }

    // ******* GERAR DATA DE EXPIRAÇÃO DUAS HORAS DEPOIS DA GERAÇÃO DO TOKEN ====
    private Instant gerarExpiracao(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

}
