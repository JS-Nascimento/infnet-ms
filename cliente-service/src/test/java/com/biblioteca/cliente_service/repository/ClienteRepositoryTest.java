package com.biblioteca.cliente_service.repository;

import com.biblioteca.cliente_service.model.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ClienteRepositoryTest {

    @Autowired
    private ClienteRepository clienteRepository;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = new Cliente(null, "John Doe", "john@example.com", "123456789", "12345678900");
        clienteRepository.save(cliente);
    }

    @Test
    void shouldSaveCliente() {
        Cliente savedCliente = clienteRepository.save(cliente);

        assertThat(savedCliente).isNotNull();
        assertThat(savedCliente.getId()).isNotNull();
        assertThat(savedCliente.getNome()).isEqualTo(cliente.getNome());
        assertThat(savedCliente.getEmail()).isEqualTo(cliente.getEmail());
    }

    @Test
    void shouldFindClienteById() {
        Optional<Cliente> foundCliente = clienteRepository.findById(cliente.getId());

        assertThat(foundCliente).isPresent();
        assertThat(foundCliente.get().getId()).isEqualTo(cliente.getId());
        assertThat(foundCliente.get().getNome()).isEqualTo(cliente.getNome());
    }

    @Test
    void shouldDeleteCliente() {
        clienteRepository.deleteById(cliente.getId());
        Optional<Cliente> deletedCliente = clienteRepository.findById(cliente.getId());

        assertThat(deletedCliente).isNotPresent();
    }

    @Test
    void shouldFindAllClientes() {
        Cliente cliente2 = new Cliente(null, "Jane Doe", "jane@example.com", "987654321", "09876543210");
        clienteRepository.save(cliente2);

        Iterable<Cliente> clientes = clienteRepository.findAll();

        assertThat(clientes).hasSize(12);
    }
}
