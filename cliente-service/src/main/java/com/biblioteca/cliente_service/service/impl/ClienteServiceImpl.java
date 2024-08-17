package com.biblioteca.cliente_service.service.impl;

import com.biblioteca.cliente_service.model.Cliente;
import com.biblioteca.cliente_service.repository.ClienteRepository;
import com.biblioteca.cliente_service.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    @Override
    public List<Cliente> getAllClientes() {

        return clienteRepository.findAll();
    }

    @Override
    public Cliente createCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    public Cliente getClienteById(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
    }

    @Override
    public void deleteCliente(Long id) {
        clienteRepository.deleteById(id);
    }
}
