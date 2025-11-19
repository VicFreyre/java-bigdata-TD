package br.com.sistema.matriculas.repositorio;

import br.com.sistema.matriculas.entidade.Matricula;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MatriculaRepositorio extends JpaRepository<Matricula, Long> {

    @Query("SELECT m FROM Matricula m WHERE m.aluno.id = :idAluno")
    List<Matricula> buscarPorAluno(@Param("idAluno") Long idAluno);

    @Query("SELECT m FROM Matricula m WHERE m.curso.id = :idCurso")
    List<Matricula> buscarPorCurso(@Param("idCurso") Long idCurso);

    @Query("SELECT m FROM Matricula m WHERE m.status = :status")
    Page<Matricula> buscarPorStatus(@Param("status") String status, Pageable pageable);

    @Query("SELECT m FROM Matricula m WHERE m.dataMatricula BETWEEN :dataInicio AND :dataFim")
    List<Matricula> buscarPorPeriodo(@Param("dataInicio") LocalDateTime dataInicio, 
                                     @Param("dataFim") LocalDateTime dataFim);

    @Query("SELECT m FROM Matricula m WHERE m.aluno.id = :idAluno AND m.curso.id = :idCurso AND m.status = 'ATIVA'")
    Optional<Matricula> buscarMatriculaAtiva(@Param("idAluno") Long idAluno, @Param("idCurso") Long idCurso);

    @Query("SELECT COUNT(m) FROM Matricula m WHERE m.curso.id = :idCurso AND m.status = 'ATIVA'")
    Long contarMatriculasAtivasPorCurso(@Param("idCurso") Long idCurso);
}

