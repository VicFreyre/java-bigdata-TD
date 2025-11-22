package br.com.sistema.matriculas.controlador;

import br.com.sistema.matriculas.relatorio.RelatorioServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/relatorios")
public class RelatorioControlador {

    @Autowired
    private RelatorioServico relatorioServico;

    @GetMapping
    public String index() {
        return "redirect:/";
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
}

