package br.com.sistema.matriculas.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SobreControlador {

    @GetMapping("/sobre")
    public String sobre() {
        return "sobre";
    }
}

