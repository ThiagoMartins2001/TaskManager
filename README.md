# TaskManager - Sistema de Gerenciamento de Tarefas

## 📋 Descrição

TaskManager é um sistema de gerenciamento de tarefas desenvolvido em Java com Spring Boot, oferecendo uma API REST completa para operações CRUD (Create, Read, Update, Delete) de tarefas. O sistema utiliza arquitetura em camadas com padrão MVC e integração com banco de dados MySQL via Docker.

## 🚀 Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3.5.5**
- **Spring Data JPA**
- **MySQL 8.0**
- **Docker & Docker Compose**
- **Maven**

## 📁 Estrutura do Projeto

```
TaskManager/
├── src/
│   ├── main/
│   │   ├── java/CodingTechnology/TaskManager/
│   │   │   ├── Controller/
│   │   │   │   └── TaskController.java
│   │   │   ├── Model/
│   │   │   │   └── Task.java
│   │   │   ├── Repository/
│   │   │   │   └── TaskRepository.java
│   │   │   └── TaskManagerApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── static/
│   │       └── templates/
│   └── test/
│       └── java/CodingTechnology/TaskManager/
│           └── TaskManagerApplicationTests.java
├── mysql-data/
├── docker-compose.yml
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md
```

## 🛠️ Configuração e Instalação

### Pré-requisitos

- Java 21 ou superior
- Docker e Docker Compose
- Maven (ou use o wrapper Maven incluído: `mvnw`)

### 1. Clone o repositório

```bash
git clone <url-do-repositorio>
cd TaskManager
```

### 2. Inicie o banco de dados MySQL

```bash
docker-compose up -d
```

### 3. Execute a aplicação

**Usando Maven wrapper (recomendado):**
```bash
./mvnw spring-boot:run
```

**Ou usando Maven instalado:**
```bash
mvn spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8081`

## 📚 Documentação da API

### Endpoints Disponíveis

| Método | URL | Descrição | Status de Resposta |
|--------|-----|-----------|-------------------|
| GET | `/api/task/getAll` | Lista todas as tarefas | 200 OK |
| POST | `/api/task` | Cria uma nova tarefa | 201 Created |
| GET | `/api/task/{id}` | Busca uma tarefa por ID | 200 OK / 404 Not Found |
| PUT | `/api/task/{id}` | Atualiza uma tarefa | 200 OK / 404 Not Found |
| DELETE | `/api/task/{id}` | Remove uma tarefa | 204 No Content / 404 Not Found |

### Estrutura da Tarefa (Task)

```json
{
  "id": 1,
  "title": "Título da Tarefa",
  "description": "Descrição detalhada da tarefa",
  "completed": false
}
```

### Exemplos de Uso

#### Criar uma nova tarefa
```bash
curl -X POST http://localhost:8081/api/task \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Estudar Spring Boot",
    "description": "Aprender conceitos básicos do Spring Boot",
    "completed": false
  }'
```

#### Listar todas as tarefas
```bash
curl -X GET http://localhost:8081/api/task/getAll
```

#### Buscar tarefa por ID
```bash
curl -X GET http://localhost:8081/api/task/1
```

#### Atualizar uma tarefa
```bash
curl -X PUT http://localhost:8081/api/task/1 \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Estudar Spring Boot - Atualizado",
    "description": "Aprender conceitos avançados do Spring Boot",
    "completed": true
  }'
```

#### Deletar uma tarefa
```bash
curl -X DELETE http://localhost:8081/api/task/1
```

## 🗄️ Configuração do Banco de Dados

O projeto utiliza MySQL 8.0 configurado via Docker:

- **Host**: localhost
- **Porta**: 5552
- **Database**: taskdb
- **Usuário**: admin
- **Senha**: admin
- **Root Password**: Mudar123

### Configurações do Docker

O arquivo `docker-compose.yml` configura:
- Container MySQL 8.0
- Persistência de dados na pasta `mysql-data/`
- Mapeamento da porta 5552 (local) para 3306 (container)

## 🏗️ Arquitetura do Sistema

O projeto segue a arquitetura em camadas do Spring Boot:

```
┌─────────────────────────────────────┐
│           Controller Layer          │ ← TaskController.java
├─────────────────────────────────────┤
│          Repository Layer           │ ← TaskRepository.java
├─────────────────────────────────────┤
│            Model Layer              │ ← Task.java
├─────────────────────────────────────┤
│         Database Layer              │ ← MySQL via Docker
└─────────────────────────────────────┘
```

### Componentes Principais

- **TaskController**: Controlador REST que expõe endpoints HTTP
- **TaskRepository**: Interface para operações de banco de dados
- **Task**: Entidade JPA que representa uma tarefa
- **TaskManagerApplication**: Classe principal da aplicação Spring Boot

## 🚀 Funcionalidades Implementadas

✅ **CRUD Completo de Tarefas**
- Criar novas tarefas
- Listar todas as tarefas
- Buscar tarefa por ID
- Atualizar tarefas existentes
- Deletar tarefas

✅ **Validação de Dados**
- Verificação de existência antes de atualizar/deletar
- Respostas HTTP apropriadas (201, 204, 404)

✅ **Configuração Docker**
- Banco de dados MySQL containerizado
- Persistência de dados
- Configuração automatizada

## 🔧 Desenvolvimento

### Executar Testes
```bash
./mvnw test
```

### Compilar o Projeto
```bash
./mvnw clean compile
```

### Criar JAR Executável
```bash
./mvnw clean package
```

## 📖 Documentação Detalhada

Para informações detalhadas sobre a arquitetura, classes e implementação, consulte o arquivo [DOCUMENTATION.md](DOCUMENTATION.md).

## 🤝 Contribuição

1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

## 👨‍💻 Autor

**ThiagoMartins2001** - [GitHub](https://github.com/ThiagoMartins2001)

---

**Versão**: 1.1  
**Data**: 2024  
**Última Atualização**: Dezembro 2024
