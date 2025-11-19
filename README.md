# Sistema de Matrículas

Sistema completo de gerenciamento de matrículas desenvolvido com Spring Boot, seguindo princípios de POO rigorosa e com interface moderna e intuitiva.

## 📋 Índice

- [Pré-requisitos](#pré-requisitos)
- [Tecnologias](#tecnologias)
- [Instalação e Configuração](#instalação-e-configuração)
- [Executando o Projeto](#executando-o-projeto)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Funcionalidades](#funcionalidades)
- [Banco de Dados](#banco-de-dados)
- [Scripts SQL](#scripts-sql)

## 🔧 Pré-requisitos

Antes de executar o projeto, certifique-se de ter instalado:

- **Java 17.0.12** ou superior
- **Maven 3.6+**
- **PostgreSQL 17**
- **IDE** (IntelliJ IDEA, Eclipse, VS Code, etc.)

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

## 📦 Instalação e Configuração

### 1. Clone o repositório

```bash
git clone <url-do-repositorio>
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
spring.datasource.password=1234
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

### Opção 1: Via Maven

```bash
mvn spring-boot:run
```

### Opção 2: Via IDE

1. Importe o projeto como projeto Maven
2. Execute a classe `SistemaMatriculasApplication.java`
3. O servidor iniciará na porta `8080`

### Acessando o Sistema

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
│   │       └── banco.sql                # Script SQL inicial
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

**Campos:**
- id
- nome
- idade
- endereco
- email
- telefone

### 2. CRUD de Cursos

- ✅ Listar cursos com paginação
- ✅ Pesquisar curso por nome
- ✅ Cadastrar curso
- ✅ Editar curso
- ✅ Excluir curso
- ✅ Visualizar lista de alunos matriculados no curso

**Campos:**
- id
- nome
- descricao
- cargaHoraria
- categoria (Tecnologia, Administração, Saúde, Linguagens, Gestão, Outros)

### 3. Matrículas

- ✅ Listar matrículas
- ✅ Criar matrícula
- ✅ Cancelar matrícula
- ✅ Exibir detalhes
- ✅ Ver histórico do aluno
- ✅ Ver relatório por curso

**Campos:**
- id
- id_aluno
- id_curso
- data_matricula
- status (ATIVA / CANCELADA)

### 4. Relatórios

- ✅ **Relatório de Alunos por Curso:** Lista todos os alunos matriculados em cada curso
- ✅ **Relatório de Cursos Mais Procurados:** Ranking dos cursos com mais matrículas ativas
- ✅ **Relatório de Matrículas por Período:** Análise de matrículas em um período específico com filtro por data

## 🗄️ Banco de Dados

### Tabelas

#### `alunos`
- `id` (SERIAL PRIMARY KEY)
- `nome` (VARCHAR(255) NOT NULL)
- `idade` (INTEGER NOT NULL)
- `endereco` (VARCHAR(500))
- `email` (VARCHAR(255) NOT NULL UNIQUE)
- `telefone` (VARCHAR(20))
- `data_cadastro` (TIMESTAMP)

#### `cursos`
- `id` (SERIAL PRIMARY KEY)
- `nome` (VARCHAR(255) NOT NULL)
- `descricao` (TEXT)
- `carga_horaria` (INTEGER NOT NULL)
- `categoria` (VARCHAR(100) NOT NULL)
- `data_cadastro` (TIMESTAMP)

#### `matriculas`
- `id` (SERIAL PRIMARY KEY)
- `id_aluno` (INTEGER NOT NULL, FOREIGN KEY)
- `id_curso` (INTEGER NOT NULL, FOREIGN KEY)
- `data_matricula` (TIMESTAMP)
- `status` (VARCHAR(20) NOT NULL, CHECK: 'ATIVA' ou 'CANCELADA')
- `data_cancelamento` (TIMESTAMP)

### Índices

- `idx_alunos_nome` - Busca rápida por nome
- `idx_alunos_email` - Busca rápida por email
- `idx_cursos_nome` - Busca rápida por nome
- `idx_cursos_categoria` - Busca rápida por categoria
- `idx_matriculas_aluno` - Relacionamento com alunos
- `idx_matriculas_curso` - Relacionamento com cursos
- `idx_matriculas_status` - Filtro por status
- `idx_matriculas_data` - Filtro por data

### View

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

### Trigger

**`trg_definir_dados_matricula`**

Trigger que define automaticamente a data de matrícula e o status como 'ATIVA' ao inserir uma nova matrícula:

```sql
CREATE TRIGGER trg_definir_dados_matricula
BEFORE INSERT ON matriculas
FOR EACH ROW
EXECUTE FUNCTION definir_dados_matricula();
```

### Procedure

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

## 📊 Relatórios

### Como Rodar Relatórios

1. Acesse o menu **Relatórios** no topo da página
2. Selecione o relatório desejado:
   - **Alunos por Curso:** Mostra todos os alunos agrupados por curso
   - **Cursos Mais Procurados:** Ranking dos cursos ordenados por número de matrículas
   - **Matrículas por Período:** Permite filtrar por data de início e fim

### Filtros

O relatório de **Matrículas por Período** permite:
- Selecionar data de início
- Selecionar data de fim
- Visualizar todas as matrículas realizadas no período

## 🐛 Solução de Problemas

### Erro de Conexão com Banco de Dados

1. Verifique se o PostgreSQL está rodando
2. Confirme as credenciais em `application.properties`
3. Certifique-se de que o banco `sistema_matriculas` existe

### Erro ao Executar Script SQL

O script SQL é executado automaticamente. Se houver erro:
1. Verifique os logs do Spring Boot
2. Execute o script manualmente no PostgreSQL
3. Verifique se há objetos duplicados (DROP antes de CREATE)

## 📄 Licença

Este projeto foi desenvolvido para fins educacionais.

## 👨‍💻 Desenvolvido com

- Java 17
- Spring Boot 3.2.0
- PostgreSQL 17
- Bootstrap 5.3.0
- Thymeleaf

---

**Sistema de Matrículas** - Gerenciamento completo de matrículas escolares

