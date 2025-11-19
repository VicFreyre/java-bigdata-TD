package br.com.sistema.matriculas.controlador;

import br.com.sistema.matriculas.relatorio.RelatorioServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/relatorios")
public class RelatorioControlador {

    @Autowired
    private RelatorioServico relatorioServico;

    @GetMapping
    public String index() {
        return "relatorios";
    }

    @GetMapping("/alunos-por-curso")
    public String alunosPorCurso(Model model) {
        List<Map<String, Object>> dados = relatorioServico.alunosPorCurso();
        model.addAttribute("dados", dados);
        return "relatorios-alunos-por-curso";
    }

    @GetMapping("/cursos-mais-procurados")
    public String cursosMaisProcurados(Model model) {
        List<Map<String, Object>> dados = relatorioServico.cursosMaisProcurados();
        model.addAttribute("dados", dados);
        return "relatorios-cursos-mais-procurados";
    }

    @GetMapping("/matriculas-por-periodo")
    public String matriculasPorPeriodo(
            @RequestParam(value = "dataInicio", required = false) 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
            @RequestParam(value = "dataFim", required = false) 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim,
            Model model) {

        if (dataInicio == null) {
            dataInicio = LocalDateTime.now().minusMonths(1);
        }
        if (dataFim == null) {
            dataFim = LocalDateTime.now();
        }

        List<Map<String, Object>> dados = relatorioServico.matriculasPorPeriodo(dataInicio, dataFim);
        
        model.addAttribute("dados", dados);
        model.addAttribute("dataInicio", dataInicio);
        model.addAttribute("dataFim", dataFim);
        
        return "relatorios-matriculas-por-periodo";
    }
}

