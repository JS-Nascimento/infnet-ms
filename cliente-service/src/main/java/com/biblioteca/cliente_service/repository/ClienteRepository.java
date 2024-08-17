package com.biblioteca.cliente_service.repository;

import com.biblioteca.cliente_service.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
