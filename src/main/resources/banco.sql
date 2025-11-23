-- ============================================
-- SISTEMA DE MATRÍCULAS - BANCO DE DADOS
-- PostgreSQL 17
-- ============================================



-- ============================================
-- TABELA: alunos
-- ============================================
CREATE TABLE alunos (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    idade INTEGER NOT NULL CHECK (idade > 0),
    endereco VARCHAR(500),
    email VARCHAR(255) NOT NULL UNIQUE,
    telefone VARCHAR(20),
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_ultima_modificacao TIMESTAMP
);

-- Índice para busca por nome
CREATE INDEX idx_alunos_nome ON alunos(nome);

-- Índice para busca por email
CREATE INDEX idx_alunos_email ON alunos(email);

-- ============================================
-- TABELA: cursos
-- ============================================
CREATE TABLE cursos (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    descricao TEXT,
    carga_horaria INTEGER NOT NULL CHECK (carga_horaria > 0),
    categoria VARCHAR(100) NOT NULL,
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_ultima_modificacao TIMESTAMP
);

-- Índice para busca por nome
CREATE INDEX idx_cursos_nome ON cursos(nome);

-- Índice para busca por categoria
CREATE INDEX idx_cursos_categoria ON cursos(categoria);

-- ============================================
-- TABELA: matriculas
-- ============================================
CREATE TABLE matriculas (
    id SERIAL PRIMARY KEY,
    id_aluno INTEGER NOT NULL,
    id_curso INTEGER NOT NULL,
    data_matricula TIMESTAMP,
    status VARCHAR(20) NOT NULL DEFAULT 'ATIVA' CHECK (status IN ('ATIVA', 'CANCELADA', 'TRANCADA', 'TRANSFERIDA')),
    data_cancelamento TIMESTAMP,
    FOREIGN KEY (id_aluno) REFERENCES alunos(id) ON DELETE CASCADE,
    FOREIGN KEY (id_curso) REFERENCES cursos(id) ON DELETE CASCADE,
    UNIQUE(id_aluno, id_curso, status) 
);

-- Índices para melhor performance
CREATE INDEX idx_matriculas_aluno ON matriculas(id_aluno);
CREATE INDEX idx_matriculas_curso ON matriculas(id_curso);
CREATE INDEX idx_matriculas_status ON matriculas(status);
CREATE INDEX idx_matriculas_data ON matriculas(data_matricula);

-- ============================================
-- VIEW: vw_matriculas_detalhes
-- ============================================
CREATE OR REPLACE VIEW vw_matriculas_detalhes AS
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

-- ============================================
-- TRIGGER: definir_dados_matricula
-- ============================================
CREATE OR REPLACE FUNCTION definir_dados_matricula()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.data_matricula IS NULL THEN
        NEW.data_matricula := NOW();
    END IF;
    
    IF NEW.status IS NULL THEN
        NEW.status := 'ATIVA';
    END IF;
    
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_definir_dados_matricula
BEFORE INSERT ON matriculas
FOR EACH ROW
EXECUTE FUNCTION definir_dados_matricula();

-- ============================================
-- PROCEDURE: realizar_matricula
-- ============================================
CREATE OR REPLACE FUNCTION realizar_matricula(p_id_aluno INT, p_id_curso INT)
RETURNS VOID AS $$
DECLARE
    v_matricula_existente INTEGER;
BEGIN
    -- Verificar se já existe matrícula ativa
    SELECT COUNT(*) INTO v_matricula_existente
    FROM matriculas
    WHERE id_aluno = p_id_aluno 
      AND id_curso = p_id_curso 
      AND status = 'ATIVA';
    
    IF v_matricula_existente > 0 THEN
        RAISE EXCEPTION 'Aluno já possui matrícula ativa neste curso';
    END IF;
    
    BEGIN
        INSERT INTO matriculas(id_aluno, id_curso, data_matricula, status) 
        VALUES (p_id_aluno, p_id_curso, NOW(), 'ATIVA');
    EXCEPTION WHEN OTHERS THEN
        RAISE EXCEPTION 'Erro ao realizar matrícula: %', SQLERRM;
    END;
END;
$$ LANGUAGE plpgsql;

-- ============================================
-- DADOS INICIAIS (OPCIONAL)
-- ============================================


