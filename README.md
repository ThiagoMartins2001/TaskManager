# TaskManager - Sistema de Gerenciamento de Tarefas

## 📋 Descrição

TaskManager é um sistema de gerenciamento de tarefas desenvolvido em Java com Spring Boot, oferecendo uma API REST completa para operações CRUD (Create, Read, Update, Delete) de tarefas.

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
│   │   │   │   └── taskRepository.java
│   │   │   └── TaskManagerApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── docker-compose.yml
├── pom.xml
└── README.md
```

## 🛠️ Configuração e Instalação

### Pré-requisitos

- Java 21 ou superior
- Docker e Docker Compose
- Maven

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

```bash
./mvnw spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8081`

## 📚 Documentação da API

### Endpoints Disponíveis

| Método | URL | Descrição |
|--------|-----|-----------|
| GET | `/api/task/getAll` | Lista todas as tarefas |
| POST | `/api/task` | Cria uma nova tarefa |
| GET | `/api/task/{id}` | Busca uma tarefa por ID |
| PUT | `/api/task/{id}` | Atualiza uma tarefa |
| DELETE | `/api/task/{id}` | Remove uma tarefa |

### Exemplo de Uso

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

## 🗄️ Configuração do Banco de Dados

O projeto utiliza MySQL 8.0 configurado via Docker:

- **Host**: localhost
- **Porta**: 5552
- **Database**: taskdb
- **Usuário**: admin
- **Senha**: admin

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

**CodingTechnology** - [GitHub](https://github.com/CodingTechnology)

---

**Versão**: 1.0  
**Data**: 2024
