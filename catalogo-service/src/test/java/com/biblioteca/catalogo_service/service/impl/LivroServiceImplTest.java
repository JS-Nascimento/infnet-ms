package com.biblioteca.catalogo_service.service.impl;

import com.biblioteca.catalogo_service.model.Livro;
import com.biblioteca.catalogo_service.repository.LivroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LivroServiceImplTest {

    @Mock
    private LivroRepository livroRepository;

    @InjectMocks
    private LivroServiceImpl livroService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllLivros_ReturnsListOfLivros() {
        Livro livro1 = new Livro();
        Livro livro2 = new Livro();
        when(livroRepository.findAll()).thenReturn(Arrays.asList(livro1, livro2));

        List<Livro> livros = livroService.getAllLivros();

        assertEquals(2, livros.size());
        verify(livroRepository, times(1)).findAll();
    }

    @Test
    void createLivro_SavesAndReturnsLivro() {
        Livro livro = new Livro();
        when(livroRepository.save(livro)).thenReturn(livro);

        Livro createdLivro = livroService.createLivro(livro);

        assertNotNull(createdLivro);
        verify(livroRepository, times(1)).save(livro);
    }

    @Test
    void getLivroById_ExistingId_ReturnsLivro() {
        Livro livro = new Livro();
        when(livroRepository.findById(1L)).thenReturn(Optional.of(livro));

        Livro foundLivro = livroService.getLivroById(1L);

        assertNotNull(foundLivro);
        verify(livroRepository, times(1)).findById(1L);
    }

    @Test
    void getLivroById_NonExistingId_ThrowsException() {
        when(livroRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> livroService.getLivroById(1L));
        verify(livroRepository, times(1)).findById(1L);
    }

    @Test
    void updateLivro_ExistingId_UpdatesAndReturnsLivro() {
        Livro existingLivro = new Livro();
        existingLivro.setId(1L);
        Livro updatedLivro = new Livro();
        updatedLivro.setTitulo("New Title");
        updatedLivro.setAutor("New Author");
        updatedLivro.setIsbn("New ISBN");
        updatedLivro.setEmprestado(true);

        when(livroRepository.findById(1L)).thenReturn(Optional.of(existingLivro));
        when(livroRepository.save(existingLivro)).thenReturn(existingLivro);

        Livro result = livroService.updateLivro(1L, updatedLivro);

        assertNull(result);
        assertEquals("New Title", existingLivro.getTitulo());
        assertEquals("New Author", existingLivro.getAutor());
        assertEquals("New ISBN", existingLivro.getIsbn());
        assertTrue(existingLivro.getEmprestado());
        verify(livroRepository, times(1)).findById(1L);
        verify(livroRepository, times(1)).save(existingLivro);
    }

    @Test
    void updateLivro_NonExistingId_ThrowsException() {
        Livro updatedLivro = new Livro();
        when(livroRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> livroService.updateLivro(1L, updatedLivro));
        verify(livroRepository, times(1)).findById(1L);
    }
}