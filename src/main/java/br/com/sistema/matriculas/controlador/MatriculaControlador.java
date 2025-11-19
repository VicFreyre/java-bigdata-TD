package br.com.sistema.matriculas.controlador;

import br.com.sistema.matriculas.dto.AlunoDTO;
import br.com.sistema.matriculas.dto.CursoDTO;
import br.com.sistema.matriculas.dto.MatriculaDTO;
import br.com.sistema.matriculas.servico.AlunoServico;
import br.com.sistema.matriculas.servico.CursoServico;
import br.com.sistema.matriculas.servico.MatriculaServico;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/matriculas")
public class MatriculaControlador {

    @Autowired
    private MatriculaServico matriculaServico;

    @Autowired
    private AlunoServico alunoServico;

    @Autowired
    private CursoServico cursoServico;

    @GetMapping
    public String listar(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<MatriculaDTO> matriculas = matriculaServico.listarTodas(pageable);

        model.addAttribute("matriculas", matriculas);
        return "matriculas-listar";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        List<AlunoDTO> alunos = alunoServico.listarTodos();
        List<CursoDTO> cursos = cursoServico.listarTodos();

        model.addAttribute("matriculaDTO", new MatriculaDTO());
        model.addAttribute("alunos", alunos);
        model.addAttribute("cursos", cursos);
        return "matriculas-form";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute MatriculaDTO matriculaDTO,
                        BindingResult result,
                        RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            List<AlunoDTO> alunos = alunoServico.listarTodos();
            List<CursoDTO> cursos = cursoServico.listarTodos();
            redirectAttributes.addFlashAttribute("alunos", alunos);
            redirectAttributes.addFlashAttribute("cursos", cursos);
            return "matriculas-form";
        }

        try {
            matriculaServico.salvar(matriculaDTO);
            redirectAttributes.addFlashAttribute("sucesso", "Matrícula realizada com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
            return "redirect:/matriculas/novo";
        }

        return "redirect:/matriculas";
    }

    @GetMapping("/detalhes/{id}")
    public String detalhes(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return matriculaServico.buscarPorId(id)
                .map(matriculaDTO -> {
                    List<MatriculaDTO> historico = matriculaServico.buscarPorAluno(matriculaDTO.getIdAluno());
                    model.addAttribute("matricula", matriculaDTO);
                    model.addAttribute("historico", historico);
                    return "matriculas-detalhes";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("erro", "Matrícula não encontrada");
                    return "redirect:/matriculas";
                });
    }

    @PostMapping("/cancelar/{id}")
    public String cancelar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            matriculaServico.cancelar(id);
            redirectAttributes.addFlashAttribute("sucesso", "Matrícula cancelada com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }

        return "redirect:/matriculas";
    }
}

