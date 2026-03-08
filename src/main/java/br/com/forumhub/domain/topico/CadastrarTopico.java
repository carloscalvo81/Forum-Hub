package br.com.forumhub.domain.topico;

import br.com.forumhub.domain.ValidacaoException;
import br.com.forumhub.domain.curso.CursoRepository;
import br.com.forumhub.domain.topico.validacoes.ValidaCadastroTopico;
import br.com.forumhub.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CadastrarTopico {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private List<ValidaCadastroTopico> validacao;

    // ******* CADASTRO DE TÓPICO *******
    public DadosDetalhamentoTopico cadastrar(DadosCadastroTopico dados) {

        if (!usuarioRepository.existsById(dados.idUsuario())) {
            throw new ValidacaoException("Usuário com ID não encontrado.");
        }
        if (!cursoRepository.existsById(dados.idCurso())) {
            throw new ValidacaoException("Curso com ID não encontrado");
        }

        validacao.forEach(e -> e.validar(dados));

        var autor = usuarioRepository.getReferenceById(dados.idUsuario());

        var curso = cursoRepository.getReferenceById(dados.idCurso());

        LocalDateTime dataCriacao = LocalDateTime.now();

        var topico = new Topico(
                null, dados.titulo(), dados.mensagem(),
                dataCriacao, "NÃO RESPONDIDO", autor, curso
        );

        topicoRepository.save(topico);

        return new DadosDetalhamentoTopico(topico);

    }
}
