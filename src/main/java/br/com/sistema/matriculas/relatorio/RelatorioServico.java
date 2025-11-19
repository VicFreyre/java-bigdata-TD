package br.com.sistema.matriculas.relatorio;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface RelatorioServico {

    List<Map<String, Object>> alunosPorCurso();

    List<Map<String, Object>> cursosMaisProcurados();

    List<Map<String, Object>> matriculasPorPeriodo(LocalDateTime dataInicio, LocalDateTime dataFim);
}

