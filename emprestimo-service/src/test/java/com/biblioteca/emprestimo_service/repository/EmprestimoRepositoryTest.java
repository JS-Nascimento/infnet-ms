package com.biblioteca.emprestimo_service.repository;

import com.biblioteca.emprestimo_service.model.Emprestimo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class EmprestimoRepositoryTest {

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    @Test
    @DisplayName("Deve salvar um empréstimo")
    void shouldSaveEmprestimo() {

        Emprestimo emprestimo = new Emprestimo(null, 1L, 1L, LocalDate.now(), null, false);


        Emprestimo savedEmprestimo = emprestimoRepository.save(emprestimo);


        assertThat(savedEmprestimo.getId()).isNotNull();
        assertThat(savedEmprestimo.getClienteId()).isEqualTo(1L);
        assertThat(savedEmprestimo.getLivroId()).isEqualTo(1L);
        assertThat(savedEmprestimo.getDataEmprestimo()).isEqualTo(LocalDate.now());
        assertThat(savedEmprestimo.getDevolvido()).isFalse();
    }

    @Test
    @DisplayName("Deve encontrar um empréstimo por ID")
    void shouldFindEmprestimoById() {

        Emprestimo emprestimo = new Emprestimo(null, 1L, 1L, LocalDate.now(), null, false);
        emprestimo = emprestimoRepository.save(emprestimo);
        Long id = emprestimo.getId();


        Optional<Emprestimo> foundEmprestimo = emprestimoRepository.findById(id);


        assertThat(foundEmprestimo).isPresent();
        assertThat(foundEmprestimo.get().getId()).isEqualTo(id);
    }


    @Test
    @DisplayName("Deve deletar um empréstimo por ID")
    @Sql("/insert_emprestimo.sql")
    void shouldDeleteEmprestimoById() {

        Long id = 1L;

        emprestimoRepository.deleteById(id);
        Optional<Emprestimo> deletedEmprestimo = emprestimoRepository.findById(id);

        assertThat(deletedEmprestimo).isNotPresent();
    }
}
