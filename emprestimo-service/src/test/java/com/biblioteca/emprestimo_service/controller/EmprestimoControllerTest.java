package com.biblioteca.emprestimo_service.controller;

import com.biblioteca.emprestimo_service.model.Emprestimo;
import com.biblioteca.emprestimo_service.service.EmprestimoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmprestimoController.class)
class EmprestimoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmprestimoService emprestimoService;

    private Emprestimo emprestimo;

    @BeforeEach
    void setUp() {
        emprestimo = new Emprestimo(1L, 1L, 1L, LocalDate.now(), null, false);
    }

    @Test
    void shouldGetAllEmprestimos() throws Exception {
        when(emprestimoService.getAllEmprestimos()).thenReturn(Arrays.asList(emprestimo));

        mockMvc.perform(get("/emprestimos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].clienteId").value(emprestimo.getClienteId()));

        verify(emprestimoService, times(1)).getAllEmprestimos();
    }

    @Test
    void shouldCreateEmprestimo() throws Exception {
        when(emprestimoService.createEmprestimo(anyLong(), anyLong())).thenReturn(emprestimo);

        mockMvc.perform(post("/emprestimos")
                        .param("clienteId", emprestimo.getClienteId().toString())
                        .param("livroId", emprestimo.getLivroId().toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(emprestimo.getId()));

        verify(emprestimoService, times(1)).createEmprestimo(emprestimo.getClienteId(), emprestimo.getLivroId());
    }

    @Test
    void shouldGetEmprestimoById() throws Exception {
        when(emprestimoService.getEmprestimoById(anyLong())).thenReturn(emprestimo);

        mockMvc.perform(get("/emprestimos/{id}", emprestimo.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(emprestimo.getId()));

        verify(emprestimoService, times(1)).getEmprestimoById(emprestimo.getId());
    }

    @Test
    void shouldDevolverLivro() throws Exception {
        doNothing().when(emprestimoService).devolverLivro(anyLong());

        mockMvc.perform(put("/emprestimos/{id}/devolver", emprestimo.getId()))
                .andExpect(status().isOk());

        verify(emprestimoService, times(1)).devolverLivro(emprestimo.getId());
    }
}
