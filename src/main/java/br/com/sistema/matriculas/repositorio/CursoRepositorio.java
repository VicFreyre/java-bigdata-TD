package br.com.sistema.matriculas.repositorio;

import br.com.sistema.matriculas.entidade.Curso;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepositorio extends JpaRepository<Curso, Long> {

    @Query("SELECT c FROM Curso c WHERE LOWER(c.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    Page<Curso> buscarPorNome(@Param("nome") String nome, Pageable pageable);

    @Query("SELECT c FROM Curso c WHERE c.categoria = :categoria")
    Page<Curso> buscarPorCategoria(@Param("categoria") String categoria, Pageable pageable);
}

