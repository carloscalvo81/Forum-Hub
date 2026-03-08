package br.com.forumhub.domain.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService implements UserDetailsService {

    @Autowired
    private UsuarioRepository repository;

    // ******* SERVIÇO DE BUSCA DE USUÁRIO POR LOGIN VALIDADO *******
    @Override
    public UserDetails loadUserByUsername(String login)
            throws UsernameNotFoundException {

        var usuario = repository.findByEmailAndAtivoTrue(login);

        //////////////////////
        // ESTE EH UM TRECHO DE TESTE DE AUTENTICACAO - eliminar no DEPLOY
        System.out.println("LOG: Buscando usuário: " + login);
        var encoder = new BCryptPasswordEncoder();
        boolean confere = encoder.matches(
                "12345678",
                "$2a$10$e0NRo4oS8gJtIhXHITVEkOa8WcN6i/K3PaY5E9OQj1YxDCEV4VpW6"
        );

        System.out.println("Senha confere? " + confere);
        System.out.println("ESTÁ AQUI O HAH CORRETO:");
        System.out.println(new BCryptPasswordEncoder().encode("12345678"));
        //////////////////////

        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário com login não encontrado ou inativo");
        }

        return usuario;
    }
}
