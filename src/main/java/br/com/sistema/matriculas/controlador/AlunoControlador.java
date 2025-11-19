package br.com.sistema.matriculas.controlador;

import br.com.sistema.matriculas.dto.AlunoDTO;
import br.com.sistema.matriculas.dto.MatriculaDTO;
import br.com.sistema.matriculas.servico.AlunoServico;
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

@Controller
@RequestMapping("/alunos")
public class AlunoControlador {

    @Autowired
    private AlunoServico alunoServico;

    @Autowired
    private MatriculaServico matriculaServico;

    @GetMapping
    public String listar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<AlunoDTO> alunos;

        if (nome != null && !nome.trim().isEmpty()) {
            alunos = alunoServico.buscarPorNome(nome, pageable);
            model.addAttribute("nome", nome);
        } else {
            alunos = alunoServico.listarTodos(pageable);
        }

        model.addAttribute("alunos", alunos);
        return "alunos-listar";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("alunoDTO", new AlunoDTO());
        return "alunos-form";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute AlunoDTO alunoDTO,
                        BindingResult result,
                        RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "alunos-form";
        }

        try {
            alunoServico.salvar(alunoDTO);
            redirectAttributes.addFlashAttribute("sucesso", "Aluno cadastrado com sucesso!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
            return "redirect:/alunos/novo";
        }

        return "redirect:/alunos";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return alunoServico.buscarPorId(id)
                .map(alunoDTO -> {
                    model.addAttribute("alunoDTO", alunoDTO);
                    return "alunos-form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("erro", "Aluno não encontrado");
                    return "redirect:/alunos";
                });
    }

    @PostMapping("/atualizar/{id}")
    public String atualizar(@PathVariable Long id,
                           @Valid @ModelAttribute AlunoDTO alunoDTO,
                           BindingResult result,
                           RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "alunos-form";
        }

        try {
            alunoServico.atualizar(id, alunoDTO);
            redirectAttributes.addFlashAttribute("sucesso", "Aluno atualizado com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
            return "redirect:/alunos/editar/" + id;
        }

        return "redirect:/alunos";
    }

    @GetMapping("/detalhes/{id}")
    public String detalhes(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return alunoServico.buscarPorId(id)
                .map(alunoDTO -> {
                    java.util.List<MatriculaDTO> historico = matriculaServico.buscarPorAluno(id);
                    model.addAttribute("aluno", alunoDTO);
                    model.addAttribute("historico", historico);
                    return "alunos-detalhes";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("erro", "Aluno não encontrado");
                    return "redirect:/alunos";
                });
    }

    @PostMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            alunoServico.excluir(id);
            redirectAttributes.addFlashAttribute("sucesso", "Aluno excluído com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
        }

        return "redirect:/alunos";
    }
}

