package com.biblioteca.catalogo_service.repository;

import com.biblioteca.catalogo_service.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class LivroRepositoryTest {

    @Autowired
    private LivroRepository livroRepository;


    @Test
    void saveLivro_SavesLivroSuccessfully() {
        Livro livro = new Livro();
        livro.setTitulo("The Pragmatic Programmer");
        livro.setAutor("Andrew Hunt");
        livro.setIsbn("978-0201616224");
        livro.setEmprestado(false);

        Livro savedLivro = livroRepository.save(livro);
        assertNotNull(savedLivro.getId());
    }

    @Test
    void findById_ReturnsLivroWhenLivroExists() {
        Livro livro = new Livro();
        livro.setTitulo("Design Patterns");
        livro.setAutor("Erich Gamma");
        livro.setIsbn("978-0201633610");
        livro.setEmprestado(false);

        Livro savedLivro = livroRepository.save(livro);
        Optional<Livro> foundLivro = livroRepository.findById(savedLivro.getId());
        assertTrue(foundLivro.isPresent());
    }

    @Test
    void findById_ReturnsEmptyWhenLivroDoesNotExist() {
        Optional<Livro> foundLivro = livroRepository.findById(999L);
        assertFalse(foundLivro.isPresent());
    }

    @Test
    @Transactional
    void deleteById_DeletesLivroSuccessfully() {
        Livro livro = new Livro();
        livro.setTitulo("Test Livro");
        livro.setAutor("Test Autor");
        livro.setIsbn("123-4567890123");
        livro.setEmprestado(false);

        Livro savedLivro = livroRepository.save(livro);
        livroRepository.deleteById(savedLivro.getId());
        Optional<Livro> foundLivro = livroRepository.findById(savedLivro.getId());
        assertFalse(foundLivro.isPresent());
    }
}
