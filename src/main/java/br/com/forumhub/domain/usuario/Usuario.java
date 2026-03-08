package br.com.forumhub.domain.usuario;

import br.com.forumhub.domain.topico.Topico;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.annotation.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity(name = "Usuario")
@Table(name = "usuarios")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String senha;

    @OneToMany(mappedBy = "autor", fetch = FetchType.LAZY)
    private List<Topico> topicosDoUsuario = new ArrayList<>();

    private boolean ativo;

    private static final BCryptPasswordEncoder encriptador = new BCryptPasswordEncoder();

    public Usuario(DadosCadastroUsuario dados){
        this.nome = dados.nome();
        this.email = dados.email();
        this.senha = gerarSenhaHash(dados.senha());
        this.ativo = true;
    }

    // ******* ENCRIPTADOR SENHA *******
    private String gerarSenhaHash(String senha){
        return encriptador.encode(senha);
    }

    public void deletar() {
        this.ativo = false;
    }

    // RETORNO DOS PALEIS (ROLES) DO USUÁRIO
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public @Nullable String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        //return UserDetails.super.isAccountNonExpired();
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        //return UserDetails.super.isAccountNonLocked();
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        //return UserDetails.super.isCredentialsNonExpired();
        return true;
    }

    @Override
    public boolean isEnabled() {
        //return UserDetails.super.isEnabled();
        return true;
    }

    // ******* ATUALIZA OS DADOS DO USUÁRIO ====
    public void atualizar(DadosAlterarUsuario dados) {

        if (dados.nome() != null){
            this.nome = dados.nome();
        }

        if (dados.senha() != null) {
            this.senha = gerarSenhaHash(dados.senha());
        }
    }
}
