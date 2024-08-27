package com.biblioteca.emprestimo_service.controller;

import com.biblioteca.emprestimo_service.model.Emprestimo;
import com.biblioteca.emprestimo_service.service.EmprestimoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/emprestimos")
public class EmprestimoController {

    private final EmprestimoService emprestimoService;

    @GetMapping
    public List<Emprestimo> getAllEmprestimos() {
        return emprestimoService.getAllEmprestimos();
    }

    @PostMapping
    public Emprestimo createEmprestimo(@RequestParam Long clienteId, @RequestParam Long livroId) {
        return emprestimoService.createEmprestimo(clienteId, livroId);
    }

    @GetMapping("/{id}")
    public Emprestimo getEmprestimoById(@PathVariable Long id) {
        return emprestimoService.getEmprestimoById(id);
    }

    @PutMapping("/{id}/devolver")
    public void devolverLivro(@PathVariable Long id ) {
        emprestimoService.devolverLivro(id);
    }
}
