package br.com.sistema.matriculas.servico.impl;

import br.com.sistema.matriculas.dto.MatriculaDTO;
import br.com.sistema.matriculas.entidade.Aluno;
import br.com.sistema.matriculas.entidade.Curso;
import br.com.sistema.matriculas.entidade.Matricula;
import br.com.sistema.matriculas.excecao.RecursoNaoEncontradoException;
import br.com.sistema.matriculas.repositorio.AlunoRepositorio;
import br.com.sistema.matriculas.repositorio.CursoRepositorio;
import br.com.sistema.matriculas.repositorio.MatriculaRepositorio;
import br.com.sistema.matriculas.servico.MatriculaServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class MatriculaServicoImpl implements MatriculaServico {
    @Override
    public void reativar(Long id) {
        Matricula matricula = matriculaRepositorio.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Matrícula não encontrada com ID: " + id));
        if (!"TRANCADA".equals(matricula.getStatus())) {
            throw new IllegalArgumentException("Apenas matrículas trancadas podem ser reativadas");
        }
        matricula.reativar();
        matriculaRepositorio.save(matricula);
    }

    @Autowired
    private MatriculaRepositorio matriculaRepositorio;

    @Autowired
    private AlunoRepositorio alunoRepositorio;

    @Autowired
    private CursoRepositorio cursoRepositorio;

    @Override
    public Page<MatriculaDTO> buscarPorNumeroMatricula(String numeroMatricula, Pageable pageable) {
        return matriculaRepositorio.buscarPorNumeroMatricula(numeroMatricula, pageable)
                .map(this::converterParaDTO);
    }

    @Override
    public Page<MatriculaDTO> listarTodas(Pageable pageable) {
        return matriculaRepositorio.findAll(pageable)
                .map(this::converterParaDTO);
    }

    @Override
    public Optional<MatriculaDTO> buscarPorId(Long id) {
        return matriculaRepositorio.findById(id)
                .map(this::converterParaDTO);
    }

    @Override
    public MatriculaDTO salvar(MatriculaDTO matriculaDTO) {
        Aluno aluno = alunoRepositorio.findById(matriculaDTO.getIdAluno())
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Aluno não encontrado com ID: " + matriculaDTO.getIdAluno()));

        Curso curso = cursoRepositorio.findById(matriculaDTO.getIdCurso())
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Curso não encontrado com ID: " + matriculaDTO.getIdCurso()));

        // Verificar se já existe matrícula ativa
        Optional<Matricula> matriculaExistente = matriculaRepositorio.buscarMatriculaAtiva(
                matriculaDTO.getIdAluno(), matriculaDTO.getIdCurso());

        if (matriculaExistente.isPresent()) {
            throw new IllegalArgumentException("Aluno já possui matrícula ativa neste curso");
        }

        // Gerar número de matrícula: 4 dígitos aleatórios + "25"
        String numeroMatricula = String.format("%04d25", (int) (Math.random() * 10000));

        Matricula matricula = new Matricula(aluno, curso, numeroMatricula);
        matricula = matriculaRepositorio.save(matricula);

        return converterParaDTO(matricula);
    }

    @Override
    public void cancelar(Long id) {
        Matricula matricula = matriculaRepositorio.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Matrícula não encontrada com ID: " + id));

        if ("CANCELADA".equals(matricula.getStatus())) {
            throw new IllegalArgumentException("Matrícula já está cancelada");
        }

        matricula.cancelar();
        matriculaRepositorio.save(matricula);
    }

    @Override
    public void trancar(Long id) {
        Matricula matricula = matriculaRepositorio.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Matrícula não encontrada com ID: " + id));

        if (!"ATIVA".equals(matricula.getStatus())) {
            throw new IllegalArgumentException("Apenas matrículas ativas podem ser trancadas");
        }

        matricula.trancar();
        matriculaRepositorio.save(matricula);
    }


    @Override
    public List<MatriculaDTO> buscarPorAluno(Long idAluno) {
        return matriculaRepositorio.buscarPorAluno(idAluno).stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MatriculaDTO> buscarPorCurso(Long idCurso) {
        return matriculaRepositorio.buscarPorCurso(idCurso).stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MatriculaDTO> buscarPorPeriodo(LocalDateTime dataInicio, LocalDateTime dataFim) {
        return matriculaRepositorio.buscarPorPeriodo(dataInicio, dataFim).stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Long contarMatriculasAtivasPorCurso(Long idCurso) {
        return matriculaRepositorio.contarMatriculasAtivasPorCurso(idCurso);
    }

    private MatriculaDTO converterParaDTO(Matricula matricula) {
        MatriculaDTO dto = new MatriculaDTO();
        dto.setId(matricula.getId());
        dto.setIdAluno(matricula.getAluno().getId());
        dto.setNomeAluno(matricula.getAluno().getNome());
        dto.setEmailAluno(matricula.getAluno().getEmail());
        dto.setIdCurso(matricula.getCurso().getId());
        dto.setNomeCurso(matricula.getCurso().getNome());
        dto.setDataMatricula(matricula.getDataMatricula());
        dto.setNumeroMatricula(matricula.getNumeroMatricula());
        dto.setStatus(matricula.getStatus());
        dto.setDataCancelamento(matricula.getDataCancelamento());
        return dto;
    }
}
