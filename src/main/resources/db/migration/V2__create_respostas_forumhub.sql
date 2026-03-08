-- V2__create_respostas_forumhub.sql

CREATE TABLE respostas (
   id BIGINT NOT NULL AUTO_INCREMENT,
   mensagem TEXT NOT NULL,
   data_criacao DATETIME NOT NULL,
   autor_id BIGINT NOT NULL,
   topico_id BIGINT NOT NULL,
   PRIMARY KEY (id)
);
