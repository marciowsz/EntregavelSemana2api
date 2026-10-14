# API Escola — Spring Boot + PostgreSQL (Semana 02)

API REST de **Alunos** e **Cursos** construída com Spring Boot, arquitetura em camadas e persistência real no PostgreSQL via JPA/Hibernate.

## Fluxo de uma requisição

```
Cliente (Postman) → Controller → Service → Repository → PostgreSQL
```

| Camada | Classe | Responsabilidade |
|---|---|---|
| Entity | `Aluno`, `Curso` | Mapeamento JPA (`@Entity`, `@Id`, `@GeneratedValue`) — o Hibernate cria as tabelas |
| Repository | `AlunoRepository`, `CursoRepository` | Estendem `JpaRepository` e herdam `save`, `findAll`, `findById`, `delete` |
| Service | `AlunoService`, `CursoService` | Regras de negócio (ex.: erro 404 quando o id não existe) |
| Controller | `AlunoController`, `CursoController` | Endpoints REST, recebem e devolvem JSON |

A **injeção de dependência** é feita via construtor: o Spring cria os Beans (`@Repository`, `@Service`, `@RestController`) e entrega automaticamente o Repository ao Service e o Service ao Controller. Com um único construtor, o `@Autowired` é opcional.

## Como rodar

1. Crie o banco no PostgreSQL: `CREATE DATABASE escola;`
2. Ajuste usuário e senha em `src/main/resources/application.properties`.
3. Execute `mvn spring-boot:run` (ou rode a classe `ApiApplication` pela IDE).
4. No console, com `show-sql=true`, aparecem os comandos `create table aluno` e `create table curso` gerados pelo Hibernate (`ddl-auto=update`).

## Endpoints

### Desafio prático

| Método | URL | Resposta |
|---|---|---|
| GET | `/alunos/ra` | `O RA do aluno [Seu Nome] é [Seu RA]` |

### Alunos

| Método | URL | Anotações | Descrição |
|---|---|---|---|
| GET | `/alunos` | `@GetMapping` | Lista todos |
| GET | `/alunos/{id}` | `@PathVariable` | Busca por id (404 se não existir) |
| GET | `/alunos/buscar?nome=ana` | `@RequestParam` | Busca por parte do nome |
| POST | `/alunos` | `@PostMapping`, `@RequestBody` | Cria (retorna 201) |
| PUT | `/alunos/{id}` | `@PathVariable`, `@RequestBody` | Atualiza |
| DELETE | `/alunos/{id}` | `@PathVariable` | Exclui (retorna 204) |

### Cursos

| Método | URL | Descrição |
|---|---|---|
| GET | `/cursos` | Lista todos |
| GET | `/cursos?turno=noite` | Filtra por turno (`@RequestParam` opcional) |
| GET | `/cursos/{id}` | Busca por id |
| POST | `/cursos` | Cria |
| PUT | `/cursos/{id}` | Atualiza |
| DELETE | `/cursos/{id}` | Exclui |

## Testando no Postman / Insomnia

**POST** `http://localhost:8080/alunos` — Body → raw → JSON:

```json
{
  "nome": "Ana Souza",
  "ra": "123456",
  "email": "ana@email.com"
}
```

**POST** `http://localhost:8080/cursos`:

```json
{
  "descricao": "Análise e Desenvolvimento de Sistemas",
  "turno": "Noite"
}
```

Depois, **GET** `http://localhost:8080/alunos` e `http://localhost:8080/cursos` para listar o que foi salvo no banco. No console aparece o SQL executado, por exemplo:

```sql
insert into aluno (email, nome, ra) values (?, ?, ?)
select a1_0.id, a1_0.email, a1_0.nome, a1_0.ra from aluno a1_0
```
