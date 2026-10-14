package com.escola.api.service;

import com.escola.api.entity.Aluno;
import com.escola.api.repository.AlunoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;

    // Injeção de dependência via construtor: o Spring entrega o Bean do Repository
    public AlunoService(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    public List<Aluno> listarTodos() {
        return alunoRepository.findAll();
    }

    public Aluno buscarPorId(Long id) {
        return alunoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Aluno com id " + id + " não encontrado"));
    }

    public List<Aluno> buscarPorNome(String nome) {
        return alunoRepository.findByNomeContainingIgnoreCase(nome);
    }

    public Aluno salvar(Aluno aluno) {
        aluno.setId(null); // garante que será um INSERT, e não um UPDATE
        return alunoRepository.save(aluno);
    }

    public Aluno atualizar(Long id, Aluno dados) {
        Aluno aluno = buscarPorId(id);
        aluno.setNome(dados.getNome());
        aluno.setRa(dados.getRa());
        aluno.setEmail(dados.getEmail());
        return alunoRepository.save(aluno);
    }

    public void excluir(Long id) {
        Aluno aluno = buscarPorId(id);
        alunoRepository.delete(aluno);
    }
}
