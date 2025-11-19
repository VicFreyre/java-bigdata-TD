package br.com.sistema.matriculas.servico;

import br.com.sistema.matriculas.dto.CursoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CursoServico {

    Page<CursoDTO> listarTodos(Pageable pageable);

    Page<CursoDTO> buscarPorNome(String nome, Pageable pageable);

    Optional<CursoDTO> buscarPorId(Long id);

    CursoDTO salvar(CursoDTO cursoDTO);

    CursoDTO atualizar(Long id, CursoDTO cursoDTO);

    void excluir(Long id);

    List<CursoDTO> listarTodos();
}

