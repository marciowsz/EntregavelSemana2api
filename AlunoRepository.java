package com.escola.api.repository;

import com.escola.api.entity.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    // Query Method: o Spring gera o SQL a partir do nome do método
    // SELECT * FROM aluno WHERE UPPER(nome) LIKE UPPER('%?%')
    List<Aluno> findByNomeContainingIgnoreCase(String nome);
}
