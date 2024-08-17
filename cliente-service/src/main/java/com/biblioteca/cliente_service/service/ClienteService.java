package com.biblioteca.cliente_service.service;

import com.biblioteca.cliente_service.model.Cliente;

import java.util.List;

public interface ClienteService {
    List<Cliente> getAllClientes();
    Cliente createCliente(Cliente cliente);
    Cliente getClienteById(Long id);
    void deleteCliente(Long id);
}
