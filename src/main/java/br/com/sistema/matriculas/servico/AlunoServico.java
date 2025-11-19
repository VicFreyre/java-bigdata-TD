package br.com.sistema.matriculas.servico;

import br.com.sistema.matriculas.dto.AlunoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AlunoServico {

    Page<AlunoDTO> listarTodos(Pageable pageable);

    Page<AlunoDTO> buscarPorNome(String nome, Pageable pageable);

    Optional<AlunoDTO> buscarPorId(Long id);

    AlunoDTO salvar(AlunoDTO alunoDTO);

    AlunoDTO atualizar(Long id, AlunoDTO alunoDTO);

    void excluir(Long id);

    List<AlunoDTO> listarTodos();

    boolean existePorEmail(String email);
}

