package br.com.sistema.matriculas.entidade;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "matriculas")
public class Matricula {
    public void reativar() {
        this.status = "ATIVA";
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Aluno é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_aluno", nullable = false)
    private Aluno aluno;

    @NotNull(message = "Curso é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_curso", nullable = false)
    private Curso curso;


    @Column(name = "data_matricula")
    private LocalDateTime dataMatricula;

    @Column(name = "numero_matricula", length = 6, nullable = false, unique = true)
    private String numeroMatricula;

    @Column(nullable = false, length = 20)
    private String status;

    @Column(name = "data_cancelamento")
    private LocalDateTime dataCancelamento;

    // Construtores
    public Matricula() {
        this.dataMatricula = LocalDateTime.now();
        this.status = "ATIVA";
    }


    public Matricula(Aluno aluno, Curso curso, String numeroMatricula) {
        this();
        this.aluno = aluno;
        this.curso = curso;
        this.numeroMatricula = numeroMatricula;
    }
    public String getNumeroMatricula() {
        return numeroMatricula;
    }

    public void setNumeroMatricula(String numeroMatricula) {
        this.numeroMatricula = numeroMatricula;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public LocalDateTime getDataMatricula() {
        return dataMatricula;
    }

    public void setDataMatricula(LocalDateTime dataMatricula) {
        this.dataMatricula = dataMatricula;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDataCancelamento() {
        return dataCancelamento;
    }

    public void setDataCancelamento(LocalDateTime dataCancelamento) {
        this.dataCancelamento = dataCancelamento;
    }

    public void cancelar() {
        this.status = "CANCELADA";
        this.dataCancelamento = LocalDateTime.now();
    }

    public void trancar() {
        this.status = "TRANCADA";
    }

    public void transferir(Curso novoCurso) {
        this.status = "TRANSFERIDA";
        this.curso = novoCurso;
    }
}

