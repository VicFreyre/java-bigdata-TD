package br.com.sistema.matriculas.controlador;

import br.com.sistema.matriculas.dto.CursoDTO;
import br.com.sistema.matriculas.dto.MatriculaDTO;
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

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/cursos")
public class CursoControlador {

    @Autowired
    private CursoServico cursoServico;

    @Autowired
    private MatriculaServico matriculaServico;

    private static final List<String> CATEGORIAS = Arrays.asList(
            "Tecnologia", "Administração", "Saúde", "Linguagens", "Gestão", "Outros"
    );

    @GetMapping
    public String listar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<CursoDTO> cursos;

        if (nome != null && !nome.trim().isEmpty()) {
            cursos = cursoServico.buscarPorNome(nome, pageable);
            model.addAttribute("nome", nome);
        } else {
            cursos = cursoServico.listarTodos(pageable);
        }

        model.addAttribute("cursos", cursos);
        model.addAttribute("categorias", CATEGORIAS);
        return "cursos-listar";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("cursoDTO", new CursoDTO());
        model.addAttribute("categorias", CATEGORIAS);
        return "cursos-form";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute CursoDTO cursoDTO,
                        BindingResult result,
                        RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("categorias", CATEGORIAS);
            return "cursos-form";
        }

        try {
            cursoServico.salvar(cursoDTO);
            redirectAttributes.addFlashAttribute("sucesso", "Curso cadastrado com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
            return "redirect:/cursos/novo";
        }

        return "redirect:/cursos";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return cursoServico.buscarPorId(id)
                .map(cursoDTO -> {
                    model.addAttribute("cursoDTO", cursoDTO);
                    model.addAttribute("categorias", CATEGORIAS);
                    return "cursos-form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("erro", "Curso não encontrado");
                    return "redirect:/cursos";
                });
    }

    @PostMapping("/atualizar/{id}")
    public String atualizar(@PathVariable Long id,
                           @Valid @ModelAttribute CursoDTO cursoDTO,
                           BindingResult result,
                           RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("categorias", CATEGORIAS);
            return "cursos-form";
        }

        try {
            cursoServico.atualizar(id, cursoDTO);
            redirectAttributes.addFlashAttribute("sucesso", "Curso atualizado com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
            return "redirect:/cursos/editar/" + id;
        }

        return "redirect:/cursos";
    }

    @GetMapping("/detalhes/{id}")
    public String detalhes(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return cursoServico.buscarPorId(id)
                .map(cursoDTO -> {
                    List<MatriculaDTO> alunosMatriculados = matriculaServico.buscarPorCurso(id);
                    model.addAttribute("curso", cursoDTO);
                    model.addAttribute("alunosMatriculados", alunosMatriculados);
                    return "cursos-detalhes";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("erro", "Curso não encontrado");
                    return "redirect:/cursos";
                });
    }

    @PostMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            cursoServico.excluir(id);
            redirectAttributes.addFlashAttribute("sucesso", "Curso excluído com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }

        return "redirect:/cursos";
    }
}

