package br.com.forumhub.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    private SecurityFilter filtroDeSeguranca;

    // ******* CONFIGURAÇÕES DE SEGURANÇA *******
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(
                        // Política de login Stateless
                        s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(req -> {

                    // Acesso liberado para a requisição POST em /login
                    req.requestMatchers(HttpMethod.POST, "/login").permitAll();
                    // Acesso liberado para cadastro de novos usuários no sistema
                    req.requestMatchers(HttpMethod.POST, "/usuarios").permitAll();
                    // Swagger / OpenAPI
                    req.requestMatchers(
                            "/v3/api-docs/**",
                            "/swagger-ui.html",
                            "/swagger-ui/**"
                    ).permitAll();
                    // Acesso ao restante, apenas autenticado
                    req.anyRequest().authenticated();
                })
                // Adicionando nosso filtro de segurança, antes do de autenticação do Spring
                .addFilterBefore(filtroDeSeguranca, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    // ******* RETORNO SEGURO DE GERENTE DE AUTENTICAÇÃO *******
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    // ******* ENCRIPTADOR PARA SENHAS ====
    @Bean
    public PasswordEncoder encriptador() {
        return new BCryptPasswordEncoder();
    }

}
