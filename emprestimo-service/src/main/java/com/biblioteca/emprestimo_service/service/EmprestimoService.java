package com.biblioteca.emprestimo_service.service;

import com.biblioteca.emprestimo_service.model.Emprestimo;

import java.util.List;

public interface EmprestimoService {
    List<Emprestimo> getAllEmprestimos();
    Emprestimo createEmprestimo(Long clienteId, Long livroId);
    Emprestimo getEmprestimoById(Long id);
    void devolverLivro(Long emprestimoId);
}

