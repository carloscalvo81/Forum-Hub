# ForumHub API

API REST desenvolvida com **Spring Boot** para gerenciamento de tópicos
de um fórum.

Este projeto foi criado como prática de desenvolvimento backend
utilizando Java, Spring Boot, JPA, autenticação com JWT e boas práticas
de arquitetura em APIs REST.

------------------------------------------------------------------------

## Tecnologias utilizadas

-   Java 17
-   Spring Boot
-   Spring Security
-   JWT (JSON Web Token)
-   Spring Data JPA
-   Maven
-   MySQL / H2
-   Flyway (migrations de banco)
-   IntelliJ IDEA

------------------------------------------------------------------------

## Estrutura do projeto

    controller      -> endpoints da API
    domain          -> entidades e DTOs
    repository      -> acesso ao banco de dados
    infra/security  -> configuração de segurança e JWT
    infra/exception -> tratamento de erros

------------------------------------------------------------------------

## Funcionalidades

-   Cadastro de tópicos
-   Listagem de tópicos com paginação
-   Busca de tópico por ID
-   Atualização de tópico
-   Exclusão de tópico
-   Filtro de tópicos por curso
-   Filtro de tópicos por status
-   Autenticação com JWT

------------------------------------------------------------------------

## Endpoints principais

### Autenticação

POST /login

Retorna um token JWT para acessar os endpoints protegidos.

------------------------------------------------------------------------

### Tópicos

Criar tópico

POST /topicos

Listar tópicos

GET /topicos

Detalhar tópico

GET /topicos/{id}

Atualizar tópico

PUT /topicos/{id}

Excluir tópico

DELETE /topicos/{id}

------------------------------------------------------------------------

## Paginação

A listagem de tópicos suporta paginação:

    GET /topicos?page=0&size=10

------------------------------------------------------------------------

## Como executar o projeto

### 1. Clonar ou baixar o projeto

    git clone <repositorio>

### 2. Abrir no IntelliJ

-   File
-   Open
-   Selecionar a pasta do projeto

### 3. Instalar dependências

O Maven fará o download automático.

### 4. Executar aplicação

Rodar a classe:

    ForumhubApplication.java

ou via terminal:

    mvn spring-boot:run

------------------------------------------------------------------------

## Melhorias futuras

-   Sistema de respostas nos tópicos
-   Contagem de respostas
-   Endpoint "meus tópicos"
-   Documentação com Swagger
-   Testes automatizados
-   Docker para deploy

------------------------------------------------------------------------

## Autor

Projeto desenvolvido para fins de estudo em **Spring Boot e APIs REST**.
