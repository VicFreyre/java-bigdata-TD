package br.com.sistema.matriculas.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public class AlunoDTO {

    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, max = 255, message = "Nome deve ter entre 3 e 255 caracteres")
    private String nome;

    @NotNull(message = "Idade é obrigatória")
    @Min(value = 1, message = "Idade deve ser maior que zero")
    private Integer idade;

    @Size(max = 500, message = "Endereço deve ter no máximo 500 caracteres")
    private String endereco;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ser válido")
    private String email;

    @Size(max = 20, message = "Telefone deve ter no máximo 20 caracteres")
    private String telefone;

    private LocalDateTime dataCadastro;

    // Construtores
    public AlunoDTO() {
    }

    public AlunoDTO(Long id, String nome, Integer idade, String endereco, String email, String telefone) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.endereco = endereco;
        this.email = email;
        this.telefone = telefone;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
}

