package com.escola.api.controller;

import com.escola.api.entity.Aluno;
import com.escola.api.service.AlunoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    // ===== DESAFIO PRÁTICO: troque pelos seus dados =====
    private static final String MEU_NOME = "Seu Nome";
    private static final String MEU_RA = "000000";

    private final AlunoService alunoService;

    // Injeção de dependência via construtor: o Spring entrega o Bean do Service
    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    // Desafio: GET /alunos/ra -> "O RA do aluno Seu Nome é 000000"
    @GetMapping("/ra")
    public String meuRa() {
        return "O RA do aluno " + MEU_NOME + " é " + MEU_RA;
    }

    // GET /alunos -> lista todos
    @GetMapping
    public List<Aluno> listar() {
        return alunoService.listarTodos();
    }

    // GET /alunos/buscar?nome=ana -> filtro com @RequestParam
    @GetMapping("/buscar")
    public List<Aluno> buscarPorNome(@RequestParam String nome) {
        return alunoService.buscarPorNome(nome);
    }

    // GET /alunos/1 -> busca por id com @PathVariable
    @GetMapping("/{id}")
    public Aluno buscarPorId(@PathVariable Long id) {
        return alunoService.buscarPorId(id);
    }

    // POST /alunos -> cria a partir do JSON do corpo (@RequestBody)
    @PostMapping
    public ResponseEntity<Aluno> criar(@RequestBody Aluno aluno) {
        Aluno salvo = alunoService.salvar(aluno);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    // PUT /alunos/1 -> atualiza
    @PutMapping("/{id}")
    public Aluno atualizar(@PathVariable Long id, @RequestBody Aluno aluno) {
        return alunoService.atualizar(id, aluno);
    }

    // DELETE /alunos/1 -> exclui
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        alunoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
