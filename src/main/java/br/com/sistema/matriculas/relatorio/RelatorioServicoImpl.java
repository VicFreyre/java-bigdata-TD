package br.com.sistema.matriculas.relatorio;

import br.com.sistema.matriculas.repositorio.MatriculaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class RelatorioServicoImpl implements RelatorioServico {

    @Autowired
    private MatriculaRepositorio matriculaRepositorio;

    @Override
    public List<Map<String, Object>> alunosPorCurso() {
        List<Object[]> resultados = matriculaRepositorio.findAll().stream()
                .filter(m -> "ATIVA".equals(m.getStatus()))
                .map(m -> new Object[]{
                        m.getCurso().getId(),
                        m.getCurso().getNome(),
                        m.getAluno().getId(),
                        m.getAluno().getNome(),
                        m.getAluno().getEmail()
                })
                .toList();

        Map<Long, Map<String, Object>> cursosMap = new HashMap<>();
        
        for (Object[] resultado : resultados) {
            Long cursoId = (Long) resultado[0];
            String cursoNome = (String) resultado[1];
            Long alunoId = (Long) resultado[2];
            String alunoNome = (String) resultado[3];
            String alunoEmail = (String) resultado[4];

            cursosMap.putIfAbsent(cursoId, new HashMap<>());
            Map<String, Object> cursoMap = cursosMap.get(cursoId);
            cursoMap.put("idCurso", cursoId);
            cursoMap.put("nomeCurso", cursoNome);
            
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> alunos = (List<Map<String, Object>>) cursoMap.getOrDefault("alunos", new ArrayList<>());
            
            Map<String, Object> alunoMap = new HashMap<>();
            alunoMap.put("idAluno", alunoId);
            alunoMap.put("nomeAluno", alunoNome);
            alunoMap.put("emailAluno", alunoEmail);
            alunos.add(alunoMap);
            
            cursoMap.put("alunos", alunos);
            cursoMap.put("totalAlunos", alunos.size());
        }

        return new ArrayList<>(cursosMap.values());
    }

    @Override
    public List<Map<String, Object>> cursosMaisProcurados() {
        Map<Long, Map<String, Object>> cursosMap = new HashMap<>();
        
        matriculaRepositorio.findAll().stream()
                .filter(m -> "ATIVA".equals(m.getStatus()))
                .forEach(m -> {
                    Long cursoId = m.getCurso().getId();
                    cursosMap.putIfAbsent(cursoId, new HashMap<>());
                    Map<String, Object> cursoMap = cursosMap.get(cursoId);
                    cursoMap.put("idCurso", cursoId);
                    cursoMap.put("nomeCurso", m.getCurso().getNome());
                    cursoMap.put("categoria", m.getCurso().getCategoria());
                    cursoMap.put("cargaHoraria", m.getCurso().getCargaHoraria());
                    cursoMap.put("totalMatriculas", ((Integer) cursoMap.getOrDefault("totalMatriculas", 0)) + 1);
                });

        return cursosMap.values().stream()
                .sorted((a, b) -> Integer.compare(
                        (Integer) b.get("totalMatriculas"),
                        (Integer) a.get("totalMatriculas")))
                .toList();
    }

    @Override
    public List<Map<String, Object>> matriculasPorPeriodo(LocalDateTime dataInicio, LocalDateTime dataFim) {
        List<Map<String, Object>> resultados = new ArrayList<>();
        
        matriculaRepositorio.buscarPorPeriodo(dataInicio, dataFim).forEach(m -> {
            Map<String, Object> map = new HashMap<>();
            map.put("idMatricula", m.getId());
            map.put("nomeAluno", m.getAluno().getNome());
            map.put("emailAluno", m.getAluno().getEmail());
            map.put("nomeCurso", m.getCurso().getNome());
            map.put("categoriaCurso", m.getCurso().getCategoria());
            map.put("dataMatricula", m.getDataMatricula());
            map.put("status", m.getStatus());
            resultados.add(map);
        });

        return resultados;
    }
}

