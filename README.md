# FleetPulse

API REST para gestão de frota: motoristas, veículos e viagens, com autenticação JWT e controle de acesso por role.

Projeto de estudo, feito para praticar Spring Boot, JPA, Spring Security e JWT.

## Tecnologias

- Java 25
- Spring Boot 4.1.1 (Web, Data JPA, Validation, Security)
- PostgreSQL (PostGIS 16) via Docker Compose
- JWT com [jjwt](https://github.com/jwtk/jjwt) 0.12.6
- Springdoc OpenAPI (Swagger UI)
- Lombok
- Maven 

## Pré-requisitos

- JDK 25
- Docker (para o banco de dados)

## Como rodar

```bash
# 1. clone o repositório e entre na pasta
git clone <url-do-repositorio>
cd fleetpulse

# 2. suba a aplicação
./mvnw spring-boot:run
```

No Windows, use `mvnw.cmd spring-boot:run`.

O projeto usa `spring-boot-docker-compose`, então ao iniciar a aplicação o Spring sobe sozinho o PostgreSQL definido no `compose.yaml` (porta 5432). Se preferir subir o banco manualmente antes:

```bash
docker compose up -d
```

A API fica disponível em `http://localhost:8080`.

> O banco é **recriado a cada execução** (`ddl-auto: create`) e populado com alguns veículos de exemplo (`data.sql`). Dados criados durante o uso são perdidos ao reiniciar.

## Documentação da API (Swagger)

Com a aplicação rodando: <http://localhost:8080/swagger-ui/index.html>

## Autenticação e roles

Roles existentes: `ROLE_ADMIN`, `ROLE_MANAGER`, `ROLE_DRIVER`.

Ao iniciar, a aplicação cria um administrador padrão (configurável em `application.yml`, chave `app.admin`):

| Email | Senha |
|---|---|
| `admin@fleetpulse.com` | `admin123` |

Fluxo básico:

1. Faça login em `POST /users/login` e copie o `token` da resposta.
2. Envie o token nas demais requisições: `Authorization: Bearer <token>`.

Regras principais:

- Só **ADMIN** cria usuários (`POST /users/register`).
- Motoristas **não** são criados por `/users/register`. Use `POST /drivers` (ADMIN ou MANAGER), que cria o usuário com role `ROLE_DRIVER` e o perfil de motorista juntos.
- Sem token: `401`. Com token, mas sem permissão: `403`.

## Principais endpoints

| Método | Rota | Quem acessa |
|---|---|---|
| POST | `/users/login` | Público |
| POST | `/users/register` | ADMIN |
| POST | `/drivers` | ADMIN, MANAGER |
| GET | `/drivers`, `/drivers/{id}` | ADMIN, MANAGER |
| PUT | `/drivers/{id}` | ADMIN, MANAGER |
| PATCH | `/drivers/{id}/off-duty` | ADMIN, MANAGER |
| DELETE | `/drivers/{id}` | ADMIN, MANAGER |
| POST | `/vehicles` | ADMIN |
| GET | `/vehicles`, `/vehicles/{id}` | ADMIN, MANAGER |
| PUT | `/vehicles/{id}` | ADMIN |
| PATCH | `/vehicles/{id}/maintenance/finish` | ADMIN, MANAGER |
| DELETE | `/vehicles/{id}` | ADMIN |
| POST, GET | `/trips` | ADMIN, MANAGER |
| GET | `/trips/{id}` | ADMIN, MANAGER, DRIVER |
| PUT | `/trips/{id}/driver`, `/trips/{id}/vehicle` | ADMIN, MANAGER |
| PATCH | `/trips/{id}/route`, `/trips/{id}/cancel` | ADMIN, MANAGER |
| PATCH | `/trips/{id}/start`, `/trips/{id}/complete` | ADMIN, MANAGER, DRIVER |

## Testes

```bash
./mvnw test
```

Os testes de domínio (`DriverTest`, `TripTest`, `VehicleTest`) são unitários. O `FleetpulseApplicationTests` sobe o contexto completo e precisa do banco rodando.

## Limitações conhecidas

Por ser um projeto de estudo, ficaram de fora:

- Segredo do JWT e credenciais do banco ficam no `application.yml` (em um projeto real iriam para variáveis de ambiente).
- Não há endpoint para promover/alterar a role de um usuário.
- Não há endpoints para alterar nome, email ou senha de um usuário.
- Sem refresh token nem logout (o token vale até expirar, 24h).
- Sem migrations (Flyway/Liquibase): o schema é gerado pelo Hibernate.
