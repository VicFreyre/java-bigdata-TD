## 🗃️ Sistema de Matrículas – ClassFlow

O **ClassFlow** é um sistema de gerenciamento de matrículas desenvolvido em **Java 17** com **Spring Boot**, utilizando arquitetura MVC, serviços e DTOs para garantir organização e baixo acoplamento. Ele se conecta ao banco **PostgreSQL** por meio do **Spring Data JPA/Hibernate**, que realiza o mapeamento objeto-relacional, validações e operações transacionais. O sistema controla alunos, cursos e matrículas com eficiência, integrando boas práticas de **POO**, módulos bem estruturados e recursos avançados do PostgreSQL, como **views, triggers e procedures**.

<img width="1917" height="899" alt="image" src="https://github.com/user-attachments/assets/f4b0e60b-6327-401c-8fa1-d0fe9b4be6bf" />


## 🔧 Pré-requisitos

Antes de executar o projeto, certifique-se de ter instalado:

- **Java 17.0.12** ou superior
- **Maven 3.6+**
- **PostgreSQL 17**
- **IDE** (IntelliJ IDEA, Eclipse, VS Code, etc.)

## 📦 Instalação e Configuração

### 1. Clone o repositório

```bash
git clone https://github.com/VicFreyre/java-bigdata-TD
cd sistema-matriculas
```

### 2. Configure o Banco de Dados PostgreSQL

1. Crie um banco de dados chamado `sistema_matriculas`:

```sql
CREATE DATABASE sistema_matriculas;
```

2. Configure as credenciais no arquivo `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/sistema_matriculas
spring.datasource.username=postgres
spring.datasource.password=12345
```

**Nota:** Ajuste o `username` e `password` conforme sua configuração do PostgreSQL.

### 3. Execute o Script SQL

O script `src/main/resources/banco.sql` será executado automaticamente na primeira inicialização do sistema. Ele cria:

- Tabelas: `alunos`, `cursos`, `matriculas`
- Índices para melhor performance
- View: `vw_matriculas_detalhes`
- Trigger: `trg_definir_dados_matricula`
- Procedure: `realizar_matricula`
- Dados iniciais de exemplo

## 🚀 Executando o Projeto

### Via Maven

```bash
mvn spring-boot:run
```
Após iniciar o servidor, acesse:

```
http://localhost:8080
```

## 📁 Estrutura do Projeto

```
sistema-matriculas/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── br/com/sistema/matriculas/
│   │   │       ├── SistemaMatriculasApplication.java
│   │   │       ├── controlador/          # Controllers REST
│   │   │       ├── entidade/             # Entidades JPA
│   │   │       ├── repositorio/          # Repositórios JPA
│   │   │       ├── servico/              # Interfaces de serviços
│   │   │       │   └── impl/             # Implementações dos serviços
│   │   │       ├── dto/                  # Data Transfer Objects
│   │   │       ├── excecao/              # Exceções customizadas
│   │   │       └── relatorio/            # Serviços de relatórios
│   │   └── resources/
│   │       ├── templates/                # Templates Thymeleaf
│   │       ├── application.properties    # Configurações
│   │       └── banco.sql                 # Script SQL inicial
│   └── test/
├── pom.xml
└── README.md
```

## ✨ Funcionalidades

### 1. CRUD de Alunos

- ✅ Listar alunos com busca por nome
- ✅ Cadastrar novo aluno
- ✅ Editar aluno
- ✅ Excluir aluno
- ✅ Ver detalhes do aluno
- ✅ Exibir histórico de matrículas do aluno

<img width="1917" height="259" alt="image" src="https://github.com/user-attachments/assets/6dce9d0c-b908-42cc-80ec-7285aa0fbed7" />

### 2. CRUD de Cursos

- ✅ Listar cursos com paginação
- ✅ Pesquisar curso por nome
- ✅ Cadastrar curso
- ✅ Editar curso
- ✅ Excluir curso
- ✅ Visualizar lista de alunos matriculados no curso

<img width="1914" height="250" alt="image" src="https://github.com/user-attachments/assets/424d155a-881c-4f65-b600-37767bffefa9" />

### 3. Matrículas

- ✅ Listar matrículas
- ✅ Criar matrícula
- ✅ Cancelar matrícula
- ✅ Exibir detalhes
- ✅ Ver histórico do aluno
- ✅ Ver relatório por curso

<img width="1909" height="263" alt="image" src="https://github.com/user-attachments/assets/850eb235-e68c-492a-b828-ab1579c20bdf" />

### 4. Relatórios

- ✅ **Relatório de Alunos por Curso:** Lista todos os alunos matriculados em cada curso
- ✅ **Relatório de Cursos Mais Procurados:** Ranking dos cursos com mais matrículas ativas

  <img width="1918" height="936" alt="image" src="https://github.com/user-attachments/assets/19711da1-fa81-4afe-8aa1-12c2bd14586c" />

## 🏛️ Arquitetura do Sistema
O sistema utiliza uma arquitetura multicamadas baseada no padrão** MVC (Model–View–Controller)**, garantindo organização, escalabilidade e fácil manutenção.

### **🔹 Camada de Apresentação (View)**
- Desenvolvida em HTML + Thymeleaf
- Responsável por exibir dados ao usuário
- Faz a comunicação com o Controller
 ```html
<p><strong>Nome:</strong> <span th:text="${aluno.nome}"></span></p>
```
### **🔹 Camada de Controle (Controller)**
- Recebe requisições
- Valida dados
- Chama serviços
 ```java
  @GetMapping("/alunos/{id}")
public String detalhes(@PathVariable Long id, Model model) {
    model.addAttribute("aluno", alunoService.buscarPorId(id));
    return "alunos-detalhes";
}
 ```

### 🔹 Camada de Serviço (Service)
- Contém regras de negócio
- Orquestra operações entre controller e repository
 ```java
  public AlunoDTO buscarPorId(Long id) {
    Aluno aluno = alunoRepositorio.findById(id).orElseThrow();
    return mapper.map(aluno, AlunoDTO.class);
}
```
### 🔹 Camada de Persistência (Repository)
- Usa Spring Data JPA para comunicação com o banco
 ```java

  @Entity
public class Matricula {
    @ManyToOne private Aluno aluno;
    @ManyToOne private Curso curso;
    private String status;
}
```
<img width="1316" height="456" alt="image" src="https://github.com/user-attachments/assets/fb39fe63-0a80-411f-90a9-46197c2b747e" />


## 🔗 API REST
A aplicação expõe endpoints para CRUD de alunos, cursos e matrículas.

<img width="1381" height="335" alt="image" src="https://github.com/user-attachments/assets/438c9365-31c8-4115-9857-0dd3f24efd66" />

### Exemplo de requisição – Criar Aluno
 ```json

{
    "nome": "Ana Costa",
    "idade": 21,
    "email": "ana@email.com",
    "telefone": "11999990000"
}
```
### Exemplo de resposta
 ```json

{
    "id": 1,
    "nome": "Ana Costa",
    "idade": 21,
    "email": "ana@email.com"
}
```

## 🧠 Programação Orientada a Objetos (POO)
O backend foi desenvolvido seguindo os pilares da POO para garantir organização, baixo acoplamento e alta coesão, tornando o sistema mais modular, fácil de manter e ampliar.

### 1. Abstração
A abstração consiste em representar entidades do mundo real por meio de modelos lógicos chamados classes, expondo apenas informações essenciais e ocultando detalhes internos.
 ```JAVA
public interface CursoRepositorio extends JpaRepository<Curso, Long> {}
```

### 2. Encapsulamento
Encapsulamento protege os dados das entidades, permitindo acesso e mudanças apenas por métodos controlados (getters e setters).
 ```JAVA
public class Aluno {
    private String nome;
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
}
```

### 3. Herança
A herança foi utilizada para generalizar e especializar comportamentos comuns
```JAVA
public class Pessoa {
    private String nome;
}
public class Aluno extends Pessoa {
    private Integer idade;
}
```

### 4. Polimorfismo
O polimorfismo aparece tanto em nível de sobrescrita (override) quanto de sobrecarregamento (overload)
```java
public interface MatriculaServico {
    void cancelar(Long id);
}
public class MatriculaServicoImpl implements MatriculaServico {
    @Override
    public void cancelar(Long id) { /* ... */ }
}
```


## 🗄️ Banco de Dados

### Tabelas

<img width="1339" height="379" alt="image" src="https://github.com/user-attachments/assets/aa3c4424-5831-4162-888b-be3fe3aa802e" />


### 1. Índices

- `idx_alunos_nome` - Busca rápida por nome
- `idx_alunos_email` - Busca rápida por email
- `idx_cursos_nome` - Busca rápida por nome
- `idx_cursos_categoria` - Busca rápida por categoria
- `idx_matriculas_aluno` - Relacionamento com alunos
- `idx_matriculas_curso` - Relacionamento com cursos
- `idx_matriculas_status` - Filtro por status
- `idx_matriculas_data` - Filtro por data

### 2. View

**`vw_matriculas_detalhes`**

View que une informações de matrículas, alunos e cursos para facilitar consultas:

```sql
SELECT 
    m.id AS id_matricula,
    a.id AS id_aluno,
    a.nome AS nome_aluno,
    a.email AS email_aluno,
    a.telefone AS telefone_aluno,
    c.id AS id_curso,
    c.nome AS nome_curso,
    c.categoria AS categoria_curso,
    c.carga_horaria,
    m.data_matricula,
    m.status,
    m.data_cancelamento
FROM matriculas m
JOIN alunos a ON m.id_aluno = a.id
JOIN cursos c ON m.id_curso = c.id;
```

### 3. Trigger

**`trg_definir_dados_matricula`**

Trigger que define automaticamente a data de matrícula e o status como 'ATIVA' ao inserir uma nova matrícula:

```sql
CREATE TRIGGER trg_definir_dados_matricula
BEFORE INSERT ON matriculas
FOR EACH ROW
EXECUTE FUNCTION definir_dados_matricula();
```

### 4. Procedure

**`realizar_matricula(p_id_aluno INT, p_id_curso INT)`**

Procedure que realiza uma matrícula com validação de matrícula ativa existente:

```sql
SELECT realizar_matricula(1, 1);
```

## 📝 Scripts SQL

O arquivo `src/main/resources/banco.sql` contém:

1. **Criação das tabelas** com todas as constraints
2. **Criação de índices** para otimização
3. **Criação da view** `vw_matriculas_detalhes`
4. **Criação do trigger** `trg_definir_dados_matricula`
5. **Criação da procedure** `realizar_matricula`
6. **Dados iniciais** (cursos de exemplo)

O script é executado automaticamente na primeira inicialização do sistema.

## 🎨 Interface

A interface foi desenvolvida com:

- **Bootstrap 5.3.0** para design responsivo
- **Bootstrap Icons** para ícones
- **Thymeleaf** para templates
- Menu fixo no topo
- Cards modernos e intuitivos
- Tabelas responsivas com paginação
- Formulários com validação

## 🔐 Validações

O sistema possui validações em:

- **Alunos:** Nome obrigatório (3-255 caracteres), idade > 0, email válido e único
- **Cursos:** Nome obrigatório (3-255 caracteres), carga horária > 0, categoria obrigatória
- **Matrículas:** Aluno e curso obrigatórios, não permite matrícula duplicada ativa

## 🛠️ Tecnologias

- **Java 17.0.12**
- **Spring Boot 3.2.0**
- **Spring Web**
- **Spring Data JPA**
- **PostgreSQL Driver (PostgreSQL 17)**
- **Thymeleaf**
- **Bootstrap 5.3.0**
- **Jakarta Validation (Bean Validation)**
- **Lombok**
- **ModelMapper**

---


## 🛠️ Desenvolvido por:
**Equipe:** Maria Victória Freire, Dannyelen Christinna Dourado, Arlington Costa Tavares Junior, Marcus Vinícius Costa Pachêco, Jefferson Freitas, Emmanoel Ferreira Oliveira.
### 📄 [Documentação Técnica](https://docs.google.com/document/d/1NVCaWefwCvgjClYzVp_iYclOM8QJSYfQCViVfPg_04Y/edit?usp=sharing)
### 🎨 [Apresentação no Canva](https://www.canva.com/design/DAG5k-DyvfE/pbVqZkB6PADgaYFeuzn8aw/edit?utm_content=DAG5k-DyvfE&utm_campaign=designshare&utm_medium=link2&utm_source=sharebutton)
### 📺 [Vídeo Demonstrativo](https://drive.google.com/file/d/1bfbBcatD9gXGL9CCU3YaCTCFBIk5AUia/view?usp=sharing)

*Este repositória visa contemplar o projeto de Trabalho Discente Efetivo (TDE) da disciplina de Programação Backend.*



