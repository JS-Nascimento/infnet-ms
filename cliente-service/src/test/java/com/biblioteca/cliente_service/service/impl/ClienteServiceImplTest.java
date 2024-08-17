package com.biblioteca.cliente_service.service.impl;

import com.biblioteca.cliente_service.model.Cliente;
import com.biblioteca.cliente_service.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ClienteServiceImplTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cliente = new Cliente(1L, "John Doe", "john@example.com", "123456789", "12345678900");
    }

    @Test
    void shouldGetAllClientes() {
        when(clienteRepository.findAll()).thenReturn(Arrays.asList(cliente));

        List<Cliente> clientes = clienteService.getAllClientes();

        assertThat(clientes).hasSize(1);
        assertThat(clientes.get(0).getNome()).isEqualTo(cliente.getNome());
        verify(clienteRepository, times(1)).findAll();
    }

    @Test
    void shouldCreateCliente() {
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        Cliente savedCliente = clienteService.createCliente(cliente);

        assertThat(savedCliente).isNotNull();
        assertThat(savedCliente.getId()).isEqualTo(cliente.getId());
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    void shouldGetClienteById() {
        when(clienteRepository.findById(anyLong())).thenReturn(Optional.of(cliente));

        Cliente foundCliente = clienteService.getClienteById(cliente.getId());

        assertThat(foundCliente).isNotNull();
        assertThat(foundCliente.getId()).isEqualTo(cliente.getId());
        verify(clienteRepository, times(1)).findById(cliente.getId());
    }

    @Test
    void shouldThrowExceptionWhenClienteNotFound() {
        when(clienteRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> clienteService.getClienteById(1L))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Cliente não encontrado");

        verify(clienteRepository, times(1)).findById(1L);
    }

    @Test
    void shouldDeleteCliente() {
        doNothing().when(clienteRepository).deleteById(anyLong());

        clienteService.deleteCliente(cliente.getId());

        verify(clienteRepository, times(1)).deleteById(cliente.getId());
    }
}
