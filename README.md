# 📚 API de Gerenciamento de Livros

Projeto desenvolvido com foco em consolidar conhecimentos no ecossistema
Java utilizando Spring Boot 3, abordando desde a construção de APIs REST
até boas práticas de arquitetura, segurança, integração com banco de
dados e deploy na nuvem AWS.

----------------------------------

## 🚀 Tecnologias utilizadas

-   Java 17+
-   Spring Boot 3
-   Spring Web
-   Spring Data JPA
-   Spring Security
-   JWT (JSON Web Token)
-   OAuth2
-   Hibernate
-   PostgreSQL
-   Docker
-   HikariCP (pool de conexões)
-   Swagger / OpenAPI
-   JUnit / Testes de integração

----------------------------------

## 📌 Funcionalidades

-   CRUD completo de autores e livros
-   Relacionamento entre entidades (Autor e Livro)
-   Validações e regras de negócio
-   API REST seguindo boas práticas RESTful
-   Autenticação e autorização com JWT
-   Monitoramento e logs
-   Documentação interativa com Swagger
-   Testes unitários e de integração

----------------------------------

## 🧠 Conceitos aplicados

-   Mapeamento ORM com JPA
-   Uso de annotations do Spring
-   DTOs para transferência de dados
-   Separação de responsabilidades (Controller, Service, Repository)
-   Cascade e ciclo de vida das entidades
-   Configuração com application.yaml
-   Pool de conexões com Hikari

----------------------------------

## 🐳 Rodando com Docker

### Criar rede

```
docker network create library-network
```

### Subir PostgreSQL
```
docker run --name librarydb -p 5432:5432 -e POSTGRES_PASSWORD=postgres
-e POSTGRES_USER=postgres -e POSTGRES_DB=library --network
library-network -d postgres:18.2
```

### Subir PgAdmin

```
docker run --name pgadmin4 -p 15432:80 -e
PGADMIN_DEFAULT_EMAIL=admin@admin.com -e PGADMIN_DEFAULT_PASSWORD=admin
--network library-network -d dpage/pgadmin4
```

### Acessar PgAdmin

http://localhost:15432

----------------------------------

## ⚙️ Como rodar o projeto

### Pré-requisitos

-   Java 17+
-   Maven
-   Docker

### Instalar dependências

```
mvn dependency:resolve
```

### Rodar aplicação

```
mvn spring-boot:run
```

ou rodar direto pela IDE IntelliJ

----------------------------------

## 📄 Documentação da API

http://localhost:8080/swagger-ui.html

----------------------------------

## 🧪 Testes

```
mvn test
```

----------------------------------

## 🤝 Contribuição

Sinta-se à vontade para contribuir, sugerir melhorias ou trocar ideias!
