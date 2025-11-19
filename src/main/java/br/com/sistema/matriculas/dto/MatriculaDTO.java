package br.com.sistema.matriculas.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class MatriculaDTO {

    private Long id;

    @NotNull(message = "Aluno é obrigatório")
    private Long idAluno;

    private String nomeAluno;

    private String emailAluno;

    @NotNull(message = "Curso é obrigatório")
    private Long idCurso;

    private String nomeCurso;

    private LocalDateTime dataMatricula;

    private String status;

    private LocalDateTime dataCancelamento;

    // Construtores
    public MatriculaDTO() {
    }

    public MatriculaDTO(Long id, Long idAluno, String nomeAluno, Long idCurso, String nomeCurso, 
                       LocalDateTime dataMatricula, String status) {
        this.id = id;
        this.idAluno = idAluno;
        this.nomeAluno = nomeAluno;
        this.idCurso = idCurso;
        this.nomeCurso = nomeCurso;
        this.dataMatricula = dataMatricula;
        this.status = status;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(Long idAluno) {
        this.idAluno = idAluno;
    }

    public String getNomeAluno() {
        return nomeAluno;
    }

    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }

    public String getEmailAluno() {
        return emailAluno;
    }

    public void setEmailAluno(String emailAluno) {
        this.emailAluno = emailAluno;
    }

    public Long getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(Long idCurso) {
        this.idCurso = idCurso;
    }

    public String getNomeCurso() {
        return nomeCurso;
    }

    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
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
}

