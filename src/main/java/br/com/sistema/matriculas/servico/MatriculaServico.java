package br.com.sistema.matriculas.servico;

import br.com.sistema.matriculas.dto.MatriculaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MatriculaServico {

    Page<MatriculaDTO> listarTodas(Pageable pageable);

    Optional<MatriculaDTO> buscarPorId(Long id);

    MatriculaDTO salvar(MatriculaDTO matriculaDTO);

    void cancelar(Long id);

    List<MatriculaDTO> buscarPorAluno(Long idAluno);

    List<MatriculaDTO> buscarPorCurso(Long idCurso);

    List<MatriculaDTO> buscarPorPeriodo(LocalDateTime dataInicio, LocalDateTime dataFim);

    Long contarMatriculasAtivasPorCurso(Long idCurso);
}

