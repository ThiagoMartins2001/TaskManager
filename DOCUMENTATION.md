# Documentação Técnica - TaskManager

## 📋 Índice

1. [Arquitetura do Sistema](#arquitetura-do-sistema)
2. [Configurações](#configurações)
3. [Classes e Componentes](#classes-e-componentes)
4. [Fluxo de Dados](#fluxo-de-dados)
5. [Endpoints da API](#endpoints-da-api)
6. [Estrutura do Projeto](#estrutura-do-projeto)

---

## 🏗️ Arquitetura do Sistema

O TaskManager segue a arquitetura em camadas do Spring Boot, implementando o padrão MVC (Model-View-Controller) com as seguintes camadas:

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

### Padrões Utilizados

- **Repository Pattern**: Para abstração do acesso a dados
- **Dependency Injection**: Para injeção de dependências
- **RESTful API**: Para comunicação HTTP
- **JPA/Hibernate**: Para mapeamento objeto-relacional

---

## ⚙️ Configurações

### 1. application.properties

**Localização**: `src/main/resources/application.properties`

**Função**: Arquivo de configuração principal do Spring Boot que define:
- Conexão com banco de dados MySQL
- Configurações do JPA/Hibernate
- Porta do servidor
- Configurações de logging

**Configurações Principais**:
```properties
# Configurações do Banco de Dados MySQL (Docker)
spring.datasource.url=jdbc:mysql://localhost:5552/taskdb?useSSL=false&serverTimezone=UTC
spring.datasource.username=admin
spring.datasource.password=admin
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# Configura o JPA/Hibernate para criar/atualizar as tabelas automaticamente
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Configuração da porta do servidor
server.port=8081
```

**Explicação das Configurações**:
- `spring.datasource.url`: URL de conexão com MySQL (porta 5552 para evitar conflitos)
- `spring.jpa.hibernate.ddl-auto=update`: Cria/atualiza tabelas automaticamente
- `spring.jpa.show-sql=true`: Exibe queries SQL no console para debug
- `server.port=8081`: Define a porta do servidor Spring Boot

### 2. docker-compose.yml

**Localização**: `docker-compose.yml` (raiz do projeto)

**Função**: Arquivo de orquestração do Docker que define:
- Configuração do container MySQL
- Variáveis de ambiente do banco
- Mapeamento de portas
- Volumes para persistência de dados

**Configurações Principais**:
```yaml
version: '3.8'
services:
  mysql:
    image: mysql:8.0
    container_name: task-manager-mysql
    environment:
      MYSQL_ROOT_PASSWORD: Mudar123
      MYSQL_DATABASE: taskdb
      MYSQL_USER: admin
      MYSQL_PASSWORD: admin
    ports:
      - "5552:3306"
    volumes:
      - ./mysql-data:/var/lib/mysql
```

**Explicação das Configurações**:
- `image: mysql:8.0`: Versão do MySQL utilizada
- `MYSQL_DATABASE: taskdb`: Nome do banco criado automaticamente
- `MYSQL_USER/MYSQL_PASSWORD`: Credenciais do usuário admin
- `MYSQL_ROOT_PASSWORD: Mudar123`: Senha do usuário root
- `ports: "5552:3306"`: Mapeia porta 5552 local para 3306 do container
- `volumes: ./mysql-data:/var/lib/mysql`: Persiste dados na pasta mysql-data

### 3. pom.xml

**Localização**: `pom.xml` (raiz do projeto)

**Função**: Arquivo de configuração do Maven que define:
- Dependências do projeto
- Versões das tecnologias
- Configurações de build

**Dependências Principais**:
```xml
<dependencies>
    <!-- Spring Boot Starter Web -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    
    <!-- Spring Boot Starter Data JPA -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    
    <!-- MySQL Connector -->
    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
        <scope>runtime</scope>
    </dependency>
    
    <!-- Spring Boot Starter Test -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

---

## 🧩 Classes e Componentes

### 1. TaskManagerApplication.java

**Localização**: `src/main/java/CodingTechnology/TaskManager/TaskManagerApplication.java`

**Função**: Classe principal que inicializa a aplicação Spring Boot

**Responsabilidades**:
- Ponto de entrada da aplicação
- Configuração automática do Spring Boot
- Escaneamento de componentes

**Código Principal**:
```java
@SpringBootApplication
public class TaskManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(TaskManagerApplication.class, args);
    }
}
```

**Anotações Utilizadas**:
- `@SpringBootApplication`: Combina `@Configuration`, `@EnableAutoConfiguration` e `@ComponentScan`

### 2. Task.java (Model)

**Localização**: `src/main/java/CodingTechnology/TaskManager/Model/Task.java`

**Função**: Entidade JPA que representa uma tarefa no banco de dados

**Responsabilidades**:
- Mapeamento objeto-relacional
- Definição da estrutura de dados
- Getters e setters para acesso aos dados

**Campos da Entidade**:
- `id` (Long): Identificador único (chave primária)
- `title` (String): Título da tarefa
- `description` (String): Descrição detalhada
- `completed` (boolean): Status de conclusão

**Anotações JPA Utilizadas**:
- `@Entity`: Marca a classe como entidade JPA
- `@Id`: Define o campo como chave primária
- `@GeneratedValue(strategy = GenerationType.IDENTITY)`: Gera ID automaticamente

**Código da Entidade**:
```java
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String title;
    private String description;
    private boolean completed;
    
    // Getters e Setters
}
```

### 3. TaskRepository.java (Repository)

**Localização**: `src/main/java/CodingTechnology/TaskManager/Repository/TaskRepository.java`

**Função**: Interface que estende JpaRepository para operações de banco de dados

**Responsabilidades**:
- Abstração do acesso a dados
- Operações CRUD básicas herdadas do JpaRepository
- Definição de queries customizadas (se necessário)

**Herança e Funcionalidades**:
```java
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    // Herda automaticamente:
    // - save(Task entity)
    // - findById(Long id)
    // - findAll()
    // - deleteById(Long id)
    // - existsById(Long id)
    // - etc.
}
```

**Métodos Herdados do JpaRepository**:
- `save()`: Salva ou atualiza entidade
- `findById()`: Busca por ID
- `findAll()`: Lista todas as entidades
- `deleteById()`: Remove por ID
- `existsById()`: Verifica existência

### 4. TaskController.java (Controller)

**Localização**: `src/main/java/CodingTechnology/TaskManager/Controller/TaskController.java`

**Função**: Controlador REST que expõe endpoints HTTP para operações de tarefas

**Responsabilidades**:
- Receber requisições HTTP
- Processar dados de entrada
- Chamar métodos do repository
- Retornar respostas HTTP apropriadas

**Injeção de Dependência**:
```java
@Autowired
private TaskRepository taskRepository;
```

**Anotações REST Utilizadas**:
- `@RestController`: Marca como controlador REST
- `@RequestMapping("/api/task")`: Define o caminho base
- `@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping`: Define métodos HTTP
- `@ResponseStatus`: Define códigos de status HTTP específicos

**Endpoints Implementados**:
- `GET /api/task/getAll`: Lista todas as tarefas
- `POST /api/task`: Cria nova tarefa
- `GET /api/task/{id}`: Busca tarefa por ID
- `PUT /api/task/{id}`: Atualiza tarefa
- `DELETE /api/task/{id}`: Remove tarefa

---

## 🔄 Fluxo de Dados

### Fluxo de Criação de Tarefa (POST)

```
1. Cliente → POST /api/task
2. TaskController.createTask() recebe JSON
3. Spring converte JSON para objeto Task
4. TaskRepository.save() persiste no banco
5. Retorna Task criada com ID gerado (Status 201)
```

### Fluxo de Busca de Tarefa (GET)

```
1. Cliente → GET /api/task/{id}
2. TaskController.getTaskById() recebe ID
3. TaskRepository.findById() busca no banco
4. Retorna Task (Status 200) ou 404 se não encontrada
```

### Fluxo de Atualização (PUT)

```
1. Cliente → PUT /api/task/{id}
2. TaskController.updateTask() recebe ID e dados
3. TaskRepository.findById() busca tarefa existente
4. Atualiza campos da tarefa
5. TaskRepository.save() persiste alterações
6. Retorna Task atualizada (Status 200) ou 404
```

### Fluxo de Exclusão (DELETE)

```
1. Cliente → DELETE /api/task/{id}
2. TaskController.deleteTask() recebe ID
3. TaskRepository.existsById() verifica existência
4. TaskRepository.deleteById() remove do banco
5. Retorna 204 (sucesso) ou 404 (não encontrada)
```

---

## 🌐 Endpoints da API

### 1. GET /api/task/getAll

**Função**: Lista todas as tarefas cadastradas

**Método**: `getAllTask()`

**Fluxo**:
1. Recebe requisição GET
2. Chama `taskRepository.findAll()`
3. Retorna `List<Task>` em JSON

**Resposta**:
```json
[
  {
    "id": 1,
    "title": "Estudar Spring Boot",
    "description": "Aprender conceitos básicos",
    "completed": false
  },
  {
    "id": 2,
    "title": "Implementar API REST",
    "description": "Criar endpoints CRUD",
    "completed": true
  }
]
```

### 2. POST /api/task

**Função**: Cria uma nova tarefa

**Método**: `createTask(@RequestBody Task task)`

**Fluxo**:
1. Recebe JSON no corpo da requisição
2. Spring converte para objeto Task
3. Chama `taskRepository.save(task)`
4. Retorna Task criada com ID (Status 201)

**Corpo da Requisição**:
```json
{
  "title": "Nova Tarefa",
  "description": "Descrição da tarefa",
  "completed": false
}
```

**Resposta**:
```json
{
  "id": 3,
  "title": "Nova Tarefa",
  "description": "Descrição da tarefa",
  "completed": false
}
```

### 3. GET /api/task/{id}

**Função**: Busca tarefa específica por ID

**Método**: `getTaskById(@PathVariable Long id)`

**Fluxo**:
1. Recebe ID na URL
2. Chama `taskRepository.findById(id)`
3. Retorna Task ou 404

**Respostas**:
- **200 OK**: Tarefa encontrada
- **404 Not Found**: Tarefa não encontrada

### 4. PUT /api/task/{id}

**Função**: Atualiza tarefa existente

**Método**: `updateTask(@PathVariable Long id, @RequestBody Task taskDetails)`

**Fluxo**:
1. Recebe ID na URL e dados no corpo
2. Busca tarefa existente
3. Atualiza campos
4. Salva alterações
5. Retorna Task atualizada ou 404

**Corpo da Requisição**:
```json
{
  "title": "Tarefa Atualizada",
  "description": "Nova descrição",
  "completed": true
}
```

### 5. DELETE /api/task/{id}

**Função**: Remove tarefa do sistema

**Método**: `deleteTask(@PathVariable Long id)`

**Fluxo**:
1. Recebe ID na URL
2. Verifica existência da tarefa
3. Remove do banco de dados
4. Retorna 204 (sucesso) ou 404

**Respostas**:
- **204 No Content**: Tarefa removida com sucesso
- **404 Not Found**: Tarefa não encontrada

---

## 📁 Estrutura do Projeto

### Organização de Pacotes

```
CodingTechnology.TaskManager/
├── Controller/
│   └── TaskController.java          # Controlador REST
├── Model/
│   └── Task.java                    # Entidade JPA
├── Repository/
│   └── TaskRepository.java          # Interface de acesso a dados
└── TaskManagerApplication.java      # Classe principal
```

### Estrutura de Recursos

```
src/main/resources/
├── application.properties           # Configurações da aplicação
├── static/                         # Arquivos estáticos (futuro)
└── templates/                      # Templates (futuro)
```

### Estrutura de Testes

```
src/test/java/CodingTechnology/TaskManager/
└── TaskManagerApplicationTests.java # Testes da aplicação
```

---

## 🔧 Códigos de Status HTTP

| Código | Significado | Uso |
|--------|-------------|-----|
| 200 | OK | Operação realizada com sucesso |
| 201 | Created | Recurso criado com sucesso |
| 204 | No Content | Operação realizada sem retorno |
| 400 | Bad Request | Dados inválidos na requisição |
| 404 | Not Found | Recurso não encontrado |
| 500 | Internal Server Error | Erro interno do servidor |

---

## 🚀 Próximos Passos

### Melhorias Sugeridas

1. **Camada de Serviço**: Implementar Service Layer para lógica de negócio
2. **Validação**: Adicionar validações com Bean Validation
3. **Tratamento de Erros**: Implementar Exception Handler global
4. **Logging**: Adicionar logs estruturados
5. **Testes**: Implementar testes unitários e de integração
6. **Documentação API**: Integrar Swagger/OpenAPI
7. **Segurança**: Implementar autenticação e autorização
8. **Interface Web**: Desenvolver frontend para interação

### Estrutura Futura

```
TaskManager/
├── src/main/java/CodingTechnology/TaskManager/
│   ├── Controller/
│   ├── Service/           ← Nova camada
│   ├── Repository/
│   ├── Model/
│   ├── DTO/              ← Objetos de transferência
│   ├── Exception/        ← Tratamento de erros
│   ├── Config/           ← Configurações
│   └── Security/         ← Segurança
├── src/main/resources/
│   ├── static/           ← Frontend
│   └── templates/        ← Templates
└── src/test/
    ├── unit/             ← Testes unitários
    └── integration/      ← Testes de integração
```

### Funcionalidades Futuras

- **Filtros e Paginação**: Para listagem de tarefas
- **Categorização**: Agrupar tarefas por categoria
- **Prioridades**: Definir níveis de prioridade
- **Datas**: Adicionar datas de criação e conclusão
- **Usuários**: Sistema de autenticação e autorização
- **Notificações**: Alertas para tarefas pendentes

---

**Documentação criada por**: ThiagoMartins2001  
**Versão**: 1.1  
**Data**: 2024  
**Última Atualização**: Dezembro 2024
