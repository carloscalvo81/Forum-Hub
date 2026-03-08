-- V3__drop_respostas_forumhub.sql
-- Para simplificar trato com string
ALTER TABLE topicos MODIFY COLUMN mensagem VARCHAR(500) NOT NULL;
-- Para manter escopo mínimo
DROP TABLE IF EXISTS respostas;