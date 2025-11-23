# README - Entidades, POO e Arquitetura MVC

## 1. Entidades do Sistema
O sistema possui as principais entidades:
- Aluno
- Curso
- Matricula

### Exemplo de código de criação de entidade (Model)
```java
@Entity
@Table(name = "alunos")
public class Aluno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private Integer idade;
    private String email;
    // ...
}
```

```java
@Entity
@Table(name = "cursos")
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String categoria;
    // ...
}
```

```java
@Entity
@Table(name = "matriculas")
public class Matricula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Aluno aluno;
    @ManyToOne
    private Curso curso;
    private String status;
    // ...
}
```

## 2. Pilares da Programação Orientada a Objetos (POO)
- **Encapsulamento:**
```java
public class Aluno {
    private String nome;
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
}
```
- **Herança:**
```java
public class Pessoa {
    private String nome;
}
public class Aluno extends Pessoa {
    private Integer idade;
}
```
- **Polimorfismo:**
```java
public interface MatriculaServico {
    void cancelar(Long id);
}
public class MatriculaServicoImpl implements MatriculaServico {
    @Override
    public void cancelar(Long id) { /* ... */ }
}
```
- **Abstração:**
```java
public interface CursoRepositorio extends JpaRepository<Curso, Long> {}
```

## 3. Arquitetura MVC
O sistema segue o padrão MVC, dividido em camadas:

### Camada de Apresentação (View)
Exemplo: `alunos-detalhes.html`
```html
<p><strong>Nome:</strong> <span th:text="${aluno.nome}"></span></p>
```

### Camada de Controle (Controller)
Exemplo: `AlunoControlador.java`
```java
@GetMapping("/detalhes/{id}")
public String detalhes(@PathVariable Long id, Model model) {
    AlunoDTO aluno = alunoServico.buscarPorId(id).orElseThrow();
    model.addAttribute("aluno", aluno);
    return "alunos-detalhes";
}
```

### Camada de Serviço (Service)
Exemplo: `AlunoServicoImpl.java`
```java
public AlunoDTO buscarPorId(Long id) {
    Aluno aluno = alunoRepositorio.findById(id).orElseThrow();
    return modelMapper.map(aluno, AlunoDTO.class);
}
```

### Camada de Persistência (Repository)
Exemplo: `AlunoRepositorio.java`
```java
public interface AlunoRepositorio extends JpaRepository<Aluno, Long> {}
```

### Camada de Modelo (Model/Entity)
Exemplo: `Aluno.java`
```java
@Entity
public class Aluno {
    @Id
    private Long id;
    private String nome;
    // ...
}
```

## 5. API REST - Detalhamento
A aplicação expõe uma API RESTful para integração e manipulação dos dados do sistema. Os principais pontos da API são:

### Estrutura dos Endpoints
- `/alunos` - Gerenciamento de alunos
- `/cursos` - Gerenciamento de cursos
- `/matriculas` - Gerenciamento de matrículas

### Exemplos de Endpoints
#### Alunos
- `GET /alunos` - Lista todos os alunos
- `GET /alunos/{id}` - Detalha um aluno específico
- `POST /alunos` - Cria um novo aluno
- `PUT /alunos/{id}` - Atualiza dados de um aluno
- `DELETE /alunos/{id}` - Remove um aluno

#### Cursos
- `GET /cursos` - Lista todos os cursos
- `GET /cursos/{id}` - Detalha um curso específico
- `POST /cursos` - Cria um novo curso
- `PUT /cursos/{id}` - Atualiza dados de um curso
- `DELETE /cursos/{id}` - Remove um curso

#### Matrículas
- `GET /matriculas` - Lista todas as matrículas
- `GET /matriculas/{id}` - Detalha uma matrícula específica
- `POST /matriculas` - Realiza uma matrícula
- `PUT /matriculas/{id}` - Atualiza status da matrícula (trancar, reativar, cancelar)
- `DELETE /matriculas/{id}` - Remove uma matrícula

### Formatos de Requisição e Resposta
As requisições e respostas utilizam o formato JSON. Exemplo de requisição para criar um aluno:
```json
{
    "nome": "João Silva",
    "idade": 22,
    "email": "joao@email.com",
    "endereco": "Rua A, 123",
    "telefone": "11999999999"
}
```
Exemplo de resposta:
```json
{
    "id": 1,
    "nome": "João Silva",
    "idade": 22,
    "email": "joao@email.com",
    "endereco": "Rua A, 123",
    "telefone": "11999999999"
}
```

### Métodos HTTP Utilizados
- `GET` - Consulta de dados
- `POST` - Criação de novos registros
- `PUT` - Atualização de registros existentes
- `DELETE` - Remoção de registros

### Fluxo da API
1. O cliente faz uma requisição HTTP para um endpoint.
2. O Controller recebe a requisição, valida os dados e chama o Service.
3. O Service executa a lógica de negócio e interage com o Repository.
4. O Repository acessa o banco de dados e retorna o resultado.
5. O Controller devolve a resposta ao cliente em JSON.

### Segurança e Validação
- Validação de dados via Bean Validation (Jakarta Validation)
- Possibilidade de autenticação/autorização via Spring Security (se configurado)
- Tratamento de erros com respostas padronizadas

### Documentação da API
Pode ser gerada automaticamente com ferramentas como Swagger/OpenAPI, facilitando testes e integração.

---
## 4. Tecnologias Utilizadas
- **Java 17:** Linguagem principal, orientada a objetos.
- **Spring Boot:** Framework para backend, gerencia dependências, ciclo de vida e configurações.
- **Spring Data JPA:** ORM para persistência, facilita operações com banco de dados.
- **Thymeleaf:** Motor de templates para views dinâmicas.
- **PostgreSQL:** Banco de dados relacional, armazena dados de forma persistente.
- **Bootstrap:** Framework CSS para interface responsiva.
- **Maven:** Gerenciador de dependências e build.

Cada tecnologia contribui para modularidade, produtividade, segurança e escalabilidade do sistema.

---
Este README serve como referência para entender a estrutura, entidades, pilares da POO, arquitetura MVC e tecnologias do sistema de matrículas.