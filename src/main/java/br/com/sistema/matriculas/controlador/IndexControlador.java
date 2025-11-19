package br.com.sistema.matriculas.controlador;

import br.com.sistema.matriculas.repositorio.AlunoRepositorio;
import br.com.sistema.matriculas.repositorio.CursoRepositorio;
import br.com.sistema.matriculas.repositorio.MatriculaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexControlador {

    @Autowired
    private AlunoRepositorio alunoRepositorio;

    @Autowired
    private CursoRepositorio cursoRepositorio;

    @Autowired
    private MatriculaRepositorio matriculaRepositorio;

    @GetMapping("/")
    public String index(Model model) {
        long totalAlunos = alunoRepositorio.count();
        long totalCursos = cursoRepositorio.count();
        long matriculasAtivas = matriculaRepositorio.findAll().stream()
                .filter(m -> "ATIVA".equals(m.getStatus()))
                .count();

        model.addAttribute("totalAlunos", totalAlunos);
        model.addAttribute("totalCursos", totalCursos);
        model.addAttribute("matriculasAtivas", matriculasAtivas);

        return "index";
    }
}

