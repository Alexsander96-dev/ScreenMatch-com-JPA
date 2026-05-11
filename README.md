# ScreenMatch com JPA

Projeto desenvolvido no curso da Alura de desenvolvimento Back-End Java.

Este projeto e uma continuacao do ScreenMatch (sem web), agora com persistencia em banco de dados usando Spring Data JPA e PostgreSQL, consumindo a API OMDb para buscar series e episodios.

## Tecnologias

- Java 21
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Maven
- Jackson

## Funcionalidades

- Buscar serie na API OMDb e salvar no banco
- Listar series salvas no banco de dados
- Buscar episodios por serie salva
- Relacionamento entre Serie e Episodio com JPA

## Configuracao

A aplicacao usa as variaveis de ambiente abaixo:

- `DB_HOST`
- `DB_NAME`
- `DB_USER`
- `DB_PASSWORD`

Configuracao presente em `src/main/resources/application.properties`:

`jdbc:postgresql://${DB_HOST}/${DB_NAME}`

## Execucao

No Windows PowerShell:

```powershell
.\mvnw.cmd spring-boot:run
```

## Estrutura principal

- `src/main/java/com/alura/screenmacth/screenmatchComJpa/ScreenmatchComJpaApplication.java`
- `src/main/java/com/alura/screenmacth/screenmatchComJpa/principal/Principal.java`
- `src/main/java/com/alura/screenmacth/screenmatchComJpa/model/`
- `src/main/java/com/alura/screenmacth/screenmatchComJpa/repository/`

## Observacoes

- A API key da OMDb esta definida no codigo em `Principal.java`.
- Como melhoria futura, o ideal e mover a chave para variavel de ambiente.

