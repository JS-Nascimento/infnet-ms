package com.biblioteca.cliente_service.controller;

import com.biblioteca.cliente_service.model.Cliente;
import com.biblioteca.cliente_service.service.ClienteService;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ClienteControllerTest {

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private ClienteController clienteController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(clienteController).build();
    }

    @Test
    void getAllClientes_shouldReturnListOfClientes() throws Exception {
        Cliente cliente1 = new Cliente(1L, "John Doe", "john@example.com", "123456789", "12345678900");
        Cliente cliente2 = new Cliente(2L, "Jane Doe", "jane@example.com", "987654321", "09876543210");
        List<Cliente> clientes = Arrays.asList(cliente1, cliente2);

        when(clienteService.getAllClientes()).thenReturn(clientes);

        mockMvc.perform(get("/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(clientes.size()))
                .andExpect(jsonPath("$[0].nome").value(cliente1.getNome()))
                .andExpect(jsonPath("$[1].nome").value(cliente2.getNome()));

        verify(clienteService, times(1)).getAllClientes();
    }

    @Test
    void createCliente_shouldReturnCreatedCliente() throws Exception {
        Cliente cliente = new Cliente(1L, "John Doe", "john@example.com", "123456789", "12345678900");

        when(clienteService.createCliente(any(Cliente.class))).thenReturn(cliente);

        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"John Doe\",\"email\":\"john@example.com\",\"telefone\":\"123456789\",\"cpf\":\"12345678900\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(cliente.getId()))
                .andExpect(jsonPath("$.nome").value(cliente.getNome()));

        verify(clienteService, times(1)).createCliente(any(Cliente.class));
    }

    @Test
    void getClienteById_shouldReturnCliente() throws Exception {
        Cliente cliente = new Cliente(1L, "John Doe", "john@example.com", "123456789", "12345678900");

        when(clienteService.getClienteById(anyLong())).thenReturn(cliente);

        mockMvc.perform(get("/clientes/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(cliente.getId()))
                .andExpect(jsonPath("$.nome").value(cliente.getNome()));

        verify(clienteService, times(1)).getClienteById(anyLong());
    }

    @Test
    void deleteCliente_shouldReturnNoContent() throws Exception {
        doNothing().when(clienteService).deleteCliente(anyLong());

        mockMvc.perform(delete("/clientes/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(clienteService, times(1)).deleteCliente(anyLong());
    }
}
