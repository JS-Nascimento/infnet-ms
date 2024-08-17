package com.biblioteca.catalogo_service.controller;

import com.biblioteca.catalogo_service.model.Livro;
import com.biblioteca.catalogo_service.service.LivroService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class LivroControllerTest {

    private MockMvc mockMvc;

    @Mock
    private LivroService livroService;

    @InjectMocks
    private LivroController livroController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(livroController).build();
    }

    @Test
    void getAllLivros_ReturnsListOfLivros() throws Exception {
        List<Livro> livros = Arrays.asList(new Livro(), new Livro());
        when(livroService.getAllLivros()).thenReturn(livros);

        mockMvc.perform(get("/livros"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(livros.size()));
    }

    @Test
    void createLivro_ReturnsCreatedLivro() throws Exception {
        Livro livro = new Livro();
        livro.setTitulo("Test Livro");
        when(livroService.createLivro(any(Livro.class))).thenReturn(livro);

        mockMvc.perform(post("/livros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"titulo\":\"Test Livro\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Test Livro"));
    }

    @Test
    void getLivroById_ReturnsLivro() throws Exception {
        Livro livro = new Livro();
        livro.setId(1L);
        when(livroService.getLivroById(anyLong())).thenReturn(livro);

        mockMvc.perform(get("/livros/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void updateLivro_ReturnsUpdatedLivro() throws Exception {
        Livro livro = new Livro();
        livro.setTitulo("Updated Livro");
        when(livroService.updateLivro(anyLong(), any(Livro.class))).thenReturn(livro);

        mockMvc.perform(put("/livros/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"titulo\":\"Updated Livro\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Updated Livro"));
    }
}