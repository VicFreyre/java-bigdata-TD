package br.com.sistema.matriculas.servico.impl;

import br.com.sistema.matriculas.dto.AlunoDTO;
import br.com.sistema.matriculas.entidade.Aluno;
import br.com.sistema.matriculas.excecao.RecursoNaoEncontradoException;
import br.com.sistema.matriculas.repositorio.AlunoRepositorio;
import br.com.sistema.matriculas.servico.AlunoServico;
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
public class AlunoServicoImpl implements AlunoServico {

    @Autowired
    private AlunoRepositorio alunoRepositorio;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Page<AlunoDTO> listarTodos(Pageable pageable) {
        return alunoRepositorio.findAll(pageable)
                .map(aluno -> modelMapper.map(aluno, AlunoDTO.class));
    }

    @Override
    public Page<AlunoDTO> buscarPorNome(String nome, Pageable pageable) {
        return alunoRepositorio.buscarPorNome(nome, pageable)
                .map(aluno -> modelMapper.map(aluno, AlunoDTO.class));
    }

    @Override
    public Optional<AlunoDTO> buscarPorId(Long id) {
        return alunoRepositorio.findById(id)
                .map(aluno -> modelMapper.map(aluno, AlunoDTO.class));
    }

    @Override
    public AlunoDTO salvar(AlunoDTO alunoDTO) {
        if (alunoRepositorio.existsByEmail(alunoDTO.getEmail())) {
            throw new IllegalArgumentException("Já existe um aluno cadastrado com este email");
        }
        Aluno aluno = modelMapper.map(alunoDTO, Aluno.class);
        aluno = alunoRepositorio.save(aluno);
        return modelMapper.map(aluno, AlunoDTO.class);
    }

    @Override
    public AlunoDTO atualizar(Long id, AlunoDTO alunoDTO) {
        Aluno aluno = alunoRepositorio.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Aluno não encontrado com ID: " + id));
        
        // Verificar se o email já existe em outro aluno
        Optional<Aluno> alunoComEmail = alunoRepositorio.findByEmail(alunoDTO.getEmail());
        if (alunoComEmail.isPresent() && !alunoComEmail.get().getId().equals(id)) {
            throw new IllegalArgumentException("Já existe um aluno cadastrado com este email");
        }
        
        aluno.setNome(alunoDTO.getNome());
        aluno.setIdade(alunoDTO.getIdade());
        aluno.setEndereco(alunoDTO.getEndereco());
        aluno.setEmail(alunoDTO.getEmail());
        aluno.setTelefone(alunoDTO.getTelefone());
        
        aluno = alunoRepositorio.save(aluno);
        return modelMapper.map(aluno, AlunoDTO.class);
    }

    @Override
    public void excluir(Long id) {
        if (!alunoRepositorio.existsById(id)) {
            throw new RecursoNaoEncontradoException("Aluno não encontrado com ID: " + id);
        }
        alunoRepositorio.deleteById(id);
    }

    @Override
    public List<AlunoDTO> listarTodos() {
        return alunoRepositorio.findAll().stream()
                .map(aluno -> modelMapper.map(aluno, AlunoDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean existePorEmail(String email) {
        return alunoRepositorio.existsByEmail(email);
    }
}

