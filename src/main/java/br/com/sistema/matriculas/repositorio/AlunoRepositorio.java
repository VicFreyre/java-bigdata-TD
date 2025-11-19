package br.com.sistema.matriculas.repositorio;

import br.com.sistema.matriculas.entidade.Aluno;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlunoRepositorio extends JpaRepository<Aluno, Long> {

    @Query("SELECT a FROM Aluno a WHERE LOWER(a.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    Page<Aluno> buscarPorNome(@Param("nome") String nome, Pageable pageable);

    Optional<Aluno> findByEmail(String email);

    boolean existsByEmail(String email);
}

