package br.com.sistema.matriculas.servico.impl;

import br.com.sistema.matriculas.dto.CursoDTO;
import br.com.sistema.matriculas.entidade.Curso;
import br.com.sistema.matriculas.excecao.RecursoNaoEncontradoException;
import br.com.sistema.matriculas.repositorio.CursoRepositorio;
import br.com.sistema.matriculas.servico.CursoServico;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CursoServicoImpl implements CursoServico {

    @Autowired
    private CursoRepositorio cursoRepositorio;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Page<CursoDTO> listarTodos(Pageable pageable) {
        return cursoRepositorio.findAll(pageable)
                .map(curso -> modelMapper.map(curso, CursoDTO.class));
    }

    @Override
    public Page<CursoDTO> buscarPorNome(String nome, Pageable pageable) {
        return cursoRepositorio.buscarPorNome(nome, pageable)
                .map(curso -> modelMapper.map(curso, CursoDTO.class));
    }

    @Override
    public Optional<CursoDTO> buscarPorId(Long id) {
        return cursoRepositorio.findById(id)
                .map(curso -> modelMapper.map(curso, CursoDTO.class));
    }

    @Override
    public CursoDTO salvar(CursoDTO cursoDTO) {
        Curso curso = modelMapper.map(cursoDTO, Curso.class);
        curso = cursoRepositorio.save(curso);
        return modelMapper.map(curso, CursoDTO.class);
    }

    @Override
    public CursoDTO atualizar(Long id, CursoDTO cursoDTO) {
        Curso curso = cursoRepositorio.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Curso não encontrado com ID: " + id));
        
        curso.setNome(cursoDTO.getNome());
        curso.setDescricao(cursoDTO.getDescricao());
        curso.setCargaHoraria(cursoDTO.getCargaHoraria());
        curso.setCategoria(cursoDTO.getCategoria());
        
        curso = cursoRepositorio.save(curso);
        return modelMapper.map(curso, CursoDTO.class);
    }

    @Override
    public void excluir(Long id) {
        if (!cursoRepositorio.existsById(id)) {
            throw new RecursoNaoEncontradoException("Curso não encontrado com ID: " + id);
        }
        cursoRepositorio.deleteById(id);
    }

    @Override
    public List<CursoDTO> listarTodos() {
        return cursoRepositorio.findAll().stream()
                .map(curso -> modelMapper.map(curso, CursoDTO.class))
                .collect(Collectors.toList());
    }
}

