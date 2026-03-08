package br.com.forumhub.infra.exception;

import br.com.forumhub.domain.ValidacaoException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class CatchErrors {

    // ******* NOT FOUND *******
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarErro404(){
        // Retorna um erro 404
        return ResponseEntity.notFound().build();
    }

    // ******* CAMPO INVÁLIDO *******
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarErro400(MethodArgumentNotValidException ex){

        // Recupera as mensagens de campos inválidos
        var erros = ex.getFieldErrors();

        // Retorna um erro 400 e retorna no corpo cada erro
        // encontrado junto da sua mensagem
        return ResponseEntity.badRequest().body(
                erros.stream().map(DadosErroException::new).toList()
        );
    }

    // ******* TIPO INVÁLIDO NA REQUISIÇÃO *******
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity tipoInvalidoNaRequisicao(){
        return ResponseEntity.badRequest().body("Tipo inválido na requisição.");
    }

    // ******* ERRO DE VALIDAÇÃO ====
    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity erroDeValidacao(ValidacaoException ex){
        // Para caso erro de validação encontrado, retorna a sua mensagem
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    // ******* BODY FALTANTE ====
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity bodyEstaFaltando(HttpMessageNotReadableException ex){

        // Caso a requisição precise de um body na API, e ela não
        // tiver, ou haver um erro no formato do JSON, retorna erro
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    // Record para recuperação de dados inválidos
    // Retorno para campos e mensagens
    private record DadosErroException(String campo, String mensagem){
        public DadosErroException(FieldError ex){
            this(ex.getField(), ex.getDefaultMessage());
        }
    }
}
